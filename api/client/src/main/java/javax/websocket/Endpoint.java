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


/** 
 * The Web Socket Endpoint represents an object that can handle websocket conversations. 
 * Developers may extend this class in order to implement a programmatic websocket 
 * endpoint. The Endpoint class holds lifecycle methods that may be 
 * overridden to intercept websocket open, error and close events. By implementing 
 * the {@link Endpoint#onOpen(javax.websocket.Session, javax.websocket.EndpointConfig) onOpen} method, the programmatic endpoint gains access to the {@link Session} object, 
 * to which the developer may add {@link MessageHandler} implementations in order to 
 * intercept incoming websocket messages. Each instance 
 * of a websocket endpoint is guaranteed not to be called by more than one thread 
 * at a time per active connection.
 *
 * <p>If deployed as a client endpoint, it will be instantiated once for the 
 * single connection to the server. 
 *
 * <p>When deployed as a server endpoint, the implementation uses the 
 * {@link javax.websocket.server.ServerEndpointConfig.Configurator#getEndpointInstance} 
 * method to obtain the 
 * endpoint instance it will use for each new client connection.  If the developer uses 
 * the default {@link javax.websocket.server.ServerEndpointConfig.Configurator}, 
 * there will be precisely one 
 * endpoint instance per active client connection. Consequently, in this typical 
 * case, when implementing/overriding the methods of Endpoint, the developer is 
 * guaranteed that there will be at most one thread calling each endpoint instance
 * at a time.
 * 
 * <p>If the developer provides a custom {@link javax.websocket.server.ServerEndpointConfig.Configurator}
 * which overrides the default policy for endpoint instance creation, for example, 
 * using a single Endpoint instance for multiple client connections, the developer
 * may need to write code that can execute concurrently.
 *
 * <p>Here is an example of a simple endpoint that echoes any incoming text message back to the sender.
 * <pre><code>
 * public class EchoServer extends Endpoint {
 *
 *     public void onOpen(Session session, EndpointConfig config) {
 *         final RemoteEndpoint remote = session.getBasicRemote();
 *         session.addMessageHandler(String.class, new MessageHandler.Whole&lt;String>() {
 *             public void onMessage(String text) {
 *                 try {
 *                     remote.sendString("Got your message (" + text + "). Thanks !");
 *                 } catch (IOException ioe) {
 *                     // handle send failure here
 *                 }
 *             }
 *         });
 *     }
 *
 * }
 * </code></pre>
 *
 * @author dannycoward
 */
public abstract class Endpoint {

    /**
     * Developers must implement this method to be notified when a new conversation has
     * just begun.
     *
     * @param session the session that has just been activated.
     * @param config  the configuration used to configure this endpoint.
     */
    public abstract void onOpen(Session session, EndpointConfig config);

    /**
     * This method is called immediately prior to the session with the remote 
     * peer being closed. It is called whether the session is being closed 
     * because the remote peer initiated a close and sent a close frame, or 
     * whether the local websocket container or this endpoint requests to close 
     * the session. The developer may take this last opportunity to retrieve
     * session attributes such as the ID, or any application data it holds before 
     * it becomes unavailable after the completion of the method. Developers should
     * not attempt to modify the session from within this method, or send new 
     * messages from this call as the underlying 
     * connection will not be able to send them at this stage.
     *
     * @param session     the session about to be closed.
     * @param closeReason the reason the session was closed.
     */
    public void onClose(Session session, CloseReason closeReason) {
    }

    /**
     * Developers may implement this method when the web socket session
     * creates some kind of error that is not modeled in the web socket protocol. This may for example
     * be a notification that an incoming message is too big to handle, or that the incoming message could not be encoded.
     *
     * <p>There are a number of categories of exception that this method is (currently) defined to handle:
     * <ul>
     * <li>connection problems, for example, a socket failure that occurs before
     * the web socket connection can be formally closed. These are modeled as 
     * {@link SessionException}s</li>
     * <li>runtime errors thrown by developer created message handlers calls.</li>
     * <li>conversion errors encoding incoming messages before any message handler has been called. These
     * are modeled as {@link DecodeException}s</li>
     * </ul>
     *
     * @param session the session in use when the error occurs.
     * @param thr     the throwable representing the problem.
     */
    public void onError(Session session, Throwable thr) {
    }
}
