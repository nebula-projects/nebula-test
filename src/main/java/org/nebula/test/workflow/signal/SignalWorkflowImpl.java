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

package org.nebula.test.workflow.signal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nebula.framework.core.Promise;
import org.nebula.test.activity.BasicActivityClient;
import org.nebula.test.activity.BasicActivityClientImpl;

public class SignalWorkflowImpl implements SignalWorkflow {

  private final static Log log = LogFactory.getLog(SignalWorkflowImpl.class);

  private BasicActivityClient client = new BasicActivityClientImpl();

  private Promise<String> newName = new Promise<String>();

  @Override
  public void test(String name) {
    log.info("SignalWorkflowImpl.name:" + name);

    Promise<String> result = client.testWithOneArg(newName);

    if (result.isReady()) {
      log.info("Workflow completed with new name " + newName.get());
    } else {
      log.info("Workflow doesn't complete");
    }


  }

  @Override
  public void signal(String newName) {
    log.info("signal " + newName);
    this.newName.set(newName);
  }

}
