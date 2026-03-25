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

package ee.jakarta.tck.websocket.ee.remoteendpoint.usercoder.basic;

import java.io.IOException;

import ee.jakarta.tck.websocket.ee.remoteendpoint.usercoder.TextCoderBool;
import ee.jakarta.tck.websocket.ee.remoteendpoint.usercoder.TextCoderByte;
import ee.jakarta.tck.websocket.ee.remoteendpoint.usercoder.TextCoderChar;
import ee.jakarta.tck.websocket.ee.remoteendpoint.usercoder.TextCoderDouble;
import ee.jakarta.tck.websocket.ee.remoteendpoint.usercoder.TextCoderFloat;
import ee.jakarta.tck.websocket.ee.remoteendpoint.usercoder.TextCoderInt;
import ee.jakarta.tck.websocket.ee.remoteendpoint.usercoder.TextCoderLong;
import ee.jakarta.tck.websocket.ee.remoteendpoint.usercoder.TextCoderShort;
import jakarta.websocket.EncodeException;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/text", encoders = { TextCoderBool.class, TextCoderByte.class, TextCoderChar.class,
		TextCoderDouble.class, TextCoderInt.class, TextCoderLong.class, TextCoderFloat.class, TextCoderShort.class })
public class WSCTextServer extends WSCCommonServer {

	@Override
	@OnMessage
	public void onMessage(String msg, Session session) throws IOException, EncodeException {
		super.onMessage(msg, session);
	}

	@Override
	@OnError
	public void onError(Session session, Throwable t) throws IOException {
		super.onError(session, t);
	}
}
