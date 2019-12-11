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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.websocket.Decoder;
import jakarta.websocket.Encoder;
import jakarta.websocket.Endpoint;
import jakarta.websocket.Extension;

/**
 * The DefaultServerEndpointConfig is a concrete class that embodies all the configuration parameters for an endpoint
 * that is to be published as a server endpoint. Developers may subclass this class in order to override the
 * configuration behavior.
 *
 * @author dannycoward
 */
final class DefaultServerEndpointConfig implements ServerEndpointConfig {
    private String path;
    private Class<?> endpointClass;
    private List<String> subprotocols;
    private List<Extension> extensions;
    private List<Class<? extends Encoder>> encoders;
    private List<Class<? extends Decoder>> decoders;
    private Map<String, Object> userProperties = new HashMap<String, Object>();
    private ServerEndpointConfig.Configurator serverEndpointConfigurator;

    // The builder ensures nothing except configurator can be {@code null}.
    DefaultServerEndpointConfig(Class<?> endpointClass, String path, List<String> subprotocols,
            List<Extension> extensions, List<Class<? extends Encoder>> encoders,
            List<Class<? extends Decoder>> decoders, ServerEndpointConfig.Configurator serverEndpointConfigurator) {
        this.path = path;
        this.endpointClass = endpointClass;
        this.subprotocols = Collections.unmodifiableList(subprotocols);
        this.extensions = Collections.unmodifiableList(extensions);
        this.encoders = Collections.unmodifiableList(encoders);
        this.decoders = Collections.unmodifiableList(decoders);
        if (serverEndpointConfigurator == null) {
            this.serverEndpointConfigurator = ServerEndpointConfig.Configurator.fetchContainerDefaultConfigurator();
        } else {
            this.serverEndpointConfigurator = serverEndpointConfigurator;
        }
    }

    /**
     * Returns the class of the Endpoint that this configuration configures.
     *
     * @return the class of the Endpoint.
     */
    @Override
    public Class<?> getEndpointClass() {
        return this.endpointClass;
    }

    /**
     * Creates a server configuration with the given path
     *
     * @param path the URI or URI template.
     */
    DefaultServerEndpointConfig(Class<? extends Endpoint> endpointClass, String path) {
        this.path = path;
        this.endpointClass = endpointClass;
    }

    /**
     * Return the Encoder implementation classes configured. These will be used by the container to encode outgoing
     * messages.
     *
     * @return the encoder implementation classes, in an unmodifiable list, empty if there are none.
     */
    @Override
    public List<Class<? extends Encoder>> getEncoders() {
        return this.encoders;
    }

    /**
     * Return the Decoder implementation classes configured. These will be used by the container to decode incoming
     * messages into the expected custom objects on MessageHandler callbacks.
     *
     * @return the decoder implementation classes, in an unmodifiable list.
     */
    @Override
    public List<Class<? extends Decoder>> getDecoders() {
        return this.decoders;
    }

    /**
     * Return the path of this server configuration. The path is a relative URI or URI-template.
     *
     * @return the path
     */
    @Override
    public String getPath() {
        return path;
    }

    /**
     * Return the ServerEndpointConfigurator
     *
     * @return the ServerEndpointConfigurator
     */
    @Override
    public ServerEndpointConfig.Configurator getConfigurator() {
        return this.serverEndpointConfigurator;
    }

    /**
     * Editable map of user properties.
     */
    @Override
    public final Map<String, Object> getUserProperties() {
        return this.userProperties;
    }

    @Override
    public final List<String> getSubprotocols() {
        return this.subprotocols;
    }

    @Override
    public final List<Extension> getExtensions() {
        return this.extensions;
    }

}
