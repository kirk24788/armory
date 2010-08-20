/*
 * StoreUnavailableException.java 15.03.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.exceptions;

/**
 *  Zu Cachender Store ist beim ersten Erstellen nicht verfügbar
 *
 * @author mmancino
 */
@SuppressWarnings("serial")
public class StoreUnavailableException extends Exception {
    /**
     * Zu Cachender Store ist beim ersten Erstellen nicht verfügbar
     *
     * @param msg Nachricht
     * @param e Ursprüngliche Exception
     */
    public StoreUnavailableException(String msg, Exception e) {
        super(msg, e);
    }
}