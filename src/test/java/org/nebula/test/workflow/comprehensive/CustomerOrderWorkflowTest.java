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

package org.nebula.test.workflow.comprehensive;

import org.junit.Test;
import org.nebula.test.activity.CustomerOrderActivityImpl;
import org.nebula.test.workflow.BaseTest;
import org.nebula.test.workflow.signal.TwoSignalMethodsWorkflowClientImpl;
import org.nebula.test.workflow.signal.TwoSignalMethodsWorkflowImpl;

public class CustomerOrderWorkflowTest extends BaseTest {

  @Test
  public void processOrder() {
    startActivityWorker(CustomerOrderActivityImpl.class, realms);

    startWorkflowWorker(CustomerOrderWorkflowImpl.class, realms);

    CustomerOrderWorkflowClient client = new CustomerOrderWorkflowClientImpl(
        nebulaClient, workflowProfile, realms);

    client.startWorkflow("nebula-order-1");

    log.info("instanceId=" + client.getInstanceId());

    sleep(5);
    client.completeShipment(true);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);
  }
}