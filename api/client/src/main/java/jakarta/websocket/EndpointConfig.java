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

package jakarta.websocket;

import java.util.List;
import java.util.Map;

/**
 * The endpoint configuration contains all the information needed during the handshake process for this end point. All
 * endpoints specify, for example, a URI. In the case of a server endpoint, the URI signifies the URI to which the
 * endpoint will be mapped. In the case of a client application the URI signifies the URI of the server to which the
 * client endpoint will attempt to connect.
 *
 * @author dannycoward
 */
public interface EndpointConfig {

    /**
     * Return the Encoder implementation classes configured. These will be instantiated by the container to encode
     * custom objects passed into the send() methods on remote endpoints.
     *
     * @return the encoder implementation classes, an empty list if none.
     */
    List<Class<? extends Encoder>> getEncoders();

    /**
     * Return the Decoder implementation classes configured. These will be instantiated by the container to decode
     * incoming messages into the expected custom objects on {@link MessageHandler.Whole#onMessage(Object)} callbacks.
     *
     * @return the decoder implementation classes, the empty list if none.
     */
    List<Class<? extends Decoder>> getDecoders();

    /**
     * This method returns a modifiable Map that the developer may use to store application specific information
     * relating to the endpoint that uses this configuration instance. Web socket applications running on distributed
     * implementations of the web container should make any application specific objects stored here
     * java.io.Serializable, or the object may not be recreated after a failover.
     *
     * @return a modifiable Map of application data.
     */
    Map<String, Object> getUserProperties();
}
