/*
 * Copyright (c) 2025 Contributors to the Eclipse Foundation.
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

package com.sun.ts.tests.websocket.ee.jakarta.websocket.clientendpointconfig;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.websocket.ClientEndpointConfig;
import jakarta.websocket.Decoder;
import jakarta.websocket.Encoder;
import jakarta.websocket.Extension;

import javax.net.ssl.SSLContext;

public class UserPropertiesClientEndpointConfig implements ClientEndpointConfig {

	public static final String KEY_1 = "CEC-1";

	private final Map<String, Object> CEC_USER_PROPERTIES;
	private final ClientEndpointConfig.Configurator configurator;

	public UserPropertiesClientEndpointConfig() {
		CEC_USER_PROPERTIES = new HashMap<>();
		CEC_USER_PROPERTIES.put(KEY_1, new Object());

		configurator = new ClientEndpointConfig.Configurator();
	}

	@Override
	public Map<String, Object> getUserProperties() {
		// Used in test to verify session.getUserProperties is a shallow copy of this map
		return CEC_USER_PROPERTIES;
	}

	@Override
	public List<Extension> getExtensions() {
		return Collections.emptyList();
	}

	@Override
	public SSLContext getSSLContext() {
        return null;
    }

	@Override
    public ClientEndpointConfig.Configurator getConfigurator() {
        return configurator;
    }

	@Override
	public List<Class<? extends Encoder>> getEncoders() {
		return Collections.emptyList();
	}

	@Override
	public List<Class<? extends Decoder>> getDecoders() {
		return Collections.emptyList();
	}
	
	@Override
	public List<String> getPreferredSubprotocols() {
	    return Collections.emptyList();
	}

}
