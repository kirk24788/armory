/*
 * AuctionSearch.java 20.08.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.auction;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;

import de.mancino.utils.XmlDataWrapper;

public class AuctionSearch extends XmlDataWrapper {
    /**
     * Logger instance of this class.
     */
    private static final Log LOG = LogFactory.getLog(AuctionSearch.class);

    public final List<AuctionItem> auctionItems;

    public AuctionSearch(final Document xmlData) {
        super(xmlData);
        final Map<Long, AuctionItem> auctionItemMap = new HashMap<Long, AuctionItem>();
        for(Element auctionItemElement : searchAll("//aucItem")) {
            AuctionItem auctionItem = new AuctionItem(auctionItemElement);
            if(!auctionItemMap.containsKey(Long.valueOf(auctionItem.auctionId))) {
                auctionItemMap.put(Long.valueOf(auctionItem.auctionId), auctionItem);
            }
        }
        final List<AuctionItem> auctionItemList = new ArrayList<AuctionItem>();
        for(Long key : auctionItemMap.keySet()) {
            auctionItemList.add(auctionItemMap.get(key));
        }
        auctionItems = Collections.unmodifiableList(auctionItemList);
       // Collections.unmodifiableCollection(auctionItems);
    }
}
