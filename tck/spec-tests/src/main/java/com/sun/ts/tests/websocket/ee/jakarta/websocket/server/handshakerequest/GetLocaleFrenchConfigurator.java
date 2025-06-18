/*
 * Copyright (c) 2025 Contributors to the Eclipse Foundation
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
package com.sun.ts.tests.websocket.ee.jakarta.websocket.server.handshakerequest;

import java.util.Arrays;
import java.util.Locale;

import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import jakarta.websocket.server.ServerEndpointConfig.Configurator;

public class GetLocaleFrenchConfigurator extends Configurator {
	static final String KEY = "GetLocaleFrenchConfigurator";
    static final String REQUEST_KEY = "Accept-Language";
    static final String REQUEST_VALUES = "fr";

	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
	    Locale locale = request.getPreferredLocale();
		String value = Boolean.valueOf(Locale.FRENCH.equals(locale)).toString();
		response.getHeaders().put(KEY, Arrays.asList(value));
	}
}
