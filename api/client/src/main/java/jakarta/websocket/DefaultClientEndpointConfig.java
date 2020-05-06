/*
 * Copyright (c) 2018, 2020 Oracle and/or its affiliates and others.
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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The DefaultClientEndpointConfig is a concrete implementation of a client configuration.
 *
 * @author dannycoward
 */
final class DefaultClientEndpointConfig implements ClientEndpointConfig {
    private List<String> preferredSubprotocols;
    private List<Extension> extensions;
    private List<Class<? extends Encoder>> encoders;
    private List<Class<? extends Decoder>> decoders;
    private Map<String, Object> userProperties = new HashMap<>();
    private ClientEndpointConfig.Configurator clientEndpointConfigurator;

    DefaultClientEndpointConfig(List<String> preferredSubprotocols, List<Extension> extensions,
            List<Class<? extends Encoder>> encoders, List<Class<? extends Decoder>> decoders,
            ClientEndpointConfig.Configurator clientEndpointConfigurator) {
        this.preferredSubprotocols = Collections.unmodifiableList(preferredSubprotocols);
        this.extensions = Collections.unmodifiableList(extensions);
        this.encoders = Collections.unmodifiableList(encoders);
        this.decoders = Collections.unmodifiableList(decoders);
        this.clientEndpointConfigurator = clientEndpointConfigurator;
    }

    /**
     * Return the protocols, in order of preference, favorite first, that this client would like to use for its
     * sessions.
     *
     * @return the preferred subprotocols.
     */
    @Override
    public List<String> getPreferredSubprotocols() {
        return this.preferredSubprotocols;
    }

    /**
     * Return the extensions, in order of preference, favorite first, that this client would like to use for its
     * sessions.
     *
     * @return the (unmodifiable) extension list.
     */
    @Override
    public List<Extension> getExtensions() {
        return this.extensions;
    }

    /**
     * Return the (unmodifiable) list of encoders this client will use.
     *
     * @return the encoder list.
     */
    @Override
    public List<Class<? extends Encoder>> getEncoders() {
        return this.encoders;
    }

    /**
     * Return the (unmodifiable) list of decoders this client will use.
     *
     * @return the decoders to use.
     */
    @Override
    public List<Class<? extends Decoder>> getDecoders() {
        return this.decoders;
    }

    /**
     * Editable map of user properties.
     */
    @Override
    public final Map<String, Object> getUserProperties() {
        return this.userProperties;
    }

    @Override
    public ClientEndpointConfig.Configurator getConfigurator() {
        return this.clientEndpointConfigurator;
    }

}
