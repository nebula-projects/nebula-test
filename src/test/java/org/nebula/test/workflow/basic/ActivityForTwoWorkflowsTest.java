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

package org.nebula.test.workflow.basic;

import org.junit.Before;
import org.junit.Test;
import org.nebula.framework.model.WorkflowProfile;
import org.nebula.test.activity.BasicActivityImpl;
import org.nebula.test.workflow.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class ActivityForTwoWorkflowsTest extends BaseTest {

  private WorkflowProfile workflowProfile1;
  private WorkflowProfile workflowProfile2;

  private List<String> realms1;
  private List<String> realms2;

  @Before
  public void setUp() {
    workflowProfile1 = new WorkflowProfile();
    workflowProfile1.setName("StartWithoutArgWorkflow");
    workflowProfile1.setVersion("1.0");

    realms1 = new ArrayList<String>();
    realms1.add("StartWithoutArgWorkflow");

    workflowProfile2 = new WorkflowProfile();
    workflowProfile2.setName("StartWorkflow");
    workflowProfile2.setVersion("1.0");

    realms2 = new ArrayList<String>();
    realms2.add("StartWorkflow");
  }

  @Test
  public void test() {
    List<String> activityRealms = new ArrayList<String>();
    activityRealms.add("StartWorkflow");
    activityRealms.add("StartWithoutArgWorkflow");
    startActivityWorker(BasicActivityImpl.class, activityRealms);

    startWorkflowWorker(StartWithoutArgWorkflowImpl.class, realms1);

    startWorkflowWorker(StartWorkflowImpl.class, realms2);

    StartWithoutArgWorkflowClientImpl client1 = new StartWithoutArgWorkflowClientImpl(
        nebulaClient, workflowProfile1, realms1);

    client1.start();

    log.info("StartWithoutArgWorkflowClientImpl instanceId=" + client1.getInstanceId());

    StartWorkflowClientImpl client2 = new StartWorkflowClientImpl(
        nebulaClient, workflowProfile2, realms2);

    client2.start("nebula");

    log.info("StartWorkflowClientImpl instanceId=" + client2.getInstanceId());

    this.waitForWorkflowCompletion(client1.getInstanceId(), 20, 2);
    this.waitForWorkflowCompletion(client2.getInstanceId(), 20, 2);
  }
}
