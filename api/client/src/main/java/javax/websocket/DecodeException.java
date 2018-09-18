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

import java.nio.ByteBuffer;

/**
 * A general exception that occurs when trying to decode a custom object from a text or binary message.
 *
 * @author dannycoward
 */
public class DecodeException extends Exception {
    private final ByteBuffer bb;
    private final String encodedString;
    private static final long serialVersionUID = 006;

    /**
     * Constructor with the binary data that could not be decoded, and the reason why it failed to be, and the cause.
     * The buffer may represent the whole message, or the part of the message most relevant to the decoding error,
     * depending whether the application is using one of the streaming methods or not.
     *
     * @param bb      the byte buffer containing the (part of) the message that could not be decoded.
     * @param message the reason for the failure.
     * @param cause   the cause of the error.
     */
    public DecodeException(ByteBuffer bb, String message, Throwable cause) {
        super(message, cause);
        this.encodedString = null;
        this.bb = bb;
    }

    /**
     * Constructor with the text data that could not be decoded, and the reason why it failed to be, and the cause. The
     * encoded string may represent the whole message, or the part of the message most relevant to the decoding error,
     * depending whether the application is using one of the streaming methods or not.
     *
     * @param encodedString the string representing the (part of) the message that could not be decoded.
     * @param message       the reason for the failure.
     * @param cause         the cause of the error.
     */
    public DecodeException(String encodedString, String message, Throwable cause) {
        super(message, cause);
        this.encodedString = encodedString;
        this.bb = null;
    }

    /**
     * Constructs a DecodedException with the given ByteBuffer that cannot be decoded, and reason why. The buffer may
     * represent the whole message, or the part of the message most relevant to the decoding error, depending whether
     * the application is using one of the streaming methods or not.
     *
     * @param bb      the byte buffer containing the (part of) the message that could not be decoded.
     * @param message the reason for the failure.
     */
    public DecodeException(ByteBuffer bb, String message) {
        super(message);
        this.encodedString = null;
        this.bb = bb;
    }

    /**
     * Constructs a DecodedException with the given encoded string that cannot be decoded, and reason why. The encoded
     * string may represent the whole message, or the part of the message most relevant to the decoding error, depending
     * whether the application is using one of the streaming methods or not.
     *
     * @param encodedString the string representing the (part of) the message that could not be decoded.
     * @param message       the reason for the failure.
     */
    public DecodeException(String encodedString, String message) {
        super(message);
        this.encodedString = encodedString;
        this.bb = null;
    }

    /**
     * Return the ByteBuffer containing either the whole message, or the partial message, that could not be decoded, or
     * {@code null} if this exception arose from a failure to decode a text message.
     *
     * @return the binary data not decoded or {@code null} for text message failures.
     */
    public ByteBuffer getBytes() {
        return this.bb;
    }

    /**
     * Return the encoded string that is either the whole message, or the partial message that could not be decoded, or
     * {@code null} if this exception arose from a failure to decode a binary message..
     *
     * @return the text not decoded or {@code null} for binary message failures.
     */
    public String getText() {
        return this.encodedString;
    }
}
