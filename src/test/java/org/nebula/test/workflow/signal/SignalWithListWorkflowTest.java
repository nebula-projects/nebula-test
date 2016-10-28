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


import org.junit.Before;
import org.junit.Test;
import org.nebula.test.activity.BasicActivityImpl;
import org.nebula.test.workflow.BaseTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SignalWithListWorkflowTest extends BaseTest {

  private SignalWithListWorkflowClientImpl client;

  @Before
  public void setUp() {
    super.setUp();

    startActivityWorker(BasicActivityImpl.class, realms);

    startWorkflowWorker(SignalWithListWorkflowImpl.class, realms);

    client = new SignalWithListWorkflowClientImpl(
        nebulaClient, workflowProfile, realms);

    client.test("nebula");

    log.info("instanceId=" + client.getInstanceId());

    sleep(5);
  }

  @Test
  public void testWithList() {

    List<String> newNames = new ArrayList<String>();
    newNames.add("nebula");
    newNames.add("nebula-service");
    client.signal(newNames);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }

  @Test
  public void testWithSet() {

    Set<Integer> newNames = new HashSet<Integer>();
    newNames.add(2014);
    newNames.add(2015);
    client.signal(newNames);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }

  @Test
  public void testWithCollection() {

    Collection<Integer> newNames = new LinkedList<Integer>();
    newNames.add(2014);
    newNames.add(2015);
    client.signal(newNames);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }

  @Test
  public void testWithMap() {

    Map<String, Integer> newNames = new HashMap<String, Integer>();
    newNames.put("nebula", 2014);
    newNames.put("nebula-service", 2015);
    client.signal(newNames);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);
  }
}
