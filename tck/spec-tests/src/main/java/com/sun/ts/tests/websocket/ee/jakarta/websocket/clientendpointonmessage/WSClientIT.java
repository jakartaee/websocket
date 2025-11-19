/*
 * Copyright (c) 2013, 2025 Oracle and/or its affiliates and others.
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

package com.sun.ts.tests.websocket.ee.jakarta.websocket.clientendpointonmessage;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.sun.ts.tests.websocket.common.client.EndpointCallback;
import com.sun.ts.tests.websocket.common.client.SendMessageCallback;
import com.sun.ts.tests.websocket.common.client.WebSocketCommonClient;
import com.sun.ts.tests.websocket.common.impl.StringPongMessage;
import com.sun.ts.tests.websocket.common.stringbean.StringBean;
import com.sun.ts.tests.websocket.common.stringbean.StringBeanBinaryDecoder;
import com.sun.ts.tests.websocket.common.stringbean.StringBeanBinaryStreamDecoder;
import com.sun.ts.tests.websocket.common.stringbean.StringBeanTextDecoder;
import com.sun.ts.tests.websocket.common.stringbean.StringBeanTextStreamDecoder;
import com.sun.ts.tests.websocket.common.util.IOUtil;

import jakarta.websocket.CloseReason;
import jakarta.websocket.Session;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ws_wait;
 */
@ExtendWith(ArquillianExtension.class)
public class WSClientIT extends WebSocketCommonClient {
	private static final long serialVersionUID = 7948609603037530363L;

	@Deployment(testable = false)
	public static WebArchive createDeployment() throws IOException {

		WebArchive archive = ShrinkWrap.create(WebArchive.class, "ws_ee_clientendpointonmessage_web.war");
		archive.addClasses(OPS.class, WSCServer.class);
		archive.addClasses(StringBean.class, StringBeanBinaryDecoder.class, StringBeanBinaryStreamDecoder.class,
				StringBeanTextDecoder.class, StringBeanTextStreamDecoder.class);
		archive.addClasses(IOUtil.class);
		archive.addClasses(StringPongMessage.class);
		return archive;
	};

	static final String ECHO = "Echo message to be sent to server endpoint";

	@Override
	@BeforeEach
	public void setup() throws Exception {
		setCountDownLatchCount(3);
		setContextRoot("ws_ee_clientendpointonmessage_web");
		addClientCallback(new EndpointCallback() {
			@Override
			public void onClose(Session session, CloseReason closeReason) {
				getCountDownLatch().countDown();
				super.onClose(session, closeReason);
			}
		});
		super.setup();
	}

	/* Run test */

	// TEXT ------------------------------------------

