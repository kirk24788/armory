/*
 * ArmoryConnectionException.java 15.03.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.exceptions;

public class ArmoryConnectionException extends Exception {

    public ArmoryConnectionException(Exception e) {
        super(e);
    }
    public ArmoryConnectionException(String msg, Exception e) {
        super(msg, e);
    }
    public ArmoryConnectionException(String msg) {
        super(msg);
    }

}
