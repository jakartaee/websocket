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

package javax.websocket.server;

import java.util.Set;
import javax.websocket.Endpoint;

/**
 * Developers include implementations of ServerApplicationConfig in an archive containing
 * websocket endpoints (WAR file, or JAR file within the WAR file) in order to specify the websocket 
 * endpoints within the archive the implementation must deploy. There is a separate
 * method for programmatic endpoints and for annotated endpoints.
 *
 * @author dannycoward
 */
public interface ServerApplicationConfig {

    /**
     * Return a set of ServerEndpointConfig instances that the server container
     * will use to deploy the programmatic endpoints. The set of Endpoint classes passed in to this method is
     * the set obtained by scanning the archive containing the implementation
     * of this ServerApplicationConfig. This set passed in
     * may be used the build the set of ServerEndpointConfig instances
     * to return to the container for deployment.
     *
     * @param endpointClasses the set of all the Endpoint classes in the archive containing
     *                the implementation of this interface.
     * @return the non-null set of ServerEndpointConfig s to deploy on the server, using the empty set to
     * indicate none.
     */
    public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> endpointClasses);

    /**
     * Return a set of annotated endpoint classes that the server container
     * must deploy. The set of classes passed in to this method is
     * the set obtained by scanning the archive containing the implementation
     * of this interface. Therefore, this set passed in contains all the annotated endpoint classes
     * in the JAR or WAR file containing the implementation of this interface. This set passed in
     * may be used the build the set to return to the container for deployment.
     *
     * @param scanned the set of all the annotated endpoint classes in the archive containing
     *                the implementation of this interface.
     * @return the non-null set of annotated endpoint classes to deploy on the server, using the empty
     * set to indicate none.
     */
    Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned);
}
