/*
 * Copyright (c) 2017 Oracle and/or its affiliates. All rights reserved.
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

import java.util.List;

/**
 * A simple representation of a websocket extension as a name and map of extension parameters.
 *
 * @author dannycoward
 */
public interface Extension {

    /**
     * The name of the extension.
     *
     * @return the name of the extension.
     */
    String getName();

    /**
     * The extension parameters for this extension in the order
     * they appear in the http headers.
     *
     * @return The read-only Map of extension parameters belonging to this extension.
     */
    List<Parameter> getParameters();

    /**
     * This member interface defines a single websocket extension parameter.
     */
    interface Parameter {
        /**
         * Return the name of the extension parameter.
         *
         * @return the name of the parameter.
         */
        String getName();

        /**
         * Return the value of the extension parameter.
         *
         * @return the value of the parameter.
         */
        String getValue();
    }
}
