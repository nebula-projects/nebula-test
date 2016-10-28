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

import org.junit.After;
import org.junit.Test;
import org.nebula.test.workflow.BaseTest;

import static org.junit.Assert.assertTrue;

public class StartSerialTimerWorkflowTest extends BaseTest {

  @After
  public void tearDown() {
    super.tearDown();
    dbUtil.removeWorkflowTimer(registrationId);
  }

  @Test
  public void test() {
    startWorkflowWorker(StartSerialTimerWorkflowImpl.class, realms);

    sleep(20);

    int startedInstances = dbUtil.getNumbersOfStartedInstanceForWorkflow(registrationId);
    int completedInstances = dbUtil.getNumbersOfCompletedInstanceForWorkflow(registrationId);

    log.info("startedInstances:" + startedInstances);
    log.info("completedInstances:" + completedInstances);

    assertTrue(startedInstances >= 2);
    assertTrue(startedInstances <= 3);

    assertTrue(completedInstances >= 1);
    assertTrue(completedInstances <= 2);


  }
}
