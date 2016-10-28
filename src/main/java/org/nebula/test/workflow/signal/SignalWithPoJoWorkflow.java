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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Workflow
public interface SignalWithPoJoWorkflow {

  @Start
  public void test(String name);

  @Signal
  public void signal(CustomPoJo pojo);

  @Signal
  public void signal(List<CustomPoJo> pojos);

  @Signal
  public void signal(Set<CustomPoJo> pojos);

  @Signal
  public void signal(Collection<CustomPoJo> pojos);

  @Signal
  //Map<CustomPoJo, Integer> doesn't work. Only primitive type or String as key for map
  public void signal(Map<Integer, CustomPoJo> pojos);
}