	/*
	 * @testName: echoStringTest
	 *
	 * @assertion_ids: WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: Echo String
	 */
	@Test
	public void echoStringTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSStringClientEndpoint());
		setProperty(Property.CONTENT, ECHO);
		invoke(OPS.TEXT, ECHO);
	}

	/*
	 * @testName: echoIntTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-2; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo int
	 */
	@Test
	public void echoIntTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSPrimitiveIntClientEndpoint());
		int entity = Integer.MIN_VALUE;
		setEntity(entity);
		invoke(OPS.TEXT, String.valueOf(entity));
	}

	/*
	 * @testName: echoByteTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-2; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo byte
	 */
	@Test
	public void echoByteTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSPrimitiveByteClientEndpoint());
		byte entity = 123;
		setEntity(entity);
		invoke(OPS.TEXT, String.valueOf(entity));
	}

	/*
	 * @testName: echoCharTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-2; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo char
	 */
	@Test
	public void echoCharTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSPrimitiveCharClientEndpoint());
		char entity = 'E';
		setEntity(entity);
		invoke(OPS.TEXT, String.valueOf(entity));
	}

	/*
	 * @testName: echoBooleanTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-2; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo boolean
	 */
	@Test
	public void echoBooleanTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSPrimitiveBooleanClientEndpoint());
		boolean entity = true;
		setEntity(entity);
		invoke(OPS.TEXT, String.valueOf(entity));
	}

	/*
	 * @testName: echoShortTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-2; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo short
	 */
	@Test
	public void echoShortTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSPrimitiveShortClientEndpoint());
		short entity = -32100;
		setEntity(entity);
		invoke(OPS.TEXT, String.valueOf(entity));
	}

	/*
	 * @testName: echoDoubleTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-2; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo double
	 */
	@Test
	public void echoDoubleTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSPrimitiveDoubleClientEndpoint());
		double entity = -12345678.88;
		setEntity(entity);
		invoke(OPS.TEXT, String.valueOf(entity));
	}

	/*
	 * @testName: echoFloatTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-2; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo float
	 */
	@Test
	public void echoFloatTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSPrimitiveFloatClientEndpoint());
		float entity = -12345678.88f;
		setEntity(entity);
		invoke(OPS.TEXT, String.valueOf(entity));
	}

	/*
	 * @testName: echoLongTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-2; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo long
	 */
	@Test
	public void echoLongTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSPrimitiveLongClientEndpoint());
		long entity = Long.MIN_VALUE;
		setEntity(entity);
		invoke(OPS.TEXT, String.valueOf(entity));
	}

	/*
	 * @testName: echoFullIntTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-3; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo int
	 */
	@Test
	public void echoFullIntTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSFullLongClientEndpoint());
		Integer entity = Integer.MIN_VALUE;
		setEntity(entity);
		invoke(OPS.TEXT, entity.toString());
	}

	/*
	 * @testName: echoFullByteTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-3; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo byte
	 */
	@Test
	public void echoFullByteTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSFullByteClientEndpoint());
		Byte entity = Byte.MIN_VALUE;
		setEntity(entity);
		invoke(OPS.TEXT, entity.toString());
	}

	/*
	 * @testName: echoFullCharTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-3; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo char
	 */
	@Test
	public void echoFullCharTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSFullCharClientEndpoint());
		Character entity = 'E';
		setEntity(entity);
		invoke(OPS.TEXT, entity.toString());
	}

	/*
	 * @testName: echoFullBooleanTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-3; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo boolean
	 */
	@Test
	public void echoFullBooleanTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSFullBooleanClientEndpoint());
		Boolean entity = true;
		setEntity(entity);
		invoke(OPS.TEXT, entity.toString());
	}

	/*
	 * @testName: echoFullShortTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-3; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo short
	 */
	@Test
	public void echoFullShortTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSFullShortClientEndpoint());
		Short entity = Short.MAX_VALUE;
		setEntity(entity);
		invoke(OPS.TEXT, entity.toString());
	}

	/*
	 * @testName: echoFullDoubleTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-3; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo double
	 */
	@Test
	public void echoFullDoubleTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSFullDoubleClientEndpoint());
		Double entity = Double.MIN_VALUE;
		setEntity(entity);
		invoke(OPS.TEXT, entity.toString());
	}

	/*
	 * @testName: echoFullFloatTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-3; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo float
	 */
	@Test
	public void echoFullFloatTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSFullFloatClientEndpoint());
		Float entity = Float.MAX_VALUE;
		setEntity(entity);
		invoke(OPS.TEXT, entity.toString());
	}

	/*
	 * @testName: echoFullLongTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-3; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo long
	 */
	@Test
	public void echoFullLongTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSFullLongClientEndpoint());
		Long entity = Long.MAX_VALUE;
		setEntity(entity);
		invoke(OPS.TEXT, entity.toString());
	}

	/*
	 * @testName: echoReaderTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-3; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: OnMessage(Reader)
	 */
	@Test
	public void echoReaderTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSReaderClientEndpoint());
		setProperty(Property.CONTENT, ECHO);
		invoke(OPS.TEXT, ECHO);
	}

	/*
	 * @testName: echoTextDecoderTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-3; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: OnMessage using TextDecoder
	 */
	@Test
	public void echoTextDecoderTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSTextDecoderClientEndpoint());
		setProperty(Property.CONTENT, ECHO);
		invoke(OPS.TEXT, ECHO);
	}

	/*
	 * @testName: echoTextStreamDecoderTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-3; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: OnMessage using TextStreamDecoder
	 */
	@Test
	public void echoTextStreamDecoderTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSTextStreamDecoderClientEndpoint());
		setProperty(Property.CONTENT, ECHO);
		invoke(OPS.TEXT, ECHO);
	}

	// -------------------------TEXT AND SESSION ------------------------------
	/*
	 * @testName: echoStringAndSessionTest
	 *
	 * @assertion_ids: WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: Echo String
	 */
	@Test
	public void echoStringAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSStringAndSessionClientEndpoint());
		setProperty(Property.CONTENT, ECHO);
		invoke(OPS.TEXT, ECHO);
	}

	/*
	 * @testName: echoIntAndSessionTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-2; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo int
	 */
	@Test
	public void echoIntAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSPrimitiveIntAndSessionClientEndpoint());
		int entity = Integer.MIN_VALUE;
		setEntity(entity);
		invoke(OPS.TEXT, String.valueOf(entity));
	}

	/*
	 * @testName: echoByteAndSessionTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-2; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo byte
	 */
	@Test
	public void echoByteAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSPrimitiveByteAndSessionClientEndpoint());
		byte entity = 123;
		setEntity(entity);
		invoke(OPS.TEXT, String.valueOf(entity));
	}

	/*
	 * @testName: echoCharAndSessionTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-2; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo char
	 */
	@Test
	public void echoCharAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSPrimitiveCharAndSessionClientEndpoint());
		char entity = 'E';
		setEntity(entity);
		invoke(OPS.TEXT, String.valueOf(entity) + "char");
	}

	/*
	 * @testName: echoBooleanAndSessionTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-2; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo boolean
	 */
	@Test
	public void echoBooleanAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSPrimitiveBooleanAndSessionClientEndpoint());
		boolean entity = true;
		setEntity(entity);
		invoke(OPS.TEXT, String.valueOf(entity));
	}

	/*
	 * @testName: echoShortAndSessionTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-2; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo short
	 */
	@Test
	public void echoShortAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSPrimitiveShortAndSessionClientEndpoint());
		short entity = -32100;
		setEntity(entity);
		invoke(OPS.TEXT, String.valueOf(entity));
	}

	/*
	 * @testName: echoDoubleAndSessionTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-2; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo double
	 */
	@Test
	public void echoDoubleAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSPrimitiveDoubleAndSessionClientEndpoint());
		double entity = -12345678.88;
		setEntity(entity);
		invoke(OPS.TEXT, String.valueOf(entity));
	}

	/*
	 * @testName: echoFloatAndSessionTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-2; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo float
	 */
	@Test
	public void echoFloatAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSPrimitiveFloatAndSessionClientEndpoint());
		float entity = -12345678.88f;
		setEntity(entity);
		invoke(OPS.TEXT, String.valueOf(entity));
	}

	/*
	 * @testName: echoLongAndSessionTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-2; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo long
	 */
	@Test
	public void echoLongAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSPrimitiveLongAndSessionClientEndpoint());
		long entity = Long.MIN_VALUE;
		setEntity(entity);
		invoke(OPS.TEXT, String.valueOf(entity));
	}

	/*
	 * @testName: echoFullIntAndSessionTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-3; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo int
	 */
	@Test
	public void echoFullIntAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSFullLongAndSessionClientEndpoint());
		Integer entity = Integer.MIN_VALUE;
		setEntity(entity);
		invoke(OPS.TEXT, entity.toString());
	}

	/*
	 * @testName: echoFullByteAndSessionTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-3; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo byte
	 */
	@Test
	public void echoFullByteAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSFullByteAndSessionClientEndpoint());
		Byte entity = Byte.MIN_VALUE;
		setEntity(entity);
		invoke(OPS.TEXT, entity.toString());
	}

	/*
	 * @testName: echoFullCharAndSessionTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-3; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo char
	 */
	@Test
	public void echoFullCharAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSFullCharAndSessionClientEndpoint());
		Character entity = 'E';
		setEntity(entity);
		invoke(OPS.TEXT, entity.toString());
	}

	/*
	 * @testName: echoFullBooleanAndSessionTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-3; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo boolean
	 */
	@Test
	public void echoFullBooleanAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSFullBooleanAndSessionClientEndpoint());
		Boolean entity = true;
		setEntity(entity);
		invoke(OPS.TEXT, entity.toString());
	}

	/*
	 * @testName: echoFullShortAndSessionTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-3; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo short
	 */
	@Test
	public void echoFullShortAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSFullShortAndSessionClientEndpoint());
		Short entity = Short.MAX_VALUE;
		setEntity(entity);
		invoke(OPS.TEXT, entity.toString());
	}

	/*
	 * @testName: echoFullDoubleAndSessionTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-3; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo double
	 */
	@Test
	public void echoFullDoubleAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSFullDoubleAndSessionClientEndpoint());
		Double entity = Double.MIN_VALUE;
		setEntity(entity);
		invoke(OPS.TEXT, entity.toString());
	}

	/*
	 * @testName: echoFullFloatAndSessionTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-3; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo float
	 */
	@Test
	public void echoFullFloatAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSFullFloatAndSessionClientEndpoint());
		Float entity = Float.MAX_VALUE;
		setEntity(entity);
		invoke(OPS.TEXT, entity.toString());
	}

	/*
	 * @testName: echoFullLongAndSessionTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-3; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: echo long
	 */
	@Test
	public void echoFullLongAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSFullLongAndSessionClientEndpoint());
		Long entity = Long.MAX_VALUE;
		setEntity(entity);
		invoke(OPS.TEXT, entity.toString());
	}

	/*
	 * @testName: echoReaderAndSessionTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-3; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: OnMessage(Reader)
	 */
	@Test
	public void echoReaderAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSReaderAndSessionClientEndpoint());
		setProperty(Property.CONTENT, ECHO);
		invoke(OPS.TEXT, ECHO);
	}

	/*
	 * @testName: echoTextDecoderAndSessionTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-3; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: OnMessage using TextDecoder
	 */
	@Test
	public void echoTextDecoderAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSTextDecoderAndSessionClientEndpoint());
		setProperty(Property.CONTENT, ECHO);
		invoke(OPS.TEXT, ECHO);
	}

	/*
	 * @testName: echoTextStreamDecoderAndSessionTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-3; WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: OnMessage using TextStreamDecoder
	 */
	@Test
	public void echoTextStreamDecoderAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSTextStreamDecoderAndSessionClientEndpoint());
		setProperty(Property.CONTENT, ECHO);
		invoke(OPS.TEXT, ECHO);
	}

	// -----------------------PARTIAL---------------------------------

	/*
	 * @testName: partialStringTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-2; WebSocket:SPEC:WSC-4.7-3;
	 * WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: send partial message
	 */
	@Test
	public void partialStringTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSStringPartialClientEndpoint());
		String partial2 = "partialStringTest";
		String response = ECHO + "_(false)" + partial2 + "(true)";
		setEntity(ECHO + "_" + partial2);
		invoke(OPS.TEXTPARTIAL, response);
	}

	/*
	 * @testName: partialStringAndSessionTest
	 *
	 * @assertion_ids: WebSocket:SPEC:WSC-4.7-2; WebSocket:SPEC:WSC-4.7-3;
	 * WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: send string and string receive the combined string
	 */
	@Test
	public void partialStringAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSStringPartialAndSessionClientEndpoint());
		String partial2 = "partialStringAndSessionTest";
		String response = ECHO + "_(false)" + partial2 + "(true)";
		setEntity(ECHO + "_" + partial2);
		invoke(OPS.TEXTPARTIAL, response);
	}

	// ----------------- BINARY -----------------------------------

	/*
	 * @testName: echoByteBufferTest
	 *
	 * @assertion_ids: WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: Echo ByteBuffer
	 */
	@Test
	public void echoByteBufferTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSByteBufferClientEndpoint());
		setEntity(ByteBuffer.wrap(ECHO.getBytes()));
		invoke(OPS.BINARY, ECHO);
	}

	/*
	 * @testName: byteBufferToBytesTest
	 *
	 * @assertion_ids: WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: Send ByteBuffer receive bytes
	 */
	@Test
	public void byteBufferToBytesTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSByteArrayClientEndpoint());
		setEntity(ByteBuffer.wrap(ECHO.getBytes()));
		invoke(OPS.BINARY, ECHO);
	}

	/*
	 * @testName: byteBufferToInputStreamTest
	 *
	 * @assertion_ids: WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: Send ByteBuffer receive InputStream
	 */
	@Test
	public void byteBufferToInputStreamTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSInputStreamClientEndpoint());
		setEntity(ByteBuffer.wrap(ECHO.getBytes()));
		invoke(OPS.BINARY, ECHO);
	}

	/*
	 * @testName: byteBufferToBinaryDecoderTest
	 *
	 * @assertion_ids: WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: Send ByteBuffer receive Object
	 */
	@Test
	public void byteBufferToBinaryDecoderTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSBinaryDecoderClientEndpoint());
		setEntity(ByteBuffer.wrap(ECHO.getBytes()));
		invoke(OPS.BINARY, ECHO);
	}

	/*
	 * @testName: byteBufferToBinaryStreamDecoderTest
	 *
	 * @assertion_ids: WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: Send ByteBuffer receive Object
	 */
	@Test
	public void byteBufferToBinaryStreamDecoderTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSBinaryStreamDecoderClientEndpoint());
		setEntity(ByteBuffer.wrap(ECHO.getBytes()));
		invoke(OPS.BINARY, ECHO);
	}

	// ----------------- CONTROL -----------------------------------
	/*
	 * @testName: pongToPongTest
	 *
	 * @assertion_ids: WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: pong message
	 */
	@Test
	public void pongToPongTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSPongMessageClientEndpoint());
		setEntity(ECHO);
		invoke(OPS.PONG, ECHO);
	}

	/*
	 * @testName: pongToPongAndSessionTest
	 *
	 * @assertion_ids: WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: pong message
	 */
	@Test
	public void pongToPongAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSPongMessageAndSessionClientEndpoint());
		setEntity(ECHO);
		invoke(OPS.PONG, ECHO);
	}

	// ----------------- BINARY AND SESSION -----------------------------------
	/*
	 * @testName: echoByteBufferAndSessionTest
	 *
	 * @assertion_ids: WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: Echo ByteBuffer
	 */
	@Test
	public void echoByteBufferAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSByteBufferAndSessionClientEndpoint());
		setEntity(ByteBuffer.wrap(ECHO.getBytes()));
		invoke(OPS.BINARY, ECHO);
	}

	/*
	 * @testName: byteBufferToBytesAndSessionTest
	 *
	 * @assertion_ids: WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: Send ByteBuffer receive bytes
	 */
	@Test
	public void byteBufferToBytesAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSByteArrayAndSessionClientEndpoint());
		setEntity(ByteBuffer.wrap(ECHO.getBytes()));
		invoke(OPS.BINARY, ECHO);
	}

	/*
	 * @testName: byteBufferToInputStreamAndSessionTest
	 *
	 * @assertion_ids: WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: Send ByteBuffer receive InputStream
	 */
	@Test
	public void byteBufferToInputStreamAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSInputStreamAndSessionClientEndpoint());
		setEntity(ByteBuffer.wrap(ECHO.getBytes()));
		invoke(OPS.BINARY, ECHO);
	}

	/*
	 * @testName: byteBufferToBinaryDecoderAndSessionTest
	 *
	 * @assertion_ids: WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: Send ByteBuffer receive Object
	 */
	@Test
	public void byteBufferToBinaryDecoderAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSBinaryDecoderAndSessionClientEndpoint());
		setEntity(ByteBuffer.wrap(ECHO.getBytes()));
		invoke(OPS.BINARY, ECHO);
	}

	/*
	 * @testName: byteBufferToBinaryStreamDecoderAndSessionTest
	 *
	 * @assertion_ids: WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: Send ByteBuffer receive Object
	 */
	@Test
	public void byteBufferToBinaryStreamDecoderAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSBinaryStreamDecoderAndSessionClientEndpoint());
		setEntity(ByteBuffer.wrap(ECHO.getBytes()));
		invoke(OPS.BINARY, ECHO);
	}

	// ----------------- PARTIAL BINARY -----------------------------------
	/*
	 * @testName: partialByteArrayTest
	 *
	 * @assertion_ids: WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: send bytearray and bytearray receive the combined message
	 */
	@Test
	public void partialByteArrayTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSByteArrayPartialClientEndpoint());
		String partial2 = "partialByteArrayTest";
		String response = ECHO + "_(false)" + partial2 + "(true)";
		setEntity(ByteBuffer.wrap((ECHO + "_" + partial2).getBytes()));
		invoke(OPS.BINARYPARTIAL, response);
	}

	/*
	 * @testName: partialByteArrayAndSessionTest
	 *
	 * @assertion_ids: WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: send bytearray and bytearray receive the combined message
	 */
	@Test
	public void partialByteArrayAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSByteArrayPartialAndSessionClientEndpoint());
		String partial2 = "partialByteArrayAndSessionTest";
		String response = ECHO + "_(false)" + partial2 + "(true)";
		setEntity(ByteBuffer.wrap((ECHO + "_" + partial2).getBytes()));
		invoke(OPS.BINARYPARTIAL, response);
	}

	/*
	 * @testName: partialByteBufferTest
	 *
	 * @assertion_ids: WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: send ByteBuffer and ByteBuffer receive the combined message
	 */
	@Test
	public void partialByteBufferTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSByteBufferPartialClientEndpoint());
		String partial2 = "partialByteBufferTest";
		String response = ECHO + "_(false)" + partial2 + "(true)";
		setEntity(ByteBuffer.wrap((ECHO + "_" + partial2).getBytes()));
		invoke(OPS.BINARYPARTIAL, response);
	}

	/*
	 * @testName: partialByteBufferAndSessionTest
	 *
	 * @assertion_ids: WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: send ByteBuffer and ByteBuffer receive the combined message
	 */
	@Test
	public void partialByteBufferAndSessionTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSByteBufferPartialAndSessionClientEndpoint());
		String partial2 = "partialByteBufferAndSessionTest";
		String response = ECHO + "_(false)" + partial2 + "(true)";
		setEntity(ByteBuffer.wrap((ECHO + "_" + partial2).getBytes()));
		invoke(OPS.BINARYPARTIAL, response);
	}

	// --------------------------------- MAX LEN
	/*
	 * @testName: maxLengthOKTest
	 *
	 * @assertion_ids: WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: the message is shorter than maxMessageLength
	 */
	@Test
	public void maxLengthOKTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSMaxLengthClientEndpoint());
		String entity = "12345";
		setEntity(entity);
		invoke(OPS.TEXT, entity);
	}

	/*
	 * @testName: maxLengthFailsTest
	 *
	 * @assertion_ids: WebSocket:JAVADOC:80; WebSocket:SPEC:WSC-4.7.1-1;
	 *
	 * @test_Strategy: the message is longer than maxMessageLength
	 */
	@Test
	public void maxLengthFailsTest() throws Exception {
		setCountDownLatchCount(1);
		final AtomicInteger ai = new AtomicInteger(0);
		setEntity("123456");
		EndpointCallback callback = new SendMessageCallback(entity) {
			@Override
			public void onClose(Session session, CloseReason closeReason) {
				ai.set(closeReason.getCloseCode().getCode());
				getCountDownLatch().countDown();
			}
		};
		setClientCallback(callback);
		setAnnotatedClientEndpointInstance(new WSMaxLengthClientEndpoint());
		setProperty(Property.REQUEST, buildRequest(OPS.TEXT));
		invoke(false);
		assertEqualsInt(1009, ai.get(), "Unexpected close reason found", ai.get());
		logMsg("Found expected close reason code", 1009);
	}

	/*
	 * @testName: defaultMaxLengthTest
	 *
	 * @assertion_ids: WebSocket:JAVADOC:80;
	 *
	 * @test_Strategy: Default -1;
	 */
	@Test
	public void defaultMaxLengthTest() throws Exception {
		setAnnotatedClientEndpointInstance(new WSDefaultMaxLengthClientEndpoint());
		setEntity("123456789");
		invoke(OPS.TEXT, "-1");
	}

	// ////////////////////////////////////////////////////////////////////
	private String buildRequest(OPS op) {
		return buildRequest("srv", "/", op.name());
	}

	private void invoke(OPS op, String search) throws Exception {
		setProperty(Property.REQUEST, buildRequest(op));
		setProperty(Property.SEARCH_STRING, search, search);
		invoke();
	}
}
