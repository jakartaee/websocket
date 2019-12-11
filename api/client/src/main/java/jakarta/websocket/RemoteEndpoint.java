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

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.util.concurrent.Future;

/**
 * The RemoteEndpoint object is supplied by the container and represents the 'other end' or peer of the Web Socket
 * conversation. Instances of the RemoteEndpoint are obtained from the Session using {@link Session#getBasicRemote()} or
 * {@link Session#getAsyncRemote()}. Objects of this kind include numerous ways to send web socket messages. There are
 * two kinds of RemoteEndpoint objects: RemoteEndpoint.Basic for synchronous sending of websocket messages, and
 * RemoteEndpoint.Async for sending messages asynchronously.
 *
 * <p>
 * There is no guarantee of the successful delivery of a web socket message to the peer, but if the action of sending a
 * message causes an error known to the container, the API throws it. RemoteEndpoints include a variety of ways to send
 * messages: by whole message, in parts, and in various data formats including websocket pings and pongs.
 *
 * <p>
 * Implementations may or may not support batching of messages. More detail of the expected semantics of implementations
 * that do support batching are laid out in {@link RemoteEndpoint#setBatchingAllowed(boolean)}.
 *
 * <p>
 * Note: Implementations may choose their own schemes for sending large messages in smaller parts. These schemes may or
 * may not bear a relationship to the underlying websocket dataframes in which the message is ultimately sent on the
 * wire.
 *
 * <p>
 * If the underlying connection is closed and methods on the RemoteEndpoint are attempted to be called, they will result
 * in an error being generated. For the methods that send messages, this will be an IOException, for the methods that
 * alter configuration of the endpoint, this will be runtime IllegalArgumentExceptions.
 *
 * @author dannycoward
 */
public interface RemoteEndpoint {

    /**
     * Indicate to the implementation that it is allowed to batch outgoing messages before sending. Not all
     * implementations support batching of outgoing messages. The default mode for RemoteEndpoints is false. If the
     * developer has indicated that batching of outgoing messages is permitted, then the developer must call
     * flushBatch() in order to be sure that all the messages passed into the send methods of this RemoteEndpoint are
     * sent. When batching is allowed, the implementations send operations are considered to have completed if the
     * message has been written to the local batch, in the case when there is still room in the batch for the message,
     * and are considered to have completed if the batch has been send to the peer and the remainder written to the new
     * batch, in the case when writing the message causes the batch to need to be sent. The blocking and asynchronous
     * send methods use this notion of completion in order to complete blocking calls, notify SendHandlers and complete
     * Futures respectively. When batching is allowed, if the developer has called send methods on this RemoteEndpoint
     * without calling flushBatch(), then the implementation may not have sent all the messages the developer has asked
     * to be sent. If the parameter value is false and the implementation has a batch of unsent messages, then the
     * implementation must immediately send the batch of unsent messages.
     *
     * @param allowed whether the implementation is allowed to batch messages.
     * @throws IOException if batching is being disabled and there are unsent messages this error may be thrown as the
     *                     implementation sends the batch of unsent messages if there is a problem.
     */
    void setBatchingAllowed(boolean allowed) throws IOException;

    /**
     * Return whether the implementation is allowed to batch outgoing messages before sending. The default mode for
     * RemoteEndpoints is false. The value may be changed by calling {@link #setBatchingAllowed(boolean)
     * setBatchingAllowed}.
     *
     * @return {@code true} if the implementation is allowed to batch outgoing messages before sending, otherwise
     *         {@code false}
     */
    boolean getBatchingAllowed();

    /**
     * This method is only used when batching is allowed for this RemoteEndpint. Calling this method forces the
     * implementation to send any unsent messages it has been batching.
     *
     * @throws IOException if the sending of any unsent messages failed
     */
    void flushBatch() throws IOException;

    /**
     * Send a Ping message containing the given application data to the remote endpoint. The corresponding Pong message
     * may be picked up using the MessageHandler.Pong handler.
     *
     * @param applicationData the data to be carried in the ping request.
     * @throws IOException              if the ping failed to be sent
     * @throws IllegalArgumentException if the applicationData exceeds the maximum allowed payload of 125 bytes
     */
    void sendPing(ByteBuffer applicationData) throws IOException, IllegalArgumentException;

