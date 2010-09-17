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

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mancino.armory.Armory;
import de.mancino.armory.xml.armorysearch.ArmorySearch;
import de.mancino.armory.xml.armorysearch.searchresults.character.Character;
import de.mancino.armory.xml.characterInfo.CharacterInfo;
import de.mancino.armory.xml.characterInfo.charactertab.items.Item;
import de.mancino.armory.xml.enums.Slot;
import de.mancino.exceptions.ArmoryConnectionException;
import de.mancino.rss.RssEntry;
import de.mancino.rss.RssFeed;

public class CTest {
    /**
     * Logger instance of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CTest.class);

    private static final long POLLING_INTERVAL_IN_MS = 5L * 60L * 1000L;

    private int currentLevel;
    private static final String CHAR_NAME = "Hélios";
    private static final String REALM_NAME = "Forscherliga";
    private static final String CHAR_URL = "http://eu.wowarmory.com/character-sheet.xml?r=Forscherliga&cn=H%C3%A9lios";
    private static final String ITEM_URL = "http://eu.wowarmory.com/item-info.xml?i=";

    private final HashMap<Slot, Item> items;
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
        final CharacterInfo character = logIn().getCharacterSheet(CHAR_NAME, REALM_NAME);
        final String title = "Surveillance initialized for " + character;
        currentLevel = character.character.level;
        LOG.info(title);
        message.append("Current Level: ").append(currentLevel).append("\n");
        message.append("Current Items:\n");
        items = new HashMap<Slot, Item>();
        for(Slot slot : Slot.values()) {
            Item item = character.characterTab.getItemInSlot(slot);
            message.append(" ").append(slot).append(": ");
            if (item!=null) {
                message.append(item.name);
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
                final Armory armory = logIn();
                CharacterInfo characterInfo = armory.getCharacterSheet(CHAR_NAME, REALM_NAME);
                ArmorySearch charSearch = armory.searchArmory(CHAR_NAME);
                checkLevel(charSearch);
                checkItems(characterInfo);
                feed.write();
            } catch (ArmoryConnectionException e) {
                LOG.error(e.getLocalizedMessage());
            } catch (IOException e) {
                LOG.error(e.getLocalizedMessage());
            }
        }
    }

    private Armory logIn() throws ArmoryConnectionException {
        final String passwd = new String(Base64.decodeBase64("bmtnc2VxcGd5N2E="));
        final Armory armory = new Armory("mario@mancino-net.de", passwd);
        return armory;
    }

    private void checkLevel(ArmorySearch armorySearch) {
        for(Character character : armorySearch.searchResults.characters) {
            if((character.realm).equals(REALM_NAME)) {
                LOG.debug("Current Level: " + character.level);
                if(currentLevel < character.level) {
                    final String message = CHAR_NAME + " has reached Level " + character.level;
                    LOG.info(message);
                    feed.addEntry(new RssEntry("Level Up! (" + character.level + ")" , message, CHAR_URL));
                    currentLevel = character.level;
                }
            }
        }
    }

    private void checkItems(final CharacterInfo character) {
        for(Slot slot : Slot.values()) {
            Item currentItem = character.characterTab.getItemInSlot(slot);
            if(currentItem != null && !currentItem.equals(items.get(slot))) {
                final String message = character.character.name + " received new " + slot + "-Item: " + currentItem;
                LOG.info(message);
                feed.addEntry(new RssEntry("New Item: "+ currentItem.name, message, ITEM_URL + currentItem.id));
                items.put(slot, currentItem);
            } else if(currentItem == null && items.get(slot)!=null) {
                final String message = character.character.name + " lost " + slot + "-Item: " + items.get(slot);
                LOG.info(message);
                feed.addEntry(new RssEntry("Lost Item: "+ items.get(slot).name, message, ITEM_URL + items.get(slot).id));
                items.remove(slot);
            }
        }
    }
}
