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

import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * Provider class that allows the developer to get a reference to
 * the implementation of the WebSocketContainer.
 * The provider class uses the 
 * <a href="http://docs.oracle.com/javase/7/docs/api/java/util/ServiceLoader.html">ServiceLoader</a> 
 * to load an implementation of ContainerProvider. Specifically, the fully qualified classname
 * of the container implementation of ContainerProvider must be listed in the 
 * META-INF/services/javax.websocket.ContainerProvider file in the implementation JAR file.
 *
 * @author dannycoward
 */
public abstract class ContainerProvider {
 
    /*
     * WeakHashMap so that the ClassLoader instances are not pinned in memory creating a memory leak.
     * SoftReference so cache entries can be dropped if memory is under pressure. 
     */
    private static final WeakHashMap<ClassLoader, SoftReference<Iterable<ContainerProvider>>> CACHE =
            new WeakHashMap<ClassLoader, SoftReference<Iterable<ContainerProvider>>>();

    /** 
     * Obtain a new instance of a WebSocketContainer. The method looks for the
     * ContainerProvider implementation class in the order listed in the META-INF/services/javax.websocket.ContainerProvider 
     * file, returning the WebSocketContainer implementation from the ContainerProvider implementation
     * that is not {@code null}.
     * @return an implementation provided instance of type WebSocketContainer
     */
    public static WebSocketContainer getWebSocketContainer() {
        Iterable<ContainerProvider> providers = getProviders();
        if (!providers.iterator().hasNext()) {
            throw new RuntimeException("Could not find an implementation class.");
        }
        WebSocketContainer wsc = null;
        for (ContainerProvider impl : providers) {
            wsc = impl.getContainer();
            if (wsc != null) {
                return wsc;
            } 
        }

        throw new RuntimeException("Could not find an implementation class with a non-null WebSocketContainer.");
    }
 
    private static Iterable<ContainerProvider> getProviders() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        SoftReference<Iterable<ContainerProvider>> providersRef;
        Iterable<ContainerProvider> providers;
        // WeakHashMap is not thread-safe. Sync here because a read concurrent with a write can throw an Exception. 
        synchronized (CACHE) {
            providersRef = CACHE.get(loader);
        }
        if (providersRef != null) {
            providers = providersRef.get();
            // Use providers from this point as the SoftReference checked above may be cleared at any point.
            if (providers != null) {
                return providers;
            }
        }
        synchronized (CACHE) {
            providersRef = CACHE.get(loader);
            if (providersRef == null || (providers = providersRef.get()) == null) {
                // Use providers from this point as the SoftReference checked above may be cleared at any point.
                Set<ContainerProvider> set = new HashSet<ContainerProvider>();
                for (ContainerProvider provider : ServiceLoader.load(ContainerProvider.class, loader)) {
                    set.add(provider);
                }
                providers = set;
                providersRef = new SoftReference<Iterable<ContainerProvider>>(providers);
                CACHE.put(loader, providersRef);
            }
            return providers;
        }
    }
    
    /**
     * Load the container implementation.
     * @return the implementation class
     */
    protected abstract WebSocketContainer getContainer();
}


