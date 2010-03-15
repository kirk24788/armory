/*
 * ArmoryCacheServlet.java 15.03.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.servlets;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.mancino.cache.CachedObject;
import de.mancino.data.ArmoryData;
import de.mancino.exceptions.ArmoryConnectionException;
import de.mancino.exceptions.StoreUnavailableException;

public class ArmoryCacheServlet extends HttpServlet implements Runnable  {
    /**
     * Logger.
     */
    private static final Log LOG = LogFactory.getLog(ArmoryCacheServlet.class);

    /**
     * Map von ArmoryData-Instanzen. Jede Char/Realm Kombination hat eine eindeutige
     * ArmoryData Instanz.
     */
    private final static Map<String,CachedObject<ArmoryData>> INSTANCES =
        new HashMap<String,CachedObject<ArmoryData>>();

    /**
     * Maximales Alter der Armory Daten in Millisekunden
     */
    private final static long MAX_DATA_AGE_MS = 3600000L;

    /**
     * Polling-Intervall in Millisekunden
     */
    private final static long ARMORY_POLLING_MS = 3600000L;

    /**
     * Polling-Intervall Grace Period zwischen zwei Char-Abfrage in Millisekunden
     */
    private final static long ARMORY_GRACE_PERIOD_MS = 1000L;


    /**
     * {@inheritDoc}
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        final Thread lookupThread = new Thread(this);
        getServletContext().setAttribute("lookupThread", lookupThread);
        lookupThread.setPriority(Thread.MIN_PRIORITY);
        lookupThread.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        final Thread lookupThread = (Thread) getServletContext().getAttribute("lookupThread");
        lookupThread.interrupt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        try {
            while(true) {
                // Minütlich pollen...
                try {
                    for(String key : INSTANCES.keySet()) {
                        CachedObject<ArmoryData> cachedArmoryData = INSTANCES.get(key);
                        if(cachedArmoryData != null) {
                            cachedArmoryData.getInstance();
                        }
                        Thread.sleep(ARMORY_GRACE_PERIOD_MS);
                    }
                } catch (StoreUnavailableException e) {
                    // Ignorieren! Dies kann theoretisch vorkommen wenn
                    // Der Hudson kurzzeitig beim Hochfahren offline...in dem Fall
                    // lieber loggen und hoffen dass er bis zum ersten Artefakt
                    // wieder da ist
                    LOG.debug("Fehler beim Abgleich mit der Armory", e);
                }
                Thread.sleep(ARMORY_POLLING_MS);
            }
        } catch (InterruptedException e) {
        }

    }


    /**
     * Hudson Instanz erstellen/laden. Falls die Instanz älter ist als
     * maxAgeInMinutes Minuten wird die Instanz neu erstellt.
     *
     * @param username Benutzername
     * @param password Password
     * @param maxAgeInMillis Maximales Alter der Isntanz in Minuten
     * @return Hudson Objekt
     *
     * @throws StoreUnavailableException Fehler beim Verbindungsaufbau zum Hudson
     */
    public synchronized static ArmoryData getInstance(final String charName, final String realmName) throws StoreUnavailableException  {
        final String instanceIdent = charName + "@" + realmName;
        CachedObject<ArmoryData> cachedArmoryData = INSTANCES.get(instanceIdent);
        if(cachedArmoryData==null) {
            cachedArmoryData = new CachedObject<ArmoryData>(MAX_DATA_AGE_MS) {
                @Override
                protected ArmoryData createObject()
                        throws StoreUnavailableException {
                    try {
                        return new ArmoryData(charName, realmName);
                    } catch (ArmoryConnectionException e) {
                        throw new StoreUnavailableException("Armory nicht verfügbar!", e);
                    }
                }
            };
            INSTANCES.put(instanceIdent, cachedArmoryData);
        }

        return cachedArmoryData.getInstance();
    }

}
