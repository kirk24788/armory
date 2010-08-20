/*
 * Test.java 15.03.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.mancino.armory.Armory;
import de.mancino.armory.item.ItemInfo;
import de.mancino.exceptions.ArmoryConnectionException;

public class Test {
    /**
     * Logger instance of this class.
     */
    private static final Log LOG = LogFactory.getLog(Test.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            final String passwd = new String(Base64.decodeBase64("bmtnc2VxcGd5N2E="));
            Armory armory = new Armory("mario@mancino-net.de", passwd);
            //CharacterSheet chev = new CharacterSheet("Chevron", "Forscherliga");
            //LOG.debug(chev.fullCharName);
            //ItemInfo itm = new ItemInfo(50983);
            //System.err.println(itm.armor);
            //new AuctionSearch("");
            /*
            AuctionSearch as = armory.searchAuction("Adder's Tongue", Quality.POOR);
            for(AuctionItem ai : as.auctionItems) {
                LOG.info(ai.name + ": " + ai.currentBid + "/" + ai.buyoutPrice + "/" + ai.minimumNextBid);
            }
            */
            // schattengram 49623
            // epische schultern 51205
            // blaue dingsda 44735
            // weiß 35806
            // heirloom 42992
            ItemInfo ii = armory.searchItem(35806);
            System.err.println(ii.name + " QUALITY: " + ii.overallQualityId);
        } catch (ArmoryConnectionException e) {
            e.printStackTrace();
        }
    }

}
