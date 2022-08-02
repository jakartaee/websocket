/*
 * Copyright (c) 2018, 2020 Oracle and/or its affiliates and others.
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

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

/**
 * Provider class that allows the developer to get a reference to the implementation of the WebSocketContainer. The
 * provider class uses the
 * <a href="http://docs.oracle.com/javase/7/docs/api/java/util/ServiceLoader.html">ServiceLoader</a> to load an
 * implementation of ContainerProvider. Specifically, the fully qualified class name of the container implementation of
 * ContainerProvider must be listed in the "META-INF/services/jakarta.websocket.ContainerProvider" file in the
 * implementation JAR file.
 *
 * @author dannycoward
 */
public abstract class ContainerProvider {

    /**
     * Obtain a new instance of a WebSocketContainer. The method looks for the ContainerProvider implementation class in
     * the order listed in the "META-INF/services/jakarta.websocket.ContainerProvider" file, returning the first
     * WebSocketContainer implementation from the ContainerProvider implementation that is not {@code null}.
     *
     * @return an implementation provided instance of type WebSocketContainer
     *
     * @throws ServiceConfigurationError If there is a problem loading one of the discovered ContainerProvider
     *         implementations. A ServiceConfigurationError is viewed as a serious problem so the exception is allowed
     *         to propagate rather than swallowing the exception and attempting to load the next provider (if any).
     */
    public static WebSocketContainer getWebSocketContainer() {
        if(System.getSecurityManager() == null) {
            return getWebSocketContainerImpl();
        } else {
            return AccessController.doPrivileged(new PrivilegedAction<WebSocketContainer>() {
                @Override
                public WebSocketContainer run() {
                    return getWebSocketContainerImpl();
                }
            });
        }
    }

    private static WebSocketContainer getWebSocketContainerImpl() {
        Iterator<ContainerProvider> providers = ServiceLoader.load(ContainerProvider.class).iterator();
        if (providers.hasNext()) {
            do {
                ContainerProvider impl = providers.next();
                WebSocketContainer wsc = impl.getContainer();
                if (wsc != null) {
                    return wsc;
                }
            } while (providers.hasNext());
            throw new RuntimeException("Could not find an implementation class with a non-null WebSocketContainer.");
        } else {
            throw new RuntimeException("Could not find an implementation class.");
        }
    }

    /**
     * Create a new instance of the the WebSocket container implementation.
     *
     * @return the new instance
     */
    protected abstract WebSocketContainer getContainer();
}
