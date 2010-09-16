/*
 * Test.java 15.03.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.mancino.armory.Armory;
import de.mancino.armory.auction.AuctionItem;
import de.mancino.armory.auction.AuctionSearch;
import de.mancino.armory.enums.Rarity;
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
        // Thu, 27 Apr 2006
        SimpleDateFormat df = new SimpleDateFormat( "EEE, dd MMM yyyy" );
        System.err.println(df.format(new Date()));
        if(true)return;
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
            /*
            ItemSearch is = armory.searchItem("frost lotus");
            for(Item i : is.item) {
                System.err.println(i);
            }
            ItemInfo ii = armory.getItemInfo(49623);
            System.err.println(ii.name + " QUALITY: " + ii.quality);
            */

            final String searchTerm = "Frost Lotus";
            AuctionSearch as = armory.searchAuction(searchTerm, Rarity.POOR);
            LOG.info(as.auctionItems.size() + " items found!");
            List<Long> prices = new ArrayList<Long>(as.auctionItems.size());
            for(AuctionItem ai : as.auctionItems) {
                if(ai.hasBuyoutOption()) {
                    prices.add(ai.buyoutPrice.price);
                }
            }
            LOG.info(as.auctionItems.size() + " items with buyout option!");
            writePlot("FrostLotus", prices);
        } catch (ArmoryConnectionException e) {
            e.printStackTrace();
        }
    }

    public static long median(List<Long> numbers) {
        if(numbers.size()==0) {
            throw new IllegalArgumentException("List must contain at least 1 number!");
        }
        final int middle = numbers.size() / 2;
        if(numbers.size()%2 == 1) {
            return numbers.get(middle);
        } else {
            return (numbers.get(middle-1) + numbers.get(middle)) / 2;
        }
    }

    public static long average(List<Long> numbers) {
        long total = 0;
        for(Long l : numbers) {
            total += l;
        }
        return total / numbers.size();
    }

    public static void writePlot(String id, List<Long> prices) {
        Collections.sort(prices);
        final double factor = 10000.0;
        final Calendar cal = Calendar.getInstance();
        final long currentTime = cal.get(Calendar.HOUR_OF_DAY) * 100 + cal.get(Calendar.MINUTE);
        final double minimum = prices.get(0);
        final double median = median(prices);
        final double maximum = prices.get(prices.size()-1);

        final File simpleLogFile = new File("auction_" + id + ".simple.plot");
        final File intensityLogFile = new File("auction_" + id + ".intensity.plot");
        FileWriter writer = null;
        try {
            writer = new FileWriter(simpleLogFile, true);
            IOUtils.write(currentTime + " " + minimum/factor + " " + median/factor + " " + maximum/factor + "\n",
                    writer);
        } catch (IOException e) {
            LOG.error("Couldn't write to File " + simpleLogFile);
            LOG.debug("Stacktrace:", e);
        } finally {
            IOUtils.closeQuietly(writer);
        }
        try {
            writer = new FileWriter(intensityLogFile, true);
            long lastPrice = 0;
            long lastCount = 0;
            for(Long price : prices) {
                if(price != lastPrice) {
                    IOUtils.write(currentTime + " " + lastPrice/factor + " " + lastCount + "\n",
                        writer);
                    lastPrice = price;
                    lastCount=1;
                } else {
                    lastCount++;
                }
            }
        } catch (IOException e) {
            LOG.error("Couldn't write to File " + intensityLogFile);
            LOG.debug("Stacktrace:", e);
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }
}
