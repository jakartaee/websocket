/*
 * Copyright (c) 2013, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */
package ee.jakarta.tck.websocket.ee.websocketcontainer;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import ee.jakarta.tck.websocket.common.client.WebSocketCommonClient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ws_wait;
 */
@ExtendWith(ArquillianExtension.class)
public class WSClientIT extends WebSocketCommonClient {

	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		WebArchive archive = ShrinkWrap.create(WebArchive.class, "ws_websocketcontainer_web.war");
		archive.addPackages(true, Filters.exclude(WSClientIT.class),
				"ee.jakarta.tck.websocket.ee.websocketcontainer");
		archive.addAsWebInfResource(WSClientIT.class.getPackage(), "web.xml", "web.xml");
		return archive;
	}

	private static final String CONTEXT_ROOT = "ws_websocketcontainer_web";

	private static final String SENT_STRING_MESSAGE = "Hello World";

	public WSClientIT() throws Exception {
		setContextRoot(CONTEXT_ROOT);
	}

	/* Run test */
	/*
	 * @testName: simpleTest
	 *
	 * @assertion_ids:
	 *
	 * @test_Strategy:
	 */
	@Test
	public void simpleTest() throws Exception {
		String expected = "TCKTestServer opened|" + "TCKTestServer received: " + SENT_STRING_MESSAGE
				+ "|TCKTestServer responds";
		setCountDownLatchCount(3);
		setProperty(Property.REQUEST, buildRequest("TCKTestServer"));
		setProperty(Property.SEARCH_STRING, expected);
		setProperty(Property.CONTENT, SENT_STRING_MESSAGE);
		invoke();

	}
}
