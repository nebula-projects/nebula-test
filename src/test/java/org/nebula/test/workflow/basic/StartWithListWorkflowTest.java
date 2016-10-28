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

import java.util.ArrayList;
import java.util.List;

public class StartWithListWorkflowTest extends BaseTest {

  @Test
  public void test() throws Exception {
    startActivityWorker(BasicActivityImpl.class, realms);

    startWorkflowWorker(StartWithListWorkflowImpl.class, realms);

    StartWithListWorkflowClientImpl client = new StartWithListWorkflowClientImpl(
        nebulaClient, workflowProfile, realms);

    List<String> names = new ArrayList<String>();
    names.add("nebula");
    names.add("nebula-service");
    client.test(names);

    log.info("instanceId=" + client.getInstanceId());

    this.waitForWorkflowCompletion(client.getInstanceId(), 10, 1);

  }
}
