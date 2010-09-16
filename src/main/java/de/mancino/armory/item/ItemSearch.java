/*
 * ItemSearch.java 24.08.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;

import de.mancino.armory.auction.AuctionSearch;
import de.mancino.armory.enums.Rarity;
import de.mancino.utils.XmlDataWrapper;

public class ItemSearch extends XmlDataWrapper {
    /**
     * Logger instance of this class.
     */
    private static final Log LOG = LogFactory.getLog(AuctionSearch.class);

    public final List<Item> item;

    public ItemSearch(final Document xmlData) {
        super(xmlData);
        final List<Item> tmpItemList = new ArrayList<Item>();
        for(Element auctionItemElement : searchAll("//item")) {
            final long id = Long.parseLong(getTextContent("@id", auctionItemElement));
            final String name = getTextContent("@name", auctionItemElement);
            final Rarity quality = Rarity.parse(Integer.parseInt(getTextContent("@rarity", auctionItemElement)));
            tmpItemList.add(new Item(id, name, quality));
        }
        item = Collections.unmodifiableList(tmpItemList);
       // Collections.unmodifiableCollection(auctionItems);
    }
}