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

package org.nebula.test.workflow.timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nebula.framework.core.WorkflowContext;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StartSerialTimerWorkflowImpl implements StartSerialTimerWorkflow {

  private final static Log log = LogFactory.getLog(StartSerialTimerWorkflowImpl.class);

  private static int count;
  private static String registrationId;
  private WorkflowContext workflowContext;

  public StartSerialTimerWorkflowImpl(WorkflowContext workflowContext) {
    this.workflowContext = workflowContext;
  }

  @Override
  public void test() {

    SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

    log.info(
        "Serial timer workflow started. time=" + sdf.format(new Date()) + ", instanceId="
        + workflowContext.getInstanceId() + ", count=" + (++count));
    try {
      Thread.sleep(1000 * 8);
    } catch (InterruptedException e) {
      log.error(e);
    }

    log.info(
        "Serial timer workflow started. time=" + sdf.format(new Date()) + ", instanceId="
        + workflowContext.getInstanceId() + " completed.");

  }
}
