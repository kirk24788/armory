/*
 * ArmoryRequestError.java 11.10.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.exceptions;

import de.mancino.armory.xml.ArmoryError;

public class ArmoryRequestError extends ArmoryConnectionException {
    public ArmoryRequestError(ArmoryError error) {
        super("Error " + error.code + ": " + error.message);
    }
}
