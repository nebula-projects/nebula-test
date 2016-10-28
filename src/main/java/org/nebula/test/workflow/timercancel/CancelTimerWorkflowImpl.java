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

package org.nebula.test.workflow.timercancel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nebula.framework.core.Timer;

public class CancelTimerWorkflowImpl implements CancelTimerWorkflow {

  private final static Log log = LogFactory.getLog(CancelTimerWorkflowImpl.class);

  private Timer timer = new Timer();

  @Override
  public void startTimer(int secs) {
    log.info("start timer with " + secs + " secs.");
    timer.schedule(secs);
  }

  @Override
  public void cancel() {
    log.info("cancel timer");
    timer.cancel();
  }

}
