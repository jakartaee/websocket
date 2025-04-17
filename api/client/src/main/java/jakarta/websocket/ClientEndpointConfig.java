/*
 * Copyright (c) 2018, 2025 Oracle and/or its affiliates and others.
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

/**
 * The ClientEndpointConfig is a special kind of endpoint configuration object that contains web socket configuration
 * information specific only to client endpoints. Developers deploying programmatic client endpoints can create
 * instances of this configuration by using a {@link ClientEndpointConfig.Builder}. Developers can override some of the
 * configuration operations by providing an implementation of {@link ClientEndpointConfig.Configurator}.
 *
 * @author dannycoward
 */
public interface ClientEndpointConfig extends EndpointConfig {

    /**
     * Return the ordered list of sub protocols a client endpoint would like to use, in order of preference, favorite
     * first that this client would like to use for its sessions. This list is used to generate the
     * Sec-WebSocket-Protocol header in the opening handshake for clients using this configuration. The first protocol
     * name is the most preferred. See <a href="http://tools.ietf.org/html/rfc6455#section-4.1">Client Opening
     * Handshake</a>.
     *
     * @return the list of the preferred subprotocols, the empty list if there are none
     */
    List<String> getPreferredSubprotocols();

    /**
     * Return the extensions, in order of preference, favorite first, that this client would like to use for its
     * sessions. These are the extensions that will be used to populate the Sec-WebSocket-Extensions header in the
     * opening handshake for clients using this configuration. The first extension in the list is the most preferred
     * extension. See <a href="http://tools.ietf.org/html/rfc6455#section-9.1">Negotiating Extensions</a>.
     *
     * @return the list of extensions, the empty list if there are none.
     */
    List<Extension> getExtensions();

    /**
     * Return the SSLContext to be used to establish a WebSocket (wss) connection to the server. The SSLContext will
     * have initialised. For insecure WebSocket (ws) connections, this will be {@code null}. If there is an existing
     * connection to the server that uses the same SSLContext and that connection supports multiplexing WebSocket
     * connections then the container may choose to re-use that connection rather than creating a new one. Containers
     * may provide container specific configuration to control this behaviour.
     *
     * @return the SSLContext to use to establish a secure connection to the server or {@code null} if an insecure
     *         connection should be established
     *
     * @since WebSocket 2.1
     */
    SSLContext getSSLContext();

    /**
     * Return the custom configurator for this configuration. If the developer did not provide one, the platform default
     * configurator is returned.
     *
     * @return the configurator in use with this configuration.
     */
    public ClientEndpointConfig.Configurator getConfigurator();

    /**
     * The Configurator class may be extended by developers who want to provide custom configuration algorithms, such as
     * intercepting the opening handshake, or providing arbitrary methods and algorithms that can be accessed from each
     * endpoint instance configured with this configurator.
     *
     */
    public class Configurator {

        /**
         * Default, no-arg constructor.
         */
        public Configurator() {
            // Explicitly defined to silence Java compilation warning.
        }

        /**
         * This method is called by the implementation after it has formulated the handshake request that will be used
         * to initiate the connection to the server, but before it has sent any part of the request. This allows the
         * developer to inspect and modify the handshake request headers prior to the start of the handshake
         * interaction.
         *
         * @param headers the mutable map of handshake request headers the implementation is about to send to start the
         *                handshake interaction.
         */
        public void beforeRequest(Map<String, List<String>> headers) {

        }

        /**
         * This method is called by the implementation after it has received a handshake response from the server as a
         * result of a handshake interaction it initiated. The developer may implement this method in order to inspect
         * the returning handshake response.
         *
         * @param hr the handshake response sent by the server.
         */
        public void afterResponse(HandshakeResponse hr) {

        }
    }

