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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nebula.framework.core.Promise;
import org.nebula.test.activity.BasicActivityClient;
import org.nebula.test.activity.BasicActivityClientImpl;

import java.util.List;

public class StartWithListWorkflowImpl implements StartWithListWorkflow {

  private final static Log log = LogFactory.getLog(StartWithListWorkflowImpl.class);

  private BasicActivityClient client = new BasicActivityClientImpl();

  @Override
  public List<String> test(List<String> names) {
    for (int i = 0; i < names.size(); i++) {
      log.info("StartWithListWorkflow name." + i + "=" + names.get(i));
    }

    Promise<List<String>> result = client.testWithList(names);

    if (result.isReady()) {
      for (int i = 0; i < result.get().size(); i++) {
        log.info("StartWithListWorkflow completed." + i + "=" + result.get().get(i));
      }
    } else {
      log.info("StartWithListWorkflow doesn't complete");
    }

    return null;
  }

}
