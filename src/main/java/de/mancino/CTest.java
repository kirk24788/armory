/*
 * CTest.java 13.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.mancino.armory.Armory;
import de.mancino.armory.character.CharacterSheet;
import de.mancino.armory.enums.Slot;
import de.mancino.armory.item.EquipedItem;
import de.mancino.exceptions.ArmoryConnectionException;
import de.mancino.rss.RssEntry;
import de.mancino.rss.RssFeed;

public class CTest {
    /**
     * Logger instance of this class.
     */
    private static final Log LOG = LogFactory.getLog(Test.class);

    private static final long POLLING_INTERVAL_IN_MS = 5L * 60L * 1000L;

    private int currentLevel;
    private static final String CHAR_NAME = "Hélios";
    private static final String CHAR_URL = "http://eu.wowarmory.com/character-sheet.xml?r=Forscherliga&cn=H%C3%A9lios";
    private static final String ITEM_URL = "http://eu.wowarmory.com/item-info.xml?i=";

    private final Map<Slot, EquipedItem> items;
    private final RssFeed feed;
    /**
     * @param args
     * @throws InterruptedException
     * @throws ArmoryConnectionException
     * @throws ArmoryConnectionException
     */
    public static void main(String[] args) throws InterruptedException, ArmoryConnectionException {
        // new CTest();
        new CTest().run();
    }
//
    // http://eu.wowarmory.com/item-info.xml?i=3047
    public CTest() throws ArmoryConnectionException {
        final StringBuffer message = new StringBuffer();
        final CharacterSheet character = fetchSheet();
        final String title = "Surveillance initialized for " + character;
        currentLevel = character.level;
        LOG.info(title);
        message.append("Current Level: ").append(currentLevel).append("\n");
        message.append("Current Items:\n");
        items = new HashMap<Slot, EquipedItem>();
        for(Slot slot : Slot.values()) {
            EquipedItem item = character.itemInSlot(slot);
            message.append(" ").append(slot).append(": ");
            if (item!=null) {
                message.append(item);
            } else {
                message.append("None");
            }
            message.append("\n");
            items.put(slot, item);
        }
        LOG.info(message.toString());
        feed = new RssFeed(new File("/var/www/http/spy.xml"), CHAR_NAME + " Spy", CHAR_URL, CHAR_NAME + " Spy");
        feed.addEntry(new RssEntry(title, message.toString(), CHAR_URL));
        updateFeed();
    }
    /**
     * @param feed
     */
    private void updateFeed() {
        try {
            feed.write();
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage());
        }
    }

    public void run() throws InterruptedException {
        while(true) {
            try {
                Thread.sleep(POLLING_INTERVAL_IN_MS);
                CharacterSheet character = fetchSheet();
                checkLevel(character);
                checkItems(character);
                feed.write();
            } catch (ArmoryConnectionException e) {
                LOG.error(e.getLocalizedMessage());
            } catch (IOException e) {
                LOG.error(e.getLocalizedMessage());
            }
        }
    }

    private CharacterSheet fetchSheet() throws ArmoryConnectionException {
        final String passwd = new String(Base64.decodeBase64("bmtnc2VxcGd5N2E="));
        final Armory armory = new Armory("mario@mancino-net.de", passwd);
        final String realmName = "Forscherliga";
        return armory.getCharacterSheet(CHAR_NAME, realmName);
    }

    private void checkLevel(final CharacterSheet character) {
        if(currentLevel != character.level) {
            final String message = character.fullCharName + " has reached Level " + character.level;
            LOG.info(message);
            feed.addEntry(new RssEntry("Level Up! (" + character.level + ")" , message, CHAR_URL));
            currentLevel = character.level;
        }
    }

    private void checkItems(final CharacterSheet character) {
        for(Slot slot : Slot.values()) {
            EquipedItem currentItem = character.itemInSlot(slot);
            if(currentItem != null && !currentItem.equals(items.get(slot))) {
                final String message = character.fullCharName + " received new " + slot + "-Item: " + currentItem;
                LOG.info(message);
                feed.addEntry(new RssEntry("New Item: "+ currentItem.name, message, ITEM_URL + currentItem.id));
                items.put(slot, currentItem);
            } else if(currentItem == null && items.get(slot)!=null) {
                final String message = character.fullCharName + " lost " + slot + "-Item: " + items.get(slot);
                LOG.info(message);
                feed.addEntry(new RssEntry("Lost Item: "+ items.get(slot).name, message, ITEM_URL + items.get(slot).id));
                items.remove(slot);
            }
        }
    }
}
