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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.websocket.Decoder;
import javax.websocket.Encoder;

/**
 * This class level annotation declares that the class it decorates is a web socket endpoint that will be deployed and
 * made available in the URI-space of a web socket server. The annotation allows the developer to define the URL (or URI
 * template) which this endpoint will be published, and other important properties of the endpoint to the websocket
 * runtime, such as the encoders it uses to send messages.
 *
 * <p>
 * The annotated class must have a public no-arg constructor.
 *
 * <p>
 * For example:
 *
 * <pre>
 * <code>
 * &#64;ServerEndpoint("/hello");
 * public class HelloServer {
 *
 *     &#64;OnMessage
 *     public void processGreeting(String message, Session session) {
 *         System.out.println("Greeting received:" + message);
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
public @interface ServerEndpoint {

    /**
     * The URI or URI-template, level-1 (<a href="http://tools.ietf.org/html/rfc6570">See RFC 6570</a>) where the
     * endpoint will be deployed. The URI us relative to the root of the web socket container and must begin with a
     * leading "/". Trailing "/"'s are ignored. Examples:
     *
     * <pre>
     * <code>
     * &#64;ServerEndpoint("/chat")
     * &#64;ServerEndpoint("/chat/{user}")
     * &#64;ServerEndpoint("/booking/{privilege-level}")
     * </code>
     * </pre>
     *
     * @return the URI or URI-template
     */
    public String value();

    /**
     * The ordered array of web socket protocols this endpoint supports. For example, {"superchat", "chat"}.
     *
     * @return the subprotocols.
     */
    public String[] subprotocols() default {};

    /**
     * The ordered array of decoder classes this endpoint will use. For example, if the developer has provided a
     * MysteryObject decoder, this endpoint will be able to receive MysteryObjects as web socket messages. The websocket
     * runtime will use the first decoder in the list able to decode a message, ignoring the remaining decoders.
     *
     * @return the decoders.
     */
    public Class<? extends Decoder>[] decoders() default {};

    /**
     * The ordered array of encoder classes this endpoint will use. For example, if the developer has provided a
     * MysteryObject encoder, this class will be able to send web socket messages in the form of MysteryObjects. The
     * websocket runtime will use the first encoder in the list able to encode a message, ignoring the remaining
     * encoders.
     *
     * @return the encoders.
     */
    public Class<? extends Encoder>[] encoders() default {};

    /**
     * The optional custom configurator class that the developer would like to use to further configure new instances of
     * this endpoint. If no configurator class is provided, the implementation uses its own. The implementation creates
     * a new instance of the configurator per logical endpoint.
     *
     * @return the custom configuration class, or ServerEndpointConfig.Configurator.class if none was set in the
     *         annotation.
     */
    public Class<? extends ServerEndpointConfig.Configurator> configurator() default ServerEndpointConfig.Configurator.class;
}
