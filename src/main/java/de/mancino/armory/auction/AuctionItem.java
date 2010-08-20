/*
 * AuctionItem.java 20.08.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.auction;

import org.jdom.Element;

import de.mancino.armory.enums.Price;
import de.mancino.armory.enums.Quality;

public class AuctionItem {
    /*
    <aucItem
        auc='674519261'
        bid='29440000'
        buy='30999999'
        charges='0'
        icon='inv_inscription_tarotgreatness'
        id='44253'
        ilvl='200'
        n='Darkmoon Card: Greatness'
        nbid='29440000'
        qual='4'
        quan='1'
        req='80'
        seed='811848704'
        seller='Goldstück'
        time='0' />
    */

    public final long auctionId;
    public final Price currentBid;
    public final Price buyoutPrice;
    public final long itemId;
    public final long itemLevel;
    public final String name;
    public final Price minimumNextBid;
    public final Quality quality;
    public final long quantity;
    public final long req;
    public final long seed;
    public final String seller;
    public final long time;

    public AuctionItem(Element auctionData) {
        this.auctionId = Long.parseLong(auctionData.getAttribute("auc").getValue());
        this.currentBid = new Price(auctionData.getAttribute("bid").getValue());
        this.buyoutPrice = new Price(auctionData.getAttribute("buy").getValue());
        this.itemId = Long.parseLong(auctionData.getAttribute("id").getValue());
        this.itemLevel = Long.parseLong(auctionData.getAttribute("ilvl").getValue());
        this.name = auctionData.getAttribute("n").getValue();
        this.minimumNextBid = new Price(auctionData.getAttribute("nbid").getValue());
        this.quality = Quality.parse(Integer.parseInt(auctionData.getAttribute("qual").getValue()));
        this.quantity = Long.parseLong(auctionData.getAttribute("quan").getValue());
        this.req = Long.parseLong(auctionData.getAttribute("req").getValue());
        if(auctionData.getAttribute("seed") != null) {
            this.seed = Long.parseLong(auctionData.getAttribute("seed").getValue());
        } else {
            this.seed = 0;
        }
        this.seller = auctionData.getAttribute("seller").getValue();
        this.time = Long.parseLong(auctionData.getAttribute("time").getValue());
    }

}
