/*
 * Copyright (c) 2018 Oracle and/or its affiliates and others.
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

package javax.websocket.server;

import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;

/**
 * The ServerContainer is the specialized view of the WebSocketContainer available in server-side deployments. There is
 * one ServerContainer instance per websocket application. The ServerContainer holds the methods to be able to register
 * server endpoints during the initialization phase of the application.
 * <p>
 * For websocket enabled web containers, developers may obtain a reference to the ServerContainer instance by retrieving
 * it as an attribute named <code>javax.websocket.ServerContainer</code> on the ServletContext. This way, the
 * registration methods held on this interface may be called to register server endpoints from a ServletContextListener
 * during the deployment of the WAR file containing the endpoint.
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
     * Deploys the given annotated endpoint into this ServerContainer during the initialization phase of deploying the
     * application.
     *
     * @param endpointClass the class of the annotated endpoint
     * @throws DeploymentException   if the annotated endpoint was badly formed.
     * @throws IllegalStateException if the containing websocket application has already been deployed.
     */
    public void addEndpoint(Class<?> endpointClass) throws DeploymentException;

    /**
     *
     * @param serverConfig the configuration instance representing the logical endpoint that will be registered.
     * @throws DeploymentException   if the endpoint was badly formed.
     * @throws IllegalStateException if the containing websocket application has already been deployed.
     */
    public void addEndpoint(ServerEndpointConfig serverConfig) throws DeploymentException;

}
