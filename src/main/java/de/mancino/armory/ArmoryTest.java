/*
 * ArmoryTest.java 16.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory;

import java.util.Collections;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mancino.armory.comperator.auctionitem.AuctionItemByUnitBuyoutPrice;
import de.mancino.armory.xml.auctionsearch.AuctionItem;
import de.mancino.armory.xml.auctionsearch.AuctionSearch;
import de.mancino.armory.xml.enums.Quality;
import de.mancino.exceptions.ArmoryConnectionException;

public class ArmoryTest  extends Armory {

    /**
     * Logger instance of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ArmoryTest.class);

    public ArmoryTest(String accountName, String password)
    throws ArmoryConnectionException {
        super(accountName, password, "Serpentæ", "Forscherliga");
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        try {
            final ArmoryTest aTest = new ArmoryTest(login, passwd);
            /*
            aTest.listPrices("King's Amber");
            aTest.listPrices("Cardinal Ruby");
            aTest.listPrices("Ametrine");
            aTest.listPrices("Eye of Zul");
            aTest.listPrices("Dreadstone");
            */
            boolean reallyBuy = false;
            aTest.buyAuctions("King's Amber", 870000, reallyBuy);
            aTest.buyAuctions("Cardinal Ruby", 1030000, reallyBuy);
            aTest.buyAuctions("Ametrine", 870000, reallyBuy);
            aTest.buyAuctions("Dreadstone", 830000, reallyBuy);
            aTest.buyAuctions("Eye of Zul", 650000, reallyBuy);
        } catch (ArmoryConnectionException e) {
            e.printStackTrace();
        }
    }
    public void buyAuctions(final String name, final long maxPrice) throws ArmoryConnectionException {
        buyAuctions(name, maxPrice, false);
    }

    public void buyAuctions(final String name, final long maxPrice, final boolean reallyBuy) throws ArmoryConnectionException {
        AuctionSearch search = searchAuction(name, Quality.RARE, true);
        Collections.sort(search.auctionItems, new AuctionItemByUnitBuyoutPrice());
        final long cheapest  =  search.auctionItems.get(0).buy / search.auctionItems.get(0).quantity;
        LOG.info("Buying '{}' which is cheaper than {} - Found {} Items, cheapest {}",
                new Object[]{name, maxPrice, search.auctionItems.size(), cheapest});
        for(AuctionItem item : search.auctionItems) {
            if((item.buy/item.quantity) <= maxPrice) {
                final String msg = item.name + "(" + item.quantity + "): " + item.buy + " - " + item.auctionId;
                if(reallyBuy) {
                    LOG.warn("Buying: " + msg);
                    buyAuction(item);
                } else {
                    LOG.info("Would have bought: " + msg);
                }
            }
        }
    }

    public void listPrices(final String name) throws ArmoryConnectionException {
        final StringBuffer sb = new StringBuffer();
        AuctionSearch search = searchAuction(name, Quality.RARE, true);
        Collections.sort(search.auctionItems, new AuctionItemByUnitBuyoutPrice());
        sb.append(search.auctionItems.size()).append(" Auctions for '").append(name)
            .append("', sorted by price/unit:\n");
        for(AuctionItem item : search.auctionItems) {
            sb.append(" * ").append(item.quantity).append(" ").append(name).append(" ")
                .append(item.buy/item.quantity).append(" each\n");
        }

        LOG.info(sb.toString());
    }










    final static String login = "mario@mancino-net.de";
    final static String passwd = new String(Base64.decodeBase64("bmtnc2VxcGd5N2E="));
    final static String charName = "Asira";//"Chevron";//"Hélios";
    final static String realmName = "Forscherliga";
}
