/*
 * Copyright (c) 2018, 2020 Oracle and/or its affiliates and others.
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

package jakarta.websocket;

import java.util.List;
import java.util.Map;

/**
 * The handshake response represents the WebSocket-defined HTTP response that is the response to the opening handshake
 * request.
 *
 * @author dannycoward
 */
public interface HandshakeResponse {

    /**
     * The Sec-WebSocket-Accept header name.
     */
    static final String SEC_WEBSOCKET_ACCEPT = "Sec-WebSocket-Accept";

    /**
     * Return the map of HTTP headers to header values sent by the WebSocket server. Note that the lookup of header
     * names will be performed in a case insensitive manner.
     *
     * @return the HTTP headers.
     */
    Map<String, List<String>> getHeaders();
}
