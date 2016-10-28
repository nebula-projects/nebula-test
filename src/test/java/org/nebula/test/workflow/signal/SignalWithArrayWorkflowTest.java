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
import org.nebula.test.workflow.CustomPoJo;

public class SignalWithArrayWorkflowTest extends BaseTest {

  private SignalWithArrayWorkflowClientImpl client;

  @Before
  public void setUp() {
    super.setUp();

    startActivityWorker(BasicActivityImpl.class, realms);

    startWorkflowWorker(SignalWithArrayWorkflowImpl.class, realms);

    client = new SignalWithArrayWorkflowClientImpl(nebulaClient,
                                                   workflowProfile, realms);

    client.test("nebula");

    log.info("instanceId=" + client.getInstanceId());

    sleep(5);
  }

  @Test
  public void testWithStringArray() {

    String[] newNames = new String[]{"nebula", "nebula-service"};
    client.signal(newNames);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }

  @Test
  public void testWithIntegerArray() {
    Integer[] newNames = new Integer[]{2014, 2015};
    client.signal(newNames);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }

  @Test
  public void testWithIntArray() {

    int[] newNames = new int[]{2014, 2015};
    client.signal(newNames);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }

  @Test
  public void testWithbyteArray() {

    byte[] bytes = new byte[]{1, 2};
    client.signal(bytes);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }

  @Test
  public void testWithByteArray() {

    Byte[] bytes = new Byte[]{1, 2};
    client.signal(bytes);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }

  @Test
  public void testWithshortArray() {

    short[] numbers = new short[]{1, 2};
    client.signal(numbers);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }

  @Test
  public void testWithShortArray() {

    Short[] numbers = new Short[]{1, 2};
    client.signal(numbers);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }

  @Test
  public void testWithlongArray() {

    long[] numbers = new long[]{10000000000000000L, 200000000000000L};
    client.signal(numbers);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }

  @Test
  public void testWithLongArray() {

    Long[] numbers = new Long[]{10000000000000000L, 200000000000000L};
    client.signal(numbers);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }

  @Test
  public void testWithfloatArray() {

    float[] numbers = new float[]{1.1f, 2.2f};
    client.signal(numbers);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }

  @Test
  public void testWithFloatArray() {

    Float[] numbers = new Float[]{1.1f, 2.2f};
    client.signal(numbers);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }

  @Test
  public void testWithdoubleArray() {

    double[] numbers = new double[]{1.11d, 2.22d};
    client.signal(numbers);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }

  @Test
  public void testWithDoubleArray() {

    Double[] numbers = new Double[]{1.11d, 2.22d};
    client.signal(numbers);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }

  @Test
  public void testWithcharArray() {

    char[] numbers = new char[]{'a', 'b'};
    client.signal(numbers);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }

  @Test
  public void testWithCharacterArray() {

    Character[] numbers = new Character[]{'a', 'b'};
    client.signal(numbers);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }

  @Test
  public void testWithCustomPoJoArray() {

    CustomPoJo[] pojos = new CustomPoJo[]{
        new CustomPoJo("nebula", new String[]{"beijing", "shanghai"}),
        new CustomPoJo("nebula-service", new String[]{"guangzhou"})};
    client.signal(pojos);

    this.waitForWorkflowCompletion(client.getInstanceId(), 5, 1);

  }
}
