/*BEGIN_COPYRIGHT_BLOCK
 *
 * Copyright (c) 2001-2016, JavaPLT group at Rice University (drjava@rice.edu)
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *    * Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *    * Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *    * Neither the names of DrJava, the JavaPLT group, Rice University, nor the
 *      names of its contributors may be used to endorse or promote products
 *      derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * This software is Open Source Initiative approved Open Source Software.
 * Open Source Initative Approved is a trademark of the Open Source Initiative.
 * 
 * This file is part of DrJava.  Download the current version of this project
 * from http://www.drjava.org/ or http://sourceforge.net/projects/drjava/
 * 
 * END_COPYRIGHT_BLOCK*/

package edu.rice.cs.drjava.ui.config;

import edu.rice.cs.drjava.DrJava;
import edu.rice.cs.drjava.DrJavaTestCase;
import edu.rice.cs.drjava.config.OptionConstants;
import edu.rice.cs.util.swing.DefaultSwingFrame;
import edu.rice.cs.util.swing.Utilities;

/** * Tests functionality of this OptionComponent
 */
public final class BooleanOptionComponentTest extends DrJavaTestCase {

  private static BooleanOptionComponent _option;

  public BooleanOptionComponentTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
    _option = new BooleanOptionComponent( OptionConstants.LINEENUM_ENABLED, "Line Enumeration", new DefaultSwingFrame());
    DrJava.getConfig().resetToDefaults();
    Utilities.clearEventQueue();
  }

  public void testCancelDoesNotChangeConfig() {

    Boolean testBoolean = new Boolean (!DrJava.getConfig().getSetting(OptionConstants.LINEENUM_ENABLED).booleanValue());

    _option.setValue(testBoolean);
    Utilities.clearEventQueue();
    _option.resetToCurrent(); // should reset to the original.
    Utilities.clearEventQueue();
    _option.updateConfig(); // should update with original values therefore no change.
    Utilities.clearEventQueue();
    assertEquals("Cancel (resetToCurrent) should not change the config",
                 OptionConstants.LINEENUM_ENABLED.getDefault(),
                 DrJava.getConfig().getSetting(OptionConstants.LINEENUM_ENABLED));

  }

  public void testApplyDoesChangeConfig() {
    Boolean testBoolean = new Boolean (!DrJava.getConfig().getSetting(OptionConstants.LINEENUM_ENABLED).booleanValue());

    _option.setValue(testBoolean);
    Utilities.clearEventQueue();
    _option.updateConfig();
    Utilities.clearEventQueue();
    assertEquals("Apply (updateConfig) should write change to file",
                 testBoolean,
                 DrJava.getConfig().getSetting(OptionConstants.LINEENUM_ENABLED));
  }

  public void testApplyThenResetDefault() {
    Boolean testBoolean = new Boolean (!DrJava.getConfig().getSetting(OptionConstants.LINEENUM_ENABLED).booleanValue());

    _option.setValue(testBoolean);
    Utilities.clearEventQueue();
    _option.updateConfig();
    Utilities.clearEventQueue();
    _option.resetToDefault(); // resets to default
    Utilities.clearEventQueue();
    _option.updateConfig();
    Utilities.clearEventQueue();

    assertEquals("Apply (updateConfig) should write change to file",
                 OptionConstants.LINEENUM_ENABLED.getDefault(),
                 DrJava.getConfig().getSetting(OptionConstants.LINEENUM_ENABLED));
  }
}

