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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nebula.framework.core.Promise;
import org.nebula.framework.core.Timer;
import org.nebula.test.activity.CustomerOrderActivityClient;
import org.nebula.test.activity.CustomerOrderActivityClientImpl;

public class CustomerOrderWorkflowImpl implements CustomerOrderWorkflow {

  private final static Log log = LogFactory.getLog(CustomerOrderWorkflowImpl.class);

  private CustomerOrderActivityClient orderActivityClient = new CustomerOrderActivityClientImpl();

  private Timer timer = new Timer();

  @Override
  public void startWorkflow(String orderId) {
    log.info("StartWorkflow order " + orderId);

    Promise<Boolean> charged = orderActivityClient.chargeCreditCard("creditCardNumber", 100);

    Promise<Boolean> packed = orderActivityClient.pack(orderId, charged);

    if (packed.isReady() && packed.get().equals(true)) {
      orderActivityClient.ship(orderId);

      //Wait for a period of time to complete the shipment.
      //If there is no shipment confirmation, the order will be completed at last.
      timer.schedule(20);
    }

  }

  @Override
  public void completeShipment(boolean success) {
    log.info("CompleteShipment success " + success);
    timer.cancel();
  }

}