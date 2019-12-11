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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This method level annotation can be used to decorate a Java method that wishes to be called in order to handle
 * errors. See {@link Endpoint#onError} for a description of the different categories of error.
 *
 * <p>
 * The method may only take the following parameters:-
 * <ul>
 * <li>optional {@link Session} parameter</li>
 * <li>a {@link java.lang.Throwable} parameter</li>
 * <li>Zero to n String parameters annotated with the {@code jakarta.websocket.server.PathParam} annotation</li>
 * </ul>
 *
 * <p>
 * The parameters may appear in any order.
 *
 * @author dannycoward
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnError {

}
