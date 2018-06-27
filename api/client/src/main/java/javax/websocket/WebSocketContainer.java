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

package javax.websocket;

import java.io.*;
import java.net.URI;
import java.util.Set;

/**
 * A WebSocketContainer is an implementation provided object that provides applications
 * a view on the container running it. The WebSocketContainer container various
 * configuration parameters that control default session and buffer properties
 * of the endpoints it contains. It also allows the developer to
 * deploy websocket client endpoints by initiating a web socket handshake from 
 * the provided endpoint to a supplied URI where the peer endpoint is presumed to
 * reside. 
 *
 * <p>A WebSocketContainer may be accessed by concurrent threads, so
 * implementations must ensure the integrity of its mutable attributes in such 
 * circumstances.  
 *
 * @author dannycoward
 */
public interface WebSocketContainer {

    /**
     * Return the number of milliseconds the implementation will timeout
     * attempting to send a websocket message for all RemoteEndpoints associated
     * with this container. A non-positive number indicates
     * the implementation will not timeout attempting to send a websocket message
     * asynchronously. Note this default may be overridden in each RemoteEndpoint.
     *
     * @return the timeout time in millsenconds.
     */
    long getDefaultAsyncSendTimeout();

    /**
     * Sets the number of milliseconds the implementation will timeout
     * attempting to send a websocket message  for all RemoteEndpoints associated
     * with this container. A non-positive number indicates
     * the implementation will not timeout attempting to send a websocket message
     * asynchronously. Note this default may be overridden in each RemoteEndpoint.
     */
    void setAsyncSendTimeout(long timeoutmillis);

    /**
     * Connect the supplied annotated endpoint instance to its server. The supplied 
     * object must be a class decorated with the class level
     * {@link javax.websocket.ClientEndpoint} annotation. This method 
     * blocks until the connection is established, or throws an error if either 
     * the connection could not be made or there was a problem with the supplied 
     * endpoint class. If the developer uses this method to deploy the client
     * endpoint, services like dependency injection that are supported, for 
     * example, when the implementation is part of the Java EE platform
     * may not be available. If the client endpoint uses dependency injection,
     * use {@link WebSocketContainer#connectToServer(java.lang.Class, java.net.URI)}
     * instead.
     *
     * @param annotatedEndpointInstance the annotated websocket client endpoint 
     * instance.
     * @param path the complete path to the server endpoint.
     * @return the Session created if the connection is successful.
     * @throws DeploymentException if the annotated endpoint instance is not valid.
     * @throws IOException if there was a network or protocol problem that 
     * prevented the client endpoint being connected to its server.
     * @throws IllegalStateException if called during the deployment phase
     * of the containing application.
     */
    Session connectToServer(Object annotatedEndpointInstance, URI path) throws DeploymentException, IOException;             
         
    /**
     * Connect the supplied annotated endpoint to its server. The supplied object must be a
     * class decorated with the class level
     * {@link javax.websocket.server.ServerEndpoint} annotation. This method blocks until the connection
     * is established, or throws an error if either the connection could not be made or there
     * was a problem with the supplied endpoint class.
     *
     * @param annotatedEndpointClass the annotated websocket client endpoint.
     * @param path                   the complete path to the server endpoint.
     * @return the Session created if the connection is successful.
     * @throws DeploymentException if the class is not a valid annotated endpoint class.
     * @throws IOException if there was a network or protocol problem that 
     * prevented the client endpoint being connected to its server.
     * @throws IllegalStateException if called during the deployment phase
     * of the containing application.
     */
    Session connectToServer(Class<?> annotatedEndpointClass, URI path) throws DeploymentException, IOException;

    
    /**
     * Connect the supplied programmatic client endpoint instance to its server 
     * with the given configuration. This method blocks until the connection
     * is established, or throws an error if the connection could not be made. 
     * If the developer uses this method to deploy the client
     * endpoint, services like dependency injection that are supported, for 
     * example, when the implementation is part of the Java EE platform
     * may not be available. If the client endpoint uses dependency injection,
     * use {@link WebSocketContainer#connectToServer(java.lang.Class, javax.websocket.ClientEndpointConfig, java.net.URI) }
     * instead.
     *
     * @param endpointInstance the programmatic client endpoint instance {@link Endpoint}.
     * @param path          the complete path to the server endpoint.
     * @param cec           the configuration used to configure the programmatic endpoint.
     * @return the Session created if the connection is successful.
     * @throws DeploymentException if the configuration is not valid
     * @throws IOException if there was a network or protocol problem that 
     * prevented the client endpoint being connected to its server
     * @throws IllegalStateException if called during the deployment phase
     * of the containing application.
     */
    Session connectToServer(Endpoint endpointInstance, ClientEndpointConfig cec, URI path) throws DeploymentException, IOException;
    
    /**
     * Connect the supplied programmatic endpoint to its server with the given 
     * configuration. This method blocks until the connection
     * is established, or throws an error if the connection could not be made.
     *
     * @param endpointClass the programmatic client endpoint class {@link Endpoint}.
     * @param path          the complete path to the server endpoint.
     * @param cec           the configuration used to configure the programmatic endpoint.
     * @return the Session created if the connection is successful.
     * @throws DeploymentException if the configuration is not valid
     * @throws IOException if there was a network or protocol problem that prevented the client endpoint being connected to its server
     * @throws IllegalStateException if called during the deployment phase
     * of the containing application.
     */
    Session connectToServer(Class<? extends Endpoint> endpointClass, ClientEndpointConfig cec, URI path) throws DeploymentException, IOException;



    /**
     * Return the default time in milliseconds after which any web socket sessions in this
     * container will be closed if it has been inactive. A value that is
     * 0 or negative indicates the sessions will never timeout due to inactivity.
     * The value may be overridden on a per session basis using
     * {@link Session#setMaxIdleTimeout(long) }
     *
     * @return the default number of milliseconds after which an idle session in this container
     * will be closed
     */
    long getDefaultMaxSessionIdleTimeout();

    /**
     * Sets the default time in milliseconds after which any web socket sessions in this
     * container will be closed if it has been inactive. A value that is
     * 0 or negative indicates the sessions will never timeout due to inactivity.
     * The value may be overridden on a per session basis using
     * {@link Session#setMaxIdleTimeout(long) }
     *
     * @param timeout the maximum time in milliseconds.
     */
    void setDefaultMaxSessionIdleTimeout(long timeout);

    /**
     * Returns the default maximum size of incoming binary message that this container
     * will buffer. This default may be overridden on a per session basis using
     * {@link Session#setMaxBinaryMessageBufferSize(int) }
     *
     * @return the maximum size of incoming binary message in number of bytes.
     */
    int getDefaultMaxBinaryMessageBufferSize();

    /**
     * Sets the default maximum size of incoming binary message that this container
     * will buffer.
     *
     * @param max the maximum size of binary message in number of bytes.
     */
    void setDefaultMaxBinaryMessageBufferSize(int max);

    /**
     * Returns the default maximum size of incoming text message that this container
     * will buffer. This default may be overridden on a per session basis using
     * {@link Session#setMaxTextMessageBufferSize(int) }
     *
     * @return the maximum size of incoming text message in number of bytes.
     */
    int getDefaultMaxTextMessageBufferSize();

    /**
     * Sets the maximum size of incoming text message that this container
     * will buffer.
     *
     * @param max the maximum size of text message in number of bytes.
     */
    void setDefaultMaxTextMessageBufferSize(int max);

    /**
     * Return the set of Extensions installed in the container.
     *
     * @return the set of extensions.
     */
    Set<Extension> getInstalledExtensions();
}



