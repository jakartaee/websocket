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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation may be used to annotate method parameters on server endpoints
 * where a URI-template has been used in the path-mapping of the {@link ServerEndpoint}
 * annotation. The method parameter may be of type String, any Java primitive
 * type or any boxed version thereof. If a client URI matches the URI-template,
 * but the requested path parameter cannot be decoded, then the websocket's error
 * handler will be called.
 *
 * <p>For example:-
 * <pre><code>
 * &#64;ServerEndpoint("/bookings/{guest-id}")
 * public class BookingServer {
 * 
 *     &#64;OnMessage
 *     public void processBookingRequest(@PathParam("guest-id") String guestID, String message, Session session) {
 *         // process booking from the given guest here
 *     }
 * }
 * </code></pre>
 * 
 * <p>For example:-
 * <pre><code>
 * &#64;ServerEndpoint("/rewards/{vip-level}")
 * public class RewardServer {
 * 
 *     &#64;OnMessage
 *     public void processReward(@PathParam("vip-level") Integer vipLevel, String message, Session session) {
 *         // process reward here
 *     }
 * }
 * </code></pre>
 *
 * @author dannycoward
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface PathParam {

    /**
     * The name of the variable used in the URI-template. If the name does
     * not match a path variable in the URI-template, the value of the method parameter
     * this annotation annotates is {@code null}.
     *
     * @return the name of the variable used in the URI-template.
     */
    public String value();
}
