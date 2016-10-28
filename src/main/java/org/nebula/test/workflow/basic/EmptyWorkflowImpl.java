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
import org.nebula.framework.core.WorkflowContext;

public class EmptyWorkflowImpl implements EmptyWorkflow {

  private final static Log log = LogFactory.getLog(EmptyWorkflowImpl.class);

  private static WorkflowContext workflowContext;

  public EmptyWorkflowImpl(WorkflowContext workflowContext) {
    this.workflowContext = workflowContext;
  }

  public static String getRegistrationId() {
    return workflowContext.getRegistrationId();
  }

  public static String getInstanceId() {
    return workflowContext.getInstanceId();
  }

  @Override
  public void test(String name) {
    log.info("The name is: " + name);
  }

}
