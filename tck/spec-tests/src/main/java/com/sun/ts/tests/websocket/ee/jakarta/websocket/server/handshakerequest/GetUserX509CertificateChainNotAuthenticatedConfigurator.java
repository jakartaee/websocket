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

import java.security.cert.X509Certificate;
import java.util.Arrays;

import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import jakarta.websocket.server.ServerEndpointConfig.Configurator;

public class GetUserX509CertificateChainNotAuthenticatedConfigurator extends Configurator {
	static final String KEY = "GetUserX509CertificateChainNotAuthenticatedConfigurator";

	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
	    X509Certificate[] x509Certificates = request.getUserX509CertificateChain();
		String value = Boolean.valueOf(x509Certificates == null).toString();
		response.getHeaders().put(KEY, Arrays.asList(value));
	}
}
