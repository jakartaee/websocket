/*
 * Copyright (c) 2018, 2019 Oracle and/or its affiliates and others.
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

package jakarta.websocket.server;

import java.io.IOException;
import java.util.Map;

import jakarta.websocket.*;

/**
 * The ServerContainer is the specialized view of the WebSocketContainer available in server-side deployments. There is
 * one ServerContainer instance per websocket application. The ServerContainer holds the methods to be able to register
 * server endpoints.
 * <p>
 * For websocket enabled web containers, developers may obtain a reference to the ServerContainer instance by retrieving
 * it as an attribute named <code>jakarta.websocket.server.ServerContainer</code> on the ServletContext. This way, the
 * registration methods held on this interface may be called to register server endpoints from a ServletContextListener
 * during the deployment of the WAR file containing the endpoint. Server endpoints may also be registered after the web
 * application has started.
 * </p>
 * <p>
 * WebSocket implementations that run outside the web container may have other means by which to provide a
 * ServerContainer instance to the developer at application deployment time.
 * </p>
 * <p>
 * Once the application deployment phase is complete, and the websocket application has begun accepting incoming
 * connections, the registration methods may no longer be called.
 *
 * @author dannycoward
 */
public interface ServerContainer extends WebSocketContainer {

    /**
     * Deploys the given annotated endpoint into this ServerContainer.
     *
     * @param endpointClass the class of the annotated endpoint
     * @throws DeploymentException   if the annotated endpoint was badly formed.
     * @throws IllegalStateException if the containing websocket application has already been deployed.
     */
    public void addEndpoint(Class<?> endpointClass) throws DeploymentException;

    /**
     * Deploys the given endpoint described by the provided configuration into this ServerContainer.
     *
     * @param serverConfig the configuration instance representing the logical endpoint that will be registered.
     * @throws DeploymentException   if the endpoint was badly formed.
     * @throws IllegalStateException if the containing websocket application has already been deployed.
     */
    public void addEndpoint(ServerEndpointConfig serverConfig) throws DeploymentException;

    /**
     * Upgrade the HTTP connection represented by the {@code HttpServletRequest} and {@code HttpServletResponse} to the
     * WebSocket protocol and establish a WebSocket connection as per the provided {@link ServerEndpointConfig}.
     * <p>
     * This method is primarily intended to be used by frameworks that implement the front-controller pattern. It does
     * not deploy the provided endpoint.
     * <p>
     * If the WebSocket implementation is not deployed as part of a Jakarta Servlet container, this method will throw an
     * {@link UnsupportedOperationException}.
     *
     * @param httpServletRequest    The {@code HttpServletRequest} to be processed as a WebSocket handshake as per
     *                              section 4.0 of RFC 6455.
     * @param httpServletResponse   The {@code HttpServletResponse} to be used when processing the
     *                              {@code httpServletRequest} as a WebSocket handshake as per section 4.0 of RFC 6455.
     * @param sec                   The server endpoint configuration to use to configure the WebSocket endpoint
     * @param pathParameters        Provides a mapping of path parameter names and values, if any, to be used for the
     *                              WebSocket connection established by the call to this method. If no such mapping is
     *                              defined, an empty Map must be passed.
     *
     * @throws IllegalStateException if the provided request does not meet the requirements of the WebSocket handshake
     * @throws UnsupportedOperationException if the WebSocket implementation is not deployed as part of a Jakarta
     *                                       Servlet container
     * @throws IOException if an I/O error occurs during the establishment of a WebSocket connection
     * @throws DeploymentException if a configuration error prevents the establishment of a WebSocket connection
     */
    public void upgradeHttpToWebSocket(Object httpServletRequest, Object httpServletResponse, ServerEndpointConfig sec,
            Map<String,String> pathParameters) throws IOException, DeploymentException;
}
