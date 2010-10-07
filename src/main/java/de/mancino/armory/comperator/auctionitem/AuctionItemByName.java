/*
 * AuctionSearchByName.java 07.10.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.comperator.auctionitem;

import java.util.Comparator;

import de.mancino.armory.xml.auctionsearch.AuctionItem;

public class AuctionItemByName implements Comparator<AuctionItem>{
    public int compare(AuctionItem o1, AuctionItem o2) {
        return o1.name.compareTo(o2.name);
    }
}