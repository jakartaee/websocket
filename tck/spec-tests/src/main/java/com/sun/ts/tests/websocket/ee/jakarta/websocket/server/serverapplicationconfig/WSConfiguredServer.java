/*
 * Copyright (c) 2013, 2020 Oracle and/or its affiliates and others.
 * All rights reserved.
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

package com.sun.ts.tests.websocket.ee.jakarta.websocket.server.serverapplicationconfig;

import java.io.IOException;

import com.sun.ts.tests.websocket.common.stringbean.StringBean;
import com.sun.ts.tests.websocket.common.util.IOUtil;

import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class WSConfiguredServer extends Endpoint implements MessageHandler.Whole<StringBean> {

	Session session;

	@Override
	public void onMessage(StringBean bean) {
		try {
			session.getBasicRemote().sendText(bean.get());
		} catch (IOException e) {
			e.printStackTrace();
			try {
				session.getBasicRemote().sendText(IOUtil.printStackTrace(e));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void onOpen(Session session, EndpointConfig config) {
		this.session = session;
		session.addMessageHandler(this);
	}

}