    /**
     * The ClientEndpointConfig.Builder is a class used for creating {@link ClientEndpointConfig} objects for the
     * purposes of deploying a client endpoint. Here are some examples: Building a plain configuration with no encoders,
     * decoders, subprotocols or extensions. <code>
     * ClientEndpointConfig cec = ClientEndpointConfig.Builder.create().build();
     * </code>
     *
     * Building a configuration with no subprotocols and a custom configurator.
     *
     * <pre>
     * <code>
     * ClientEndpointConfig customCec = ClientEndpointConfig.Builder.create()
     *         .preferredSubprotocols(mySubprotocols)
     *         .configurator(new MyClientConfigurator())
     *         .build();
     * </code>
     * </pre>
     *
     *
     * @author dannycoward
     */
    public final class Builder {
        private List<String> preferredSubprotocols = Collections.emptyList();
        private List<Extension> extensions = Collections.emptyList();
        private List<Class<? extends Encoder>> encoders = Collections.emptyList();
        private List<Class<? extends Decoder>> decoders = Collections.emptyList();
        private SSLContext sslContext = null;
        private ClientEndpointConfig.Configurator clientEndpointConfigurator = new ClientEndpointConfig.Configurator() {

        };

        // use create()
        private Builder() {
        }

        /**
         * Creates a new builder object with no subprotocols, extensions, encoders, decoders and a {@code null}
         * configurator.
         *
         * @return a new builder object.
         */
        public static ClientEndpointConfig.Builder create() {
            return new ClientEndpointConfig.Builder();
        }

        /**
         * Builds a configuration object using the attributes set on this builder.
         *
         * @return a new configuration object.
         */
        public ClientEndpointConfig build() {
            return new DefaultClientEndpointConfig(this.preferredSubprotocols, this.extensions, this.encoders,
                    this.decoders, this.sslContext, this.clientEndpointConfigurator);
        }

        /**
         * Sets the configurator object for the configuration this builder will build.
         *
         * @param clientEndpointConfigurator the configurator
         * @return the builder instance
         */
        public ClientEndpointConfig.Builder configurator(ClientEndpointConfig.Configurator clientEndpointConfigurator) {
            this.clientEndpointConfigurator = clientEndpointConfigurator;
            return this;
        }

        /**
         * Set the preferred sub protocols for the configuration this builder will build. The list is treated in order
         * of preference, favorite first, that this client would like to use for its sessions.
         *
         * @param preferredSubprotocols the preferred subprotocol names.
         * @return the builder instance
         */
        public ClientEndpointConfig.Builder preferredSubprotocols(List<String> preferredSubprotocols) {
            this.preferredSubprotocols = (preferredSubprotocols == null) ? new ArrayList<>()
                    : preferredSubprotocols;
            return this;
        }

        /**
         * Set the extensions for the configuration this builder will build. The list is treated in order of preference,
         * favorite first, that the client would like to use for its sessions.
         *
         * @param extensions the extensions
         * @return the builder instance
         */
        public ClientEndpointConfig.Builder extensions(List<Extension> extensions) {
            this.extensions = (extensions == null) ? new ArrayList<>() : extensions;
            return this;
        }

        /**
         * Assign the list of encoder implementation classes the client will use.
         *
         * @param encoders the encoder implementation classes
         * @return the builder instance
         */
        public ClientEndpointConfig.Builder encoders(List<Class<? extends Encoder>> encoders) {
            this.encoders = (encoders == null) ? new ArrayList<>() : encoders;
            return this;
        }

        /**
         * Assign the list of decoder implementation classes the client will use.
         *
         * @param decoders the decoder implementation classes
         * @return this builder instance
         */
        public ClientEndpointConfig.Builder decoders(List<Class<? extends Decoder>> decoders) {
            this.decoders = (decoders == null) ? new ArrayList<>() : decoders;
            return this;
        }

        /**
         * Assign the SSLContext to be used when connection to the WebSocket server. If there is an existing connection
         * to the server that uses the same SSLContext and that connection supports multiplexing WebSocket connections
         * then the container may choose to re-use that connection rather than creating a new one. Containers may
         * provide container specific configuration to control this behaviour.
         *
         * @param sslContext The SSLContext which must be initialised for secure WebSocket (wss) connections or
         *                  {@code null} for insecure WebSocket (ws) connections.
         * @return this builder instance
         *
         * @since WebSocket 2.1
         */
        public ClientEndpointConfig.Builder sslContext(SSLContext sslContext) {
            this.sslContext = sslContext;
            return this;
        }
    }
}
