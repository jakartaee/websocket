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

/**
 * Developers implement MessageHandlers in order to receive incoming messages during a web socket conversation. Each web
 * socket session uses no more than one thread at a time to call its MessageHandlers. This means that, provided each
 * message handler instance is used to handle messages for one web socket session, at most one thread at a time can be
 * calling any of its methods. Developers who wish to handle messages from multiple clients within the same message
 * handlers may do so by adding the same instance as a handler on each of the Session objects for the clients. In that
 * case, they will need to code with the possibility of their MessageHandler being called concurrently by multiple
 * threads, each one arising from a different client session.
 *
 * <p>
 * See {@link Endpoint} for a usage example.
 *
 * @author dannycoward
 */
public interface MessageHandler {

    /**
     * This kind of handler is notified by the container on arrival of a complete message. If the message is received in
     * parts, the container buffers it until it is has been fully received before this method is called.
     *
     * <p>
     * For handling incoming text messages, the allowed types for T are
     * <ul>
     * <li>{@link java.lang.String}</li>
     * <li>{@link java.io.Reader}</li>
     * <li>any developer object for which there is a corresponding {@link Decoder.Text} or {@link Decoder.TextStream}
     * configured</li>
     * </ul>
     *
     * <p>
     * For handling incoming binary messages, the allowed types for T are
     * <ul>
     * <li>{@link java.nio.ByteBuffer}</li>
     * <li>byte[]</li>
     * <li>{@link java.io.InputStream}</li>
     * <li>any developer object for which there is a corresponding {@link Decoder.Binary} or
     * {@link Decoder.BinaryStream} configured
     * </ul>
     *
     * <p>
     * For handling incoming pong messages, the type of T is {@link PongMessage}
     *
     * <p>
     * Developers should not continue to reference message objects of type {@link java.io.Reader},
     * {@link java.nio.ByteBuffer} or {@link java.io.InputStream} after the completion of the onMessage() call, since
     * they may be recycled by the implementation.
     *
     * @param <T> The type of the message object that this MessageHandler will consume.
     */
    interface Whole<T> extends MessageHandler {

        /**
         * Called when the message has been fully received.
         *
         * @param message the message data.
         */
        void onMessage(T message);
    }

    /**
     * This kind of handler is notified by the implementation as it becomes ready to deliver parts of a whole message.
     *
     * <p>
     * For handling parts of text messages, the type T is {@link java.lang.String}
     *
     * <p>
     * For handling parts of binary messages, the allowable types for T are
     * <ul>
     * <li>{@link java.nio.ByteBuffer}</li>
     * <li>byte[]</li>
     * </ul>
     *
     * <p>
     * Developers should not continue to reference message objects of type {@link java.nio.ByteBuffer} after the
     * completion of the onMessage() call, since they may be recycled by the implementation.
     *
     * <p>
     * Note: Implementations may choose their own schemes for delivering large messages in smaller parts through this
     * API. These schemes may or may not bear a relationship to the underlying websocket dataframes in which the message
     * is received off the wire.
     *
     * @param <T> The type of the object that represent pieces of the incoming message that this MessageHandler will
     *        consume.
     */
    interface Partial<T> extends MessageHandler {

        /**
         * Called when the next part of a message has been fully received.
         *
         * @param partialMessage the partial message data.
         * @param last           flag to indicate if this partialMessage is the last of the whole message being
         *                       delivered.
         */
        void onMessage(T partialMessage, boolean last);
    }
}
