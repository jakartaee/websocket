/*
 * Copyright (c) 2025 Contributors to the Eclipse Foundation
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

/**
 * Defines the configuration of an extension for an endpoint.
 * <p>
 * For a client endpoint, this represents an extension configuration that the client would like to use if supported by
 * both the client container and the server container. Extensions will be used in the order they are listed. The same
 * extension may appear multiple times with different configurations. Where an extension is listed with multiple
 * configurations, the configurations must be provided in preference order.
 * <p>
 * For a server endpoint, this represents an extension configuration that the server is prepared to accept if the client
 * requests it and the server container supports it. If the extension name is prefaced with {@code &lt;}, it represents
 * an extension that the server does not wish to accept regardless of whether the client requests it and/or the server
 * container supports it.
 */
public @interface ExtensionConfig {

    /**
     * The name of the extension.
     * <p>
     * For server endpoints only, the name may be prefaced by a single {@code &lt;} character to signal any client
     * requests for that extension should be rejected regardless of whether the server container supports the extension
     * or not.
     *
     * @return the name of the extension
     */
    String name();

    /**
     * The parameters associated with this configuration.
     * <p>
     * If a parameter associated with the extension is not defined, it means that any value is acceptable to the
     * endpoint for that parameter.
     *
     * @return the parameters associated with this configuration, if any
     */
    Parameter[] parameters() default {};

    @interface Parameter {

        /**
         * The name of the parameter.
         *
         * @return the parameter name
         */
        String name();

        /**
         * The value of the parameter.
         * <p>
         * Parameters that have not been set or have not had an explicit value defined will return an empty string.
         *
         * @return the parameter value
         */
        String value() default "";
    }
}
