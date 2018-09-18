/*
 * Copyright (c) 2012, 2018 Oracle and/or its affiliates and others.
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

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;

/**
 * The Decoder interface holds member interfaces that define how a developer can provide the web socket container a way
 * web socket messages into developer defined custom objects. The websocket implementation creates a new instance of the
 * decoder per endpoint instance per connection. The lifecycle of the Decoder instance is governed by the container
 * calls to the {@link Decoder#init(javax.websocket.EndpointConfig)} and {@link Decoder#destroy() } methods.
 *
 * @author dannycoward
 */
public interface Decoder {

    /**
     * This method is called with the endpoint configuration object of the endpoint this decoder is intended for when it
     * is about to be brought into service.
     *
     * @param config the endpoint configuration object when being brought into service
     */
    void init(EndpointConfig config);

    /**
     * This method is called when the decoder is about to be removed from service in order that any resources the
     * decoder used may be closed gracefully.
     */
    void destroy();

    /**
     * This interface defines how a custom object (of type T) is decoded from a web socket message in the form of a byte
     * buffer.
     *
     * @param <T> The type of the object that is decoded
     */
    interface Binary<T> extends Decoder {

        /**
         * Decode the given bytes into an object of type T.
         *
         * @param bytes the bytes to be decoded.
         * @return the decoded object.
         *
         * @throws DecodeException If the provided bytes cannot be decoded to type T
         */
        T decode(ByteBuffer bytes) throws DecodeException;

        /**
         * Answer whether the given bytes can be decoded into an object of type T.
         *
         * @param bytes the bytes to be decoded.
         * @return whether or not the bytes can be decoded by this decoder.
         */
        boolean willDecode(ByteBuffer bytes);

    }

    /**
     * This interface defines how a custom object is decoded from a web socket message in the form of a binary stream.
     *
     * @param <T> The type of the object that is decoded
     */
    interface BinaryStream<T> extends Decoder {

        /**
         * Decode the given bytes read from the input stream into an object of type T.
         *
         * @param is the input stream carrying the bytes.
         * @return the decoded object.
         *
         * @throws DecodeException If the provided input stream cannot be decoded to type T
         * @throws IOException If an error occurs reading the input stream
         */
        T decode(InputStream is) throws DecodeException, IOException;

    }

    /**
     * This interface defines how a custom object is decoded from a web socket message in the form of a string.
     *
     * @param <T> The type of the object that is decoded
     */
    interface Text<T> extends Decoder {
        /**
         * Decode the given String into an object of type T.
         *
         * @param s string to be decoded.
         * @return the decoded message as an object of type T
         *
         * @throws DecodeException If the provided string cannot be decoded to type T
         */
        T decode(String s) throws DecodeException;

        /**
         * Answer whether the given String can be decoded into an object of type T.
         *
         * @param s the string being tested for decodability.
         * @return whether this decoder can decoded the supplied string.
         */
        boolean willDecode(String s);

    }

    /**
     * This interface defines how a custom object of type T is decoded from a web socket message in the form of a
     * character stream.
     *
     * @param <T> The type of the object that is decoded
     */
    interface TextStream<T> extends Decoder {
        /**
         * Reads the websocket message from the implementation provided Reader and decodes it into an instance of the
         * supplied object type.
         *
         * @param reader the reader from which to read the web socket message.
         * @return the instance of the object that is the decoded web socket message.
         *
         * @throws DecodeException If the data from the provided reader cannot be decoded to type T
         * @throws IOException If an error occurs reading from the reader
         */
        T decode(Reader reader) throws DecodeException, IOException;

    }
}
