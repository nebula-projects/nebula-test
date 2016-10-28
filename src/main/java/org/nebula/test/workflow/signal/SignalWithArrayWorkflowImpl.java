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

public class SignalWithArrayWorkflowImpl implements SignalWithArrayWorkflow {

  private final static Log log = LogFactory.getLog(SignalWithArrayWorkflowImpl.class);

  private BasicActivityClient client = new BasicActivityClientImpl();

  private Promise<String> newName = new Promise<String>();

  @Override
  public void test(String name) {
    log.info("SignalWithArrayWorkflow.name:" + name);

    Promise<String> result = client.testWithOneArg(newName);
    if (result.isReady()) {
      log.info("Workflow completed");
    } else {
      log.info("Workflow doesn't complete");
    }

  }

  @Override
  public void signal(String[] newNames) {
    for (String name : newNames) {
      log.info("String name=" + name);
    }
    newName.set(newNames[0]);
  }

  public void signal(int[] numbers) {
    for (int number : numbers) {
      log.info("int number=" + number);
    }
    newName.set(String.valueOf(numbers[0]));
  }

  public void signal(Integer[] numbers) {
    for (int number : numbers) {
      log.info("Integer number=" + number);
    }
    newName.set(String.valueOf(numbers[0]));
  }

  public void signal(byte[] bytes) {
    for (byte b : bytes) {
      log.info("byte number=" + b);
    }
    newName.set(new String(bytes));
  }

  public void signal(Byte[] bytes) {
    for (byte b : bytes) {
      log.info("Byte number=" + b);
    }
    newName.set(String.valueOf(bytes));
  }

  public void signal(long[] numbers) {
    for (long number : numbers) {
      log.info("long number=" + number);
    }
    newName.set(String.valueOf(numbers[0]));
  }

  public void signal(Long[] numbers) {
    for (Long number : numbers) {
      log.info("Long number=" + number);
    }
    newName.set(String.valueOf(numbers[0]));
  }

  public void signal(float[] numbers) {
    for (float number : numbers) {
      log.info("float number=" + number);
    }
    newName.set(String.valueOf(numbers[0]));
  }

  public void signal(Float[] numbers) {
    for (Float number : numbers) {
      log.info("Float number=" + number);
    }
    newName.set(String.valueOf(numbers[0]));
  }

  public void signal(double[] numbers) {
    for (double number : numbers) {
      log.info("double number=" + number);
    }
    newName.set(String.valueOf(numbers[0]));
  }

  public void signal(Double[] numbers) {
    for (Double number : numbers) {
      log.info("Double number=" + number);
    }
    newName.set(String.valueOf(numbers[0]));
  }

  public void signal(short[] numbers) {
    for (short number : numbers) {
      log.info("short number=" + number);
    }
    newName.set(String.valueOf(numbers[0]));
  }

  public void signal(Short[] numbers) {
    for (Short number : numbers) {
      log.info("Short number=" + number);
    }
    newName.set(String.valueOf(numbers[0]));
  }

  public void signal(char[] chars) {
    for (char c : chars) {
      log.info("char c =" + c);
    }
    newName.set(new String(chars));
  }

  public void signal(Character[] chars) {
    for (Character c : chars) {
      log.info("Character c=" + c);
    }
    newName.set(String.valueOf(chars));
  }

  public void signal(CustomPoJo[] pojos) {
    for (CustomPoJo pojo : pojos) {
      log.info("CustomPoJo pojo=" + pojo.getName()
               + ", address.size=" + pojo.getAddresses().length);
    }
    newName.set(pojos[0].getName());
  }
}
