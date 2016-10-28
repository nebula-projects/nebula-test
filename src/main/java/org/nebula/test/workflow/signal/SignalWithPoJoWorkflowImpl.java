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
import org.nebula.test.workflow.CustomPoJo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SignalWithPoJoWorkflowImpl implements SignalWithPoJoWorkflow {

  private final static Log log = LogFactory.getLog(SignalWithPoJoWorkflowImpl.class);

  private BasicActivityClient client = new BasicActivityClientImpl();

  private Promise<CustomPoJo> newPojo = new Promise<CustomPoJo>();

  @Override
  public void test(String name) {
    log.info("SignalWithPoJoWorkflow.name:" + name);

    Promise<Integer> i = new Promise<Integer>();
    i.set(1);
    Promise<CustomPoJo> result = client.test(newPojo, i);
    if (result.isReady()) {
      log.info("Workflow completed");
    } else {
      log.info("Workflow doesn't complete");
    }

  }

  @Override
  public void signal(CustomPoJo pojo) {

    log.info("name=" + pojo.getName());
    for (String address : pojo.getAddresses()) {
      log.info("address=" + address);
    }
    newPojo.set(pojo);
  }

  public void signal(List<CustomPoJo> pojos) {
    for (CustomPoJo pojo : pojos) {
      log.info("List  pojo=" + pojo.getName()
               + ", address.size=" + pojo.getAddresses().length);
    }
    newPojo.set(pojos.get(0));
  }

  public void signal(Set<CustomPoJo> pojos) {
    for (CustomPoJo pojo : pojos) {
      log.info("Set  pojo=" + pojo.getName()
               + ", address.size=" + pojo.getAddresses().length);
    }
    newPojo.set(pojos.iterator().next());
  }

  public void signal(Collection<CustomPoJo> pojos) {
    for (CustomPoJo pojo : pojos) {
      log.info("Collection pojo=" + pojo.getName()
               + ", address.size=" + pojo.getAddresses().length);
    }
    newPojo.set(pojos.iterator().next());
  }

  public void signal(Map<Integer, CustomPoJo> pojos) {
    for (CustomPoJo pojo : pojos.values()) {
      log.info("Map pojo=" + pojo.getName() + ", address.size="
               + pojo.getAddresses().length + ", value="
               + pojos.get(pojo));
    }
    newPojo.set(pojos.values().iterator().next());
  }

}