    /**
     * Allows the developer to send an unsolicited Pong message containing the given application data in order to serve
     * as a unidirectional heartbeat for the session.
     *
     * @param applicationData the application data to be carried in the pong response.
     * @throws IOException              if the pong failed to be sent
     * @throws IllegalArgumentException if the applicationData exceeds the maximum allowed payload of 125 bytes
     */
    void sendPong(ByteBuffer applicationData) throws IOException, IllegalArgumentException;

    /**
     * This representation of the peer of a web socket conversation has the ability to send messages asynchronously. The
     * point of completion of the send is defined when all the supplied data has been written to the underlying
     * connection. The completion handlers for the asynchronous methods are always called with a different thread from
     * that which initiated the send.
     */
    interface Async extends RemoteEndpoint {

        /**
         * Return the number of milliseconds the implementation will timeout attempting to send a websocket message. A
         * non-positive number indicates the implementation will not timeout attempting to send a websocket message
         * asynchronously. This value overrides the default value assigned in the WebSocketContainer.
         *
         * @return the timeout time in milliseconds.
         */
        long getSendTimeout();

        /**
         * Sets the number of milliseconds the implementation will timeout attempting to send a websocket message. A
         * non-positive number indicates the implementation will not timeout attempting to send a websocket message
         * asynchronously. This value overrides the default value assigned in the WebSocketContainer.
         *
         * @param timeoutmillis The number of milliseconds this RemoteEndpoint will wait before timing out an incomplete
         *                      asynchronous message send.
         */
        void setSendTimeout(long timeoutmillis);

        /**
         * Initiates the asynchronous transmission of a text message. This method returns before the message is
         * transmitted. Developers provide a callback to be notified when the message has been transmitted. Errors in
         * transmission are given to the developer in the SendResult object.
         *
         * @param text    the text being sent.
         * @param handler the handler which will be notified of progress.
         * @throws IllegalArgumentException if the text or the handler is {@code null}.
         */
        void sendText(String text, SendHandler handler);

        /**
         * Initiates the asynchronous transmission of a text message. This method returns before the message is
         * transmitted. Developers use the returned Future object to track progress of the transmission. The Future's
         * get() method returns {@code null} upon successful completion. Errors in transmission are wrapped in the
         * {@link java.util.concurrent.ExecutionException} thrown when querying the Future object.
         *
         * @param text the text being sent.
         * @return the Future object representing the send operation.
         * @throws IllegalArgumentException if the text is {@code null}.
         */
        Future<Void> sendText(String text);

        /**
         * Initiates the asynchronous transmission of a binary message. This method returns before the message is
         * transmitted. Developers use the returned Future object to track progress of the transmission. The Future's
         * get() method returns {@code null} upon successful completion. Errors in transmission are wrapped in the
         * {@link java.util.concurrent.ExecutionException} thrown when querying the Future object.
         *
         * @param data the data being sent.
         * @return the Future object representing the send operation.
         * @throws IllegalArgumentException if the data is {@code null}.
         */
        Future<Void> sendBinary(ByteBuffer data);

        /**
         * Initiates the asynchronous transmission of a binary message. This method returns before the message is
         * transmitted. Developers provide a callback to be notified when the message has been transmitted. Errors in
         * transmission are given to the developer in the SendResult object.
         *
         * @param data    the data being sent, must not be {@code null}.
         * @param handler the handler that will be notified of progress, must not be {@code null}.
         * @throws IllegalArgumentException if either the data or the handler are {@code null}.
         */
        void sendBinary(ByteBuffer data, SendHandler handler);

        /**
         * Initiates the asynchronous transmission of a custom developer object. The developer will have provided an
         * encoder for this object type in the endpoint configuration. Containers will by default be able to encode java
         * primitive types and their object equivalents, otherwise the developer must have provided an encoder for the
         * object type in the endpoint configuration. Progress may be tracked using the Future object. The Future's
         * get() methods return {@code null} upon successful completion. Errors in transmission are wrapped in the
         * {@link java.util.concurrent.ExecutionException} thrown when querying the Future object.
         *
         * @param data the object being sent.
         * @return the Future object representing the send operation.
         * @throws IllegalArgumentException if the data is {@code null}.
         *
         */
        Future<Void> sendObject(Object data);

