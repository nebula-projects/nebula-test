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

import org.junit.Test;
import org.nebula.test.activity.BasicActivityImpl;
import org.nebula.test.workflow.BaseTest;

public class StartWithPrimitiveArrayWorkflowTest extends BaseTest {

  @Test
  public void test() {

    startActivityWorker(BasicActivityImpl.class, realms);

    startWorkflowWorker(StartWithPrimitiveArrayWorkflowImpl.class, realms);

    StartWithPrimitiveArrayWorkflowClient client = new StartWithPrimitiveArrayWorkflowClientImpl(
        nebulaClient, workflowProfile, realms);

    int[] numbers = new int[]{2014, 2015};
    client.test(numbers);

    log.info("instanceId=" + client.getInstanceId());

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }
}
