/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation
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

import java.net.URI;

import javax.net.ssl.SSLContext;

/**
 * Provides a mechanism for notifications and call-backs associated with the {@link WebSocketContainer}.
 * <p>
 * All methods are provided with default implementations that are effectively NO-OPs allowing listener implementations
 * to implement just the methods relevant to them.
 */
public interface WebSocketContainerListener {

    /**
     * Callback to obtain the SSLContext to be used to establish the connection for a WebSocket session to an endpoint
     * at the given URI using the specified protocol.
     * <p>
     * The default implementation returns {@code null}.
     *
     * @param destination   The full URI including scheme, authority and path for the WebSocket endpoint to which a
     *                      connection will be made
     * @param protocol      The protocol to use to establish the connection. If the protocol has an entry in the <a
     *                      href="https://www.iana.org/assignments/tls-extensiontype-values/tls-extensiontype-values.xhtml#alpn-protocol-ids">IANA
     *                      registry for ALPN names</a> then the identification sequence, in string form, must be used.
     *                      Registered identification sequences MUST only be used for the associated protocol. Values
     *                      for other protocols are implementation dependent.
     *
     * @return the SSLContext to use to configure the new connection or {@code null} if no SSLContext should be used.
     */
    default SSLContext getSSLContextForClientConnection(URI destination, String protocol) {
        return null;
    }
}
