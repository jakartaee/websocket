/*
 * Copyright (c) 2018, 2023 Oracle and/or its affiliates and others.
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

/**
 * The result of asynchronously sending a web socket message. A SendResult is either ok indicating there was no problem,
 * or is not OK in which case there was a problem and it carries an exception to indicate what the problem was.
 *
 * @author dannycoward
 */
final public class SendResult {

    private final Session session;
    private final Throwable exception;
    private final boolean isOK;

    /**
     * Construct a SendResult carrying an exception.
     *
     * @param session   the WebSocket session in which the message was sent
     * @param exception the exception causing a send failure.
     */
    public SendResult(Session session, Throwable exception) {
        this.session = session;
        this.exception = exception;
        this.isOK = false;
    }

    /**
     * Construct a SendResult signifying a successful send carrying no exception.
     *
     * @param session   the WebSocket session in which the message was sent
     */
    public SendResult(Session session) {
        this.session = session;
        this.exception = null;
        this.isOK = true;
    }

    /**
     * Construct a SendResult carrying an exception.
     *
     * @param exception the exception causing a send failure.
     *
     * @deprecated Deprecated in WebSocket 2.2 and will be removed in a future version. Use
     *             {@link #SendResult(Session, Throwable)} as a replacement.
     */
    @Deprecated
    public SendResult(Throwable exception) {
        this.session = null;
        this.exception = exception;
        this.isOK = false;
    }

    /**
     * Construct a SendResult signifying a successful send carrying no exception.
     *
     * @deprecated Deprecated in WebSocket 2.2 and will be removed in a future version. Use
     *             {@link #SendResult(Session, Throwable)} as a replacement.
     */
    @Deprecated
    public SendResult() {
        this.session = null;
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

    /**
     * The WebSocket session in which the session was sent.
     *
     * @return the WebSocket session in which the session was sent or {@code null} if not known.
     */
    public Session getSession() {
        return session;
    }
}