        /**
         * Initiates the asynchronous transmission of a custom developer object. Containers will by default be able to
         * encode java primitive types and their object equivalents, otherwise the developer must have provided an
         * encoder for the object type in the endpoint configuration. Developers are notified when transmission is
         * complete through the supplied callback object.
         *
         * @param data    the object being sent.
         * @param handler the handler that will be notified of progress, must not be {@code null}.
         * @throws IllegalArgumentException if either the data or the handler are {@code null}.
         */
        void sendObject(Object data, SendHandler handler);

    }

    /**
     * This representation of the peer of a web socket conversation has the ability to send messages synchronously. The
     * point of completion of the send is defined when all the supplied data has been written to the underlying
     * connection. The methods for sending messages on the RemoteEndpoint.Basic block until this point of completion is
     * reached, except for {@link RemoteEndpoint.Basic#getSendStream() getSendStream} and
     * {@link RemoteEndpoint.Basic#getSendWriter() getSendWriter} which present traditional blocking I/O streams to
     * write messages.
     *
     * <p>
     * If the websocket connection underlying this RemoteEndpoint is busy sending a message when a call is made to send
     * another one, for example if two threads attempt to call a send method concurrently, or if a developer attempts to
     * send a new message while in the middle of sending an existing one, the send method called while the connection is
     * already busy may throw an {@link java.lang.IllegalStateException}.
     */
    interface Basic extends RemoteEndpoint {

        /**
         * Send a text message, blocking until all of the message has been transmitted.
         *
         * @param text the message to be sent.
         * @throws IOException              if there is a problem delivering the message.
         * @throws IllegalArgumentException if the text is {@code null}.
         */
        void sendText(String text) throws IOException;

        /**
         * Send a binary message, returning when all of the message has been transmitted.
         *
         * @param data the message to be sent.
         * @throws IOException              if there is a problem delivering the message.
         * @throws IllegalArgumentException if the data is {@code null}.
         *
         */
        void sendBinary(ByteBuffer data) throws IOException;

        /**
         * Send a text message in parts, blocking until all of the message has been transmitted. The runtime reads the
         * message in order. Non-final parts of the message are sent with isLast set to false. The final part must be
         * sent with isLast set to true.
         *
         * @param partialMessage the parts of the message being sent.
         * @param isLast         Whether the partial message being sent is the last part of the message.
         * @throws IOException              if there is a problem delivering the message fragment.
         * @throws IllegalArgumentException if the partialMessage is {@code null}.
         */
        void sendText(String partialMessage, boolean isLast) throws IOException;

        /**
         * Send a binary message in parts, blocking until all of the message has been transmitted. The runtime reads the
         * message in order. Non-final parts are sent with isLast set to false. The final piece must be sent with isLast
         * set to true.
         *
         * @param partialByte the part of the message being sent.
         * @param isLast      Whether the partial message being sent is the last part of the message.
         * @throws IOException              if there is a problem delivering the partial message.
         * @throws IllegalArgumentException if the partialByte is {@code null}.
         */
        void sendBinary(ByteBuffer partialByte, boolean isLast) throws IOException; // or Iterable<byte[]>

        /**
         * Opens an output stream on which a binary message may be sent. The developer must close the output stream in
         * order to indicate that the complete message has been placed into the output stream.
         *
         * @return the output stream to which the message will be written.
         * @throws IOException if there is a problem obtaining the OutputStream to write the binary message.
         */
        OutputStream getSendStream() throws IOException;

        /**
         * Opens an character stream on which a text message may be sent. The developer must close the writer in order
         * to indicate that the complete message has been placed into the character stream.
         *
         * @return the writer to which the message will be written.
         * @throws IOException if there is a problem obtaining the Writer to write the text message.
         */
        Writer getSendWriter() throws IOException;

        /**
         * Sends a custom developer object, blocking until it has been transmitted. Containers will by default be able
         * to encode java primitive types and their object equivalents, otherwise the developer must have provided an
         * encoder for the object type in the endpoint configuration. A developer-provided encoder for a Java primitive
         * type overrides the container default encoder.
         *
         * @param data the object to be sent.
         * @throws IOException              if there is a communication error sending the message object.
         * @throws EncodeException          if there was a problem encoding the message object into the form of a native
         *                                  websocket message.
         * @throws IllegalArgumentException if the data parameter is {@code null}
         */
        void sendObject(Object data) throws IOException, EncodeException;
    }

}
