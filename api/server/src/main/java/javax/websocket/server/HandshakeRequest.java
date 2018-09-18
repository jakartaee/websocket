/*
 * Copyright (c) 2012, 2017 Oracle and/or its affiliates. All rights reserved.
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

package javax.websocket.server;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * The handshake request represents the web socket defined Http GET request for the opening handshake of a web socket
 * session.
 *
 * @author dannycoward
 */
public interface HandshakeRequest {
    /**
     * The Sec-WebSocket-Key header name
     */
    static String SEC_WEBSOCKET_KEY = "Sec-WebSocket-Key";
    /**
     * The Sec-WebSocket-Protocol header name
     */
    static String SEC_WEBSOCKET_PROTOCOL = "Sec-WebSocket-Protocol";
    /**
     * The Sec-WebSocket-Version header name
     */
    static String SEC_WEBSOCKET_VERSION = "Sec-WebSocket-Version";
    /**
     * The Sec-WebSocket-Extensions header name
     */
    static String SEC_WEBSOCKET_EXTENSIONS = "Sec-WebSocket-Extensions";

    /**
     * Return the read only Map of Http Headers that came with the handshake request. The header names are case
     * insensitive.
     *
     * @return the list of headers.
     */
    Map<String, List<String>> getHeaders();

    /**
     * Return the authenticated user or {@code null} if no user is authenticated for this handshake.
     *
     * @return the user principal.
     */
    Principal getUserPrincipal();

    /**
     * Return the request URI of the handshake request.
     *
     * @return the request uri of the handshake request.
     */
    URI getRequestURI();

    /**
     * Checks whether the current user is in the given role. Roles and role membership can be defined using deployment
     * descriptors of the containing WAR file, if running in a Java EE web container. If the user has not been
     * authenticated, the method returns {@code false}.
     *
     * @param role the role being checked.
     * @return whether the authenticated user is in the role, or false if the user has not been authenticated.
     */
    boolean isUserInRole(String role);

    /**
     * Return a reference to the HttpSession that the web socket handshake that started this conversation was part of,
     * if the implementation is part of a Java EE web container.
     *
     * @return the http session or {@code null} if either the websocket implementation is not part of a Java EE web
     *         container, or there is no HttpSession associated with the opening handshake request.
     */
    Object getHttpSession();

    /**
     * Return the request parameters associated with the request.
     *
     * @return the unmodifiable map of the request parameters.
     */
    Map<String, List<String>> getParameterMap();

    /**
     * Return the query string associated with the request.
     *
     * @return the query string.
     */
    String getQueryString();
}
