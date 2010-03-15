/*
 * CachedObject.java 15.03.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.cache;

import de.mancino.exceptions.StoreUnavailableException;


/**
 * Klasse zum Cachen von Objekten.
 *
 * @author mmancino
 */
public abstract class CachedObject<T> {

    /**
     * Maximales Alter in Millisekunden
     */
    private long maxAgeInMillis = 60000L;

    /**
     * Timestamp der letzten Aktualisierung
     */
    private long lastUpdate = 0;

    private T cachedObjectInstance = null;

    /**
     * Erstellt einen neuen Cache
     */
    public CachedObject() {
    }

    /**
     * Erstellt einen neuen Cache mit dem gegebenen maximalen Alter
     *
     * @param maxAgeInMillis Maximales Alter in Millisekunden
     */
    public CachedObject(long maxAgeInMillis) {
        this.maxAgeInMillis = maxAgeInMillis;
    }

    /**
     * Liefert das gecachte Objekt
     *
     * @return gecachted Objekt
     *
     * @throws StoreUnavailableException Store ist zur Zeit nicht verfügbar
     */
    public synchronized T getInstance() throws StoreUnavailableException {
        if (cachedObjectInstance == null || getCurrentAgeInMillis() > maxAgeInMillis ) {
            try {
                cachedObjectInstance = createObject();
                lastUpdate = System.currentTimeMillis();
            } catch (StoreUnavailableException e) {
                // Wenn der Store beim ersten Erstellen nicht verfügbar ist, exception weiterwerfen
                if(cachedObjectInstance==null) {
                    throw e;
                }
                // TODO: Loggen?
            }
        }
        return cachedObjectInstance;
    }

    /**
     * Liefert das aktuelle Alter der Instanz in Millisekunden
     *
     * @return Alter in Millisekunden
     */
    private long getCurrentAgeInMillis() {
        return System.currentTimeMillis()-lastUpdate;
    }

    /**
     * Setzt das maximale Alter neu
     *
     * @param maxAgeInMillis Maximales Alter in Millisekunden
     */
    public void setMaxAgeInMillis(long maxAgeInMillis) {
        this.maxAgeInMillis = maxAgeInMillis;
    }

    /**
     * Methode zum Erstellen des eigentlichen Objektes
     *
     * @return gecachted Objekt
     * @throws StoreUnavailableException Fehler beim Erstellen des Objekts
     */
    protected abstract T createObject() throws StoreUnavailableException;
}
