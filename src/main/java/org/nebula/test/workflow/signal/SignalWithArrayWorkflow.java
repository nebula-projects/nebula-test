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

import org.nebula.framework.annotation.Signal;
import org.nebula.framework.annotation.Start;
import org.nebula.framework.annotation.Workflow;
import org.nebula.test.workflow.CustomPoJo;

@Workflow
public interface SignalWithArrayWorkflow {

  @Start
  public void test(String name);

  @Signal
  public void signal(String[] newNames);

  @Signal
  public void signal(byte[] numbers);

  @Signal
  public void signal(Byte[] bytes);

  @Signal
  public void signal(int[] bytes);

  @Signal
  public void signal(Integer[] numbers);

  @Signal
  public void signal(long[] numbers);

  @Signal
  public void signal(Long[] numbers);

  @Signal
  public void signal(float[] numbers);

  @Signal
  public void signal(Float[] numbers);

  @Signal
  public void signal(double[] numbers);

  @Signal
  public void signal(Double[] numbers);

  @Signal
  public void signal(short[] numbers);

  @Signal
  public void signal(Short[] numbers);

  @Signal
  public void signal(char[] chars);

  @Signal
  public void signal(Character[] chars);

  @Signal
  public void signal(CustomPoJo[] pojos);
}
