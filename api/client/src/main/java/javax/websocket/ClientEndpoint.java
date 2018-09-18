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

package javax.websocket;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The ClientEndpoint annotation a class level annotation is used to denote that a POJO is a web socket client and can
 * be deployed as such. Similar to {@link javax.websocket.server.ServerEndpoint}, POJOs that are annotated with this
 * annotation can have methods that, using the web socket method level annotations, are web socket lifecycle methods.
 * <p>
 * For example:
 *
 * <pre>
 * <code>
 * &#64;ClientEndpoint(subprotocols="chat")
 * public class HelloServer {
 *
 *     &#64;OnMessage
 *     public void processMessageFromServer(String message, Session session) {
 *         System.out.println("Message came from the server ! " + message);
 *     }
 *
 * }
 * </code>
 * </pre>
 *
 * @author dannycoward
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ClientEndpoint {

    /**
     * The names of the subprotocols this client supports.
     *
     * @return the array of names of the subprotocols.
     */
    String[] subprotocols() default {};

    /**
     * The array of Java classes that are to act as Decoders for messages coming into the client.
     *
     * @return the array of decoders.
     */
    Class<? extends Decoder>[] decoders() default {};

    /**
     * The array of Java classes that are to act as Encoders for messages sent by the client.
     *
     * @return the array of decoders.
     */
    Class<? extends Encoder>[] encoders() default {};

    /**
     * An optional custom configurator class that the developer would like to use to provide custom configuration of new
     * instances of this endpoint. The implementation creates a new instance of the configurator per logical endpoint.
     *
     * @return the custom configurator class, or ClientEndpointConfigurator.class if none was provided in the
     *         annotation.
     */
    public Class<? extends ClientEndpointConfig.Configurator> configurator() default ClientEndpointConfig.Configurator.class;
}
