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

package org.nebula.test.workflow.timercancel;

import org.junit.Test;
import org.nebula.test.workflow.BaseTest;

public class TimerCancelTest extends BaseTest {


  @Test
  public void testTimer() {

    startWorkflowWorker(CancelTimerWorkflowImpl.class, realms);

    CancelTimerWorkflowClientImpl client = new CancelTimerWorkflowClientImpl(
        nebulaClient, workflowProfile, realms);

    client.startTimer(10);

    log.info("instanceId=" + client.getInstanceId());

    sleep(5);

    client.cancel();

    this.waitForWorkflowCompletion(client.getInstanceId(), 10, 2);
  }
}
