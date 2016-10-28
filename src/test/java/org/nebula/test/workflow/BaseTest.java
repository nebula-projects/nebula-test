/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.nebula.test.workflow;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.nebula.admin.client.NebulaMgmtClient;
import org.nebula.admin.client.request.mgmt.GetHistoryEventsRequest;
import org.nebula.admin.client.response.mgmt.GetHistoryEventsResponse;
import org.nebula.framework.activity.ActivityWorker;
import org.nebula.framework.client.NebulaClient;
import org.nebula.framework.client.NebulaRestClient;
import org.nebula.framework.core.Configuration;
import org.nebula.framework.event.Event;
import org.nebula.framework.event.Event.EVENT_TYPE;
import org.nebula.framework.model.WorkflowProfile;
import org.nebula.framework.workflow.WorkflowEventRecords;
import org.nebula.framework.workflow.WorkflowWorker;
import org.nebula.test.utils.DBUtil;
import org.nebula.test.utils.RedisUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public abstract class BaseTest {

  protected Log log = LogFactory.getLog(getClass());

  protected NebulaClient nebulaClient;

  protected WorkflowProfile workflowProfile;

  protected String registrationId;

  protected List<String> realms;

  protected String user = "test";

  protected DBUtil dbUtil = new DBUtil();
  protected RedisUtil redisUtil = new RedisUtil();

  public BaseTest() {
    try {
      PropertiesConfiguration config = new PropertiesConfiguration("test.properties");

      user = config.getString("user");
      String password = config.getString("password");

      String nebulaServiceHost = config.getString("nebulaServiceHost");
      int nebulaServicePort = config.getInt("nebulaServicePort");
      String nebulaServiceContextPath = config.getString("nebulaServiceContextPath");

      nebulaClient =
          new NebulaRestClient(user, password, nebulaServiceHost, nebulaServicePort,
                               nebulaServiceContextPath);

    } catch (ConfigurationException e) {
      log.error("Failed to init.", e);
    }
  }

  @Before
  public void setUp() {

    redisUtil.removeAllData();

    realms = new ArrayList<String>();
    realms.add(this.getClass().getSimpleName());
  }

  @After
  public void tearDown() {
    redisUtil.removeAllData();
    dbUtil.removeEventsForWorkflow(registrationId);
    dbUtil.removeHistoryEventsForWorkflow(registrationId);
    dbUtil.removeHeartbeatsForWorkflow(registrationId);
    dbUtil.removeRegistrationsForUser(user);
  }

  protected void sleep(int secs) {
    try {
      Thread.sleep(secs * 1000);
    } catch (InterruptedException e) {

    }
  }

  protected void start(Runnable runnable) {
    new Thread(runnable).start();
  }

  protected void startWorkflowWorker(final Class workflowImpl,
                                     final List<String> realms) {
    startWorkflowWorker(workflowImpl, realms, new Configuration());
  }

  protected void startWorkflowWorker(final Class workflowImpl) {
    startWorkflowWorker(workflowImpl, realms);
  }

  protected void startWorkflowWorker(final Class workflowImpl,
                                     final List<String> realms, Configuration configuration) {

    WorkflowWorker worker = new WorkflowWorker(nebulaClient, configuration);
    worker.add(workflowImpl, realms);

    workflowProfile =
        ((WorkflowWorker.ProcessDefinitionRealm)(worker.getNodeDefinitionRealms().get(0))).getDefinition().getWorkflowProfile();

    worker.start();

    registrationId =
        dbUtil
            .getWorkflowRegistrationId(user, workflowProfile.getName(), workflowProfile.getVersion());
  }

  protected void startActivityWorker(final Class activityImpl,
                                     final List<String> realms) {

    ActivityWorker worker = new ActivityWorker(nebulaClient);
    worker.add(activityImpl, realms);

    worker.start();
  }

  protected void startActivityWorker(final Class activityImpl) {

    startActivityWorker(activityImpl, realms);
  }

  protected void waitForWorkflowCompletion(
      final String instanceId, final int maxSecsToWait, int intervalSecs) {

    InterruptTask task = new InterruptTask(Thread.currentThread());
    task.schedule(maxSecsToWait * 1000);

    WorkflowProbe probe = new WorkflowProbe();
    try {
      while (!probe.isWorkflowCompleted() && !task.isTimeLimitExceeded()) {
        sleep(intervalSecs);
        probe.checkWorkflowCompleted(instanceId);
      }
    } finally {
      task.stop();
    }

    if (task.isTimeLimitExceeded()) {
      throw new IllegalStateException("time limit of " + maxSecsToWait
                                      + "s was exceeded.");
    }

  }

  protected void waitForWorkflowCancelled(
      final String instanceId, final int maxSecsToWait, int intervalSecs) {

    InterruptTask task = new InterruptTask(Thread.currentThread());
    task.schedule(maxSecsToWait * 1000);

    WorkflowProbe probe = new WorkflowProbe();
    try {
      while (!probe.isWorkflowCancelled() && !task.isTimeLimitExceeded()) {
        sleep(intervalSecs);
        probe.checkWorkflowCancelled(instanceId);
      }
    } finally {
      task.stop();
    }
    if (task.isTimeLimitExceeded()) {
      throw new IllegalStateException("time limit of " + maxSecsToWait
                                      + "s was exceeded.");
    }

  }


  private static class InterruptTask extends TimerTask {

    protected boolean timeLimitExceeded = false;

    protected Thread thread;

    private Timer timer;

    public InterruptTask(Thread thread) {
      this.thread = thread;
      this.timer = new Timer();
    }

    public void schedule(long delay) {
      Timer timer = new Timer();
      timer.schedule(this,delay);
    }

    public void stop() {
      timer.cancel();
    }

    public boolean isTimeLimitExceeded() {
      return timeLimitExceeded;
    }

    public void run() {
      timeLimitExceeded = true;
      thread.interrupt();
    }
  }

  private class WorkflowProbe {

    private boolean isWorkflowCompleted = false;

    private boolean isWorkflowCancelled = false;

    public void checkWorkflowCompleted(String instanceId) {
      isWorkflowCompleted = isWorkflowCompleted(pollEvents(instanceId)) || wasArchived(instanceId);
    }

    public void checkWorkflowCancelled(String instanceId) {
      isWorkflowCancelled = isWorkflowCancelled(pollEvents(instanceId)) || wasArchived(instanceId);
    }

    public boolean isWorkflowCompleted() {
      return isWorkflowCompleted;
    }

    public boolean isWorkflowCancelled() {
      return isWorkflowCancelled;
    }

    private boolean isWorkflowCompleted(List<Event> events) {
      return exist(events,EVENT_TYPE.WorkflowCompletedEvent);
    }

    private boolean isWorkflowCancelled(List<Event> events) {
      return exist(events,EVENT_TYPE.WorkflowCancelledEvent);
    }

    private boolean exist(List<Event> events, EVENT_TYPE eventType) {
      for (Event event : events) {
        if (event.getEventType() == eventType) {
          return true;
        }
      }
      return false;
    }

    private List<Event> pollEvents(String instanceId) {

      WorkflowEventRecords
          records =
          new WorkflowEventRecords(nebulaClient, instanceId, new Configuration());

      List<Event> events = new LinkedList<Event>();

      while (records.hasNext()) {
        events.add(records.next());
      }

      return events;
    }

    private boolean wasArchived(String instanceId) {

      return dbUtil.existInHistoryEventsTable(instanceId);
    }


  }

}
