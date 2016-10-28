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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SignalWithListWorkflowImpl implements SignalWithListWorkflow {

  private final static Log log = LogFactory.getLog(SignalWithListWorkflowImpl.class);

  private BasicActivityClient client = new BasicActivityClientImpl();

  private Promise<String> newName = new Promise<String>();

  @Override
  public void test(String name) {
    log.info("SignalWithListWorkflowImpl.name:" + name);

    Promise<String> result = client.testWithOneArg(newName);
    if (result.isReady()) {
      log.info("Workflow completed");
    } else {
      log.info("Workflow doesn't complete");
    }

  }

  @Override
  public void signal(List<String> newNames) {
    for (String name : newNames) {
      log.info("name=" + name);
    }
    newName.set(newNames.get(0));
  }

  public void signal(Set<Integer> numbers) {
    for (Integer number : numbers) {
      log.info("Set number =" + number);
    }
    newName.set(Integer.toString(numbers.iterator().next()));
  }

  public void signal(Collection<Integer> numbers) {
    for (Integer number : numbers) {
      log.info("Collection number =" + number);
    }
    newName.set(Integer.toString(numbers.iterator().next()));
  }

  public void signal(Map<String, Integer> newNames) {
    for (String key : newNames.keySet()) {
      log.info("Map key=" + key + ", value="
               + newNames.get(key));
    }
    newName.set(newNames.keySet().iterator().next());
  }

}
