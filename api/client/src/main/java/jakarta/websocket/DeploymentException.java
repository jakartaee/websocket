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

/**
 * Checked exception indicating some kind of failure either to publish an endpoint on its server, or a failure to
 * connect a client to its server.
 *
 * @author dannycoward
 */
public class DeploymentException extends Exception {

    private static final long serialVersionUID = 7576860738144220015L;

    /**
     * Creates a deployment exception with the given reason for the deployment failure.
     *
     * @param message the reason for the failure.
     */
    public DeploymentException(String message) {
        super(message);
    }

    /**
     * Creates a deployment exception with the given reason for the deployment failure and wrapped cause of the failure.
     *
     * @param message the reason for the failure.
     * @param cause   the cause of the problem.
     */
    public DeploymentException(String message, Throwable cause) {
        super(message, cause);
    }
}
