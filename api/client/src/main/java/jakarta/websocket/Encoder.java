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

package jakarta.websocket;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;

/**
 * The Encoder interface defines how developers can provide a way to convert their custom objects into web socket
 * messages. The Encoder interface contains subinterfaces that allow encoding algorithms to encode custom objects to:
 * text, binary data, character stream and write to an output stream. The websocket implementation creates a new
 * instance of the encoder per endpoint instance per connection. This means that each encoder instance has at most one
 * calling thread at a time. The lifecycle of the Encoder instance is governed by the container calls to the
 * {@link Encoder#init(jakarta.websocket.EndpointConfig)} and {@link Encoder#destroy() } methods.
 *
 * @author dannycoward
 */
public interface Encoder {

    /**
     * This method is called with the endpoint configuration object of the endpoint this encoder is intended for when it
     * is about to be brought into service.
     *
     * @param config the endpoint configuration object when being brought into service
     */
    void init(EndpointConfig config);

    /**
     * This method is called when the encoder is about to be removed from service in order that any resources the
     * encoder used may be closed gracefully.
     */
    void destroy();

    /**
     * This interface defines how to provide a way to convert a custom object into a text message.
     *
     * @param <T> The type of the custom developer object that this Encoder can encode into a String.
     */
    interface Text<T> extends Encoder {
        /**
         * Encode the given object into a String.
         *
         * @param object the object being encoded.
         * @return the encoded object as a string.
         *
         * @throws EncodeException The provided object could not be encoded as a string
         */
        String encode(T object) throws EncodeException;

    }

    /**
     * This interface may be implemented by encoding algorithms that want to write the encoded object to a character
     * stream.
     *
     * @param <T> the type of the object this encoder can encode to a CharacterStream.
     */
    interface TextStream<T> extends Encoder {
        /**
         * Encode the given object to a character stream writing it to the supplied Writer. Implementations of this
         * method may use the EncodeException to indicate a failure to convert the supplied object to an encoded form,
         * and may use the IOException to indicate a failure to write the data to the supplied stream.
         *
         * @param object the object to be encoded.
         * @param writer the writer provided by the web socket runtime to write the encoded data.
         * @throws EncodeException if there was an error encoding the object due to its state.
         * @throws IOException     if there was an exception writing to the writer.
         */
        void encode(T object, Writer writer) throws EncodeException, IOException;
    }

    /**
     * This interface defines how to provide a way to convert a custom object into a binary message.
     *
     * @param <T> The type of the custom object that this Encoder can encoder to a ByteBuffer.
     */
    interface Binary<T> extends Encoder {
        /**
         * Encode the given object into a byte array.
         *
         * @param object the object being encoded.
         * @return the binary data.
         *
         * @throws EncodeException The provided object could not be encoded to a byte buffer
         */
        ByteBuffer encode(T object) throws EncodeException;
    }

    /**
     * This interface may be implemented by encoding algorithms that want to write the encoded object to a binary
     * stream.
     *
     * @param <T> the type of the object this encoder can encode.
     */
    interface BinaryStream<T> extends Encoder {
        /**
         * Encode the given object into a binary stream written to the implementation provided OutputStream.
         *
         * @param object the object being encoded.
         * @param os     the output stream where the encoded data is written.
         *
         * @throws EncodeException The provided object could not be encoded to an output stream
         * @throws IOException If an error occurred writing to the output stream
         */
        void encode(T object, OutputStream os) throws EncodeException, IOException;
    }
}
