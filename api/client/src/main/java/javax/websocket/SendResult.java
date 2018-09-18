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

package javax.websocket;

/**
 * The result of asynchronously sending a web socket message. A SendResult is either ok indicating there was no problem,
 * or is not OK in which case there was a problem and it carries an exception to indicate what the problem was.
 *
 * @author dannycoward
 */
final public class SendResult {

    private final Throwable exception;
    private final boolean isOK;

    /**
     * Construct a SendResult carrying an exception.
     *
     * @param exception the exception causing a send failure.
     */
    public SendResult(Throwable exception) {
        this.exception = exception;
        this.isOK = false;
    }

    /**
     * Construct a SendResult signifying a successful send carrying no exception.
     */
    public SendResult() {
        this.exception = null;
        this.isOK = true;
    }

    /**
     * The problem sending the message.
     *
     * @return the problem or {@code null} if the send was successful.
     */
    public Throwable getException() {
        return exception;
    }

    /**
     * Determines if this result is ok or not.
     *
     * @return whether the send was successful or not.
     */
    public boolean isOK() {
        return this.isOK;
    }
}
