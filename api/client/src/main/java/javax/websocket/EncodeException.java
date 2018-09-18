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

/**
 * A general exception that occurs when trying to encode a custom object to a string or binary message.
 *
 * @author dannycoward
 */
public class EncodeException extends Exception {
    private final Object object;
    private static final long serialVersionUID = 006;

    /**
     * Constructor with the object being encoded, and the reason why it failed to be.
     *
     * @param object  the object that could not be encoded.
     * @param message the reason for the failure.
     */
    public EncodeException(Object object, String message) {
        super(message);
        this.object = object;
    }

    /**
     * Constructor with the object being encoded, and the reason why it failed to be, and the cause.
     *
     * @param object  the object that could not be encoded.
     * @param message the reason for the failure.
     * @param cause   the cause of the problem.
     */
    public EncodeException(Object object, String message, Throwable cause) {
        super(message, cause);
        this.object = object;
    }

    /**
     * Return the Object that could not be encoded.
     *
     * @return the object.
     */
    public Object getObject() {
        return this.object;
    }
}
