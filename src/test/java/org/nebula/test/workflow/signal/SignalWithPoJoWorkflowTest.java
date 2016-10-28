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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.nebula.test.activity.BasicActivityImpl;
import org.nebula.test.workflow.BaseTest;
import org.nebula.test.workflow.CustomPoJo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SignalWithPoJoWorkflowTest extends BaseTest {

  private SignalWithPoJoWorkflowClientImpl client;

  @Before
  public void setUp() {
    super.setUp();

    startActivityWorker(BasicActivityImpl.class, realms);

    startWorkflowWorker(SignalWithPoJoWorkflowImpl.class, realms);

    client = new SignalWithPoJoWorkflowClientImpl(nebulaClient,
                                                  workflowProfile, realms);

    client.test("nebula");

    log.info("instanceId=" + client.getInstanceId());

    String
        s =
        "[{\"name\":\"nebula\",\"addresses\":[\"beijing\",\"shanghai\"]},{\"name\":\"nebula\",\"addresses\":[\"guangzhou\"]}]";
    try {
      Object result = new ObjectMapper().readValue(s,
                                                   new TypeReference<List<CustomPoJo>>() {
                                                   });
      log.info("result=" + result);
    } catch (Exception e) {
      log.error(e);
    }

    sleep(5);
  }

  @Test
  public void test() {

    CustomPoJo pojo = new CustomPoJo();
    pojo.setName("newName");

    String[] addresses = new String[]{"nebula", "nebula-service"};
    pojo.setAddresses(addresses);

    client.signal(pojo);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }

  @Test
  public void testWithList() {

    List<CustomPoJo> pojos = new ArrayList<CustomPoJo>();
    pojos.add(createCustomPoJo("nebula", "beijing", "shanghai"));
    pojos.add(createCustomPoJo("nebula", "guangzhou"));

    client.signal(pojos);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }

  @Test
  public void testWithSet() {

    Set<CustomPoJo> pojos = new HashSet<CustomPoJo>();
    pojos.add(createCustomPoJo("nebula", "beijing", "shanghai"));
    pojos.add(createCustomPoJo("nebula", "guangzhou"));

    client.signal(pojos);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }

  @Test
  public void testWithCollection() {

    Collection<CustomPoJo> pojos = new ArrayList<CustomPoJo>();
    pojos.add(createCustomPoJo("nebula", "beijing", "shanghai"));
    pojos.add(createCustomPoJo("nebula", "guangzhou"));

    client.signal(pojos);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }

  @Test
  public void testWithMap() {

    Map<Integer, CustomPoJo> pojos = new HashMap<Integer, CustomPoJo>();
    pojos.put(1, createCustomPoJo("nebula", "beijing", "shanghai"));
    pojos.put(2, createCustomPoJo("nebula", "guangzhou"));

    client.signal(pojos);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }

  private CustomPoJo createCustomPoJo(String name, String... addresses) {
    CustomPoJo pojo = new CustomPoJo(name, addresses);

    return pojo;
  }
}
