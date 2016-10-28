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

package org.nebula.test.workflow.cancel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nebula.framework.core.Promise;
import org.nebula.framework.core.Timer;
import org.nebula.test.activity.BasicActivityClient;
import org.nebula.test.activity.BasicActivityClientImpl;

public class CancelWorkflowImpl implements CancelWorkflow {

  private final static Log log = LogFactory.getLog(CancelWorkflowImpl.class);

  private static boolean clientExecuted = false;
  private BasicActivityClient client = new BasicActivityClientImpl();
  private Timer timer = new Timer();

  public void test() {

    log.info("CancelWorkflow");

    Promise<Void> result = timer.schedule(10);

    if (result.isReady()) {
      client.testSleepSecs(5);
      clientExecuted = true;
    }
  }
}
