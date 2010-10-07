/*
 * AuctionSearch.java 07.10.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.xml.auctionsearch;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 *
 * Example Snippet:
 *  <auctionSearch end="40" start="0" total="113">
 *    <auctions>...</auctions>
 *  </auctionSearch>
 * @author mmancino
 */
public class AuctionSearch {
    @XmlAttribute(name="start")
    public int start;
    @XmlAttribute(name="end")
    public int end;
    @XmlAttribute(name="total")
    public int total;
    @XmlElementWrapper(name="auctions")
    @XmlElement(name ="aucItem")
    public List<AuctionItem> auctionItems;
}
