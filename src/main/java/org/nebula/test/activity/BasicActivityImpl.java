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

package org.nebula.test.activity;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nebula.test.workflow.CustomPoJo;

import java.util.List;

public class BasicActivityImpl implements BasicActivity {

  private final static Log log = LogFactory.getLog(BasicActivityImpl.class);

  @Override
  public void testWithoutReturn(String name) {
    log.info("testWithOneArg testWithoutReturn: " + name);

  }

  @Override
  public void testWithoutArg() {
    log.info("testWithoutArg");

  }

  @Override
  public String testWithOneArg(String name) {
    log.info("testWithOneArg: " + name);

    return name;
  }

  @Override
  public String testWithTwoArg(String name, int age) {
    log.info("testWithTwoArg name=" + name + ", age=" + age);
    return null;
  }

  @Override
  public String[] testWithArrayArg(String[] names) {
    log.info("testWithArrayArg name.length=" + names.length);

    return names;
  }

  public int[] testWithintArrayArg(int[] numbers) {
    log.info("testWithintArrayArg number.length=" + numbers.length);

    return numbers;
  }

  public List<String> testWithList(List<String> names) {
    log.info("testWithList names.size=" + names.size());

    return names;
  }

  public CustomPoJo test(CustomPoJo pojo, int i) {
    log.info("test customPojo name: " + pojo.getName() + ", i=" + i);

    return pojo;
  }

  public void testSleepSecs(int secs) {
    log.info("test testSleepSecs : " + secs + " secs.");
    try {
      Thread.sleep(secs * 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
