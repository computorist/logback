/**
 * Logback: the reliable, generic, fast and flexible logging framework.
 * Copyright (C) 1999-2009, QOS.ch. All rights reserved.
 *
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 *
 *   or (per the licensee's choosing)
 *
 * under the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation.
 */
package ch.qos.logback.access.joran;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.qos.logback.access.TeztConstants;
import ch.qos.logback.access.dummy.DummyAccessEventBuilder;
import ch.qos.logback.access.spi.AccessContext;
import ch.qos.logback.access.spi.AccessEvent;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.read.ListAppender;
import ch.qos.logback.core.testUtil.StringListAppender;

public class JoranConfiguratorTest {

  AccessContext context = new AccessContext();

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  void configure(String file) throws JoranException {
    JoranConfigurator jc = new JoranConfigurator();
    jc.setContext(context);
    jc.doConfigure(file);
  }

  @Test
  public void smoke() throws Exception {
    configure(TeztConstants.TEST_DIR_PREFIX + "input/joran/smoke.xml");

    ListAppender<AccessEvent> listAppender = (ListAppender<AccessEvent>) context
        .getAppender("LIST");
    AccessEvent event = DummyAccessEventBuilder.buildNewAccessEvent();
    listAppender.doAppend(event);

    assertEquals(1, listAppender.list.size());

    assertEquals(1, listAppender.list.size());
    AccessEvent ae = (AccessEvent) listAppender.list.get(0);
    assertNotNull(ae);
  }

  @Test
  public void defaultLayout() throws Exception {
    configure(TeztConstants.TEST_DIR_PREFIX + "input/joran/defaultLayout.xml");
    StringListAppender<AccessEvent> listAppender = (StringListAppender<AccessEvent>) context
        .getAppender("STR_LIST");
    AccessEvent event = DummyAccessEventBuilder.buildNewAccessEvent();
    listAppender.doAppend(event);
    assertEquals(1, listAppender.strList.size());
    // the result contains a line separator at the end
    assertTrue(listAppender.strList.get(0).startsWith("testMethod"));
  }
}
