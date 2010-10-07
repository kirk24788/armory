/*
 * AuctionItem.java 07.10.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.xml.auctionsearch;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import de.mancino.armory.xml.enums.Quality;

/**
*
* Example Snippet:
*   <aucItem auc="712761707"
*    bid="972550"
*    buy="989500"
*    charges="0"
*    icon="inv_jewelcrafting_gem_38"
*    id="40123"
*     ilvl="80"
*     n="Brilliant King's Amber"
*     nbid="972550"
*     qual="4"
*     quan="1"
*     req="1"
*     seed="196434048"
*     seller="Gnaxx"
*     time="3"/>
* @author mmancino
*/
@XmlRootElement(name = "aucItem")
public class AuctionItem {
    @XmlAttribute(name="auc")
    public long auctionId;
    @XmlAttribute(name="bid")
    public long bid;
    @XmlAttribute(name="buy")
    public long buy;
    @XmlAttribute(name="charges")
    public long charges;
    @XmlAttribute(name="icon")
    public String icon;
    @XmlAttribute(name="id")
    public long itemId;
    @XmlAttribute(name="ilvl")
    public int ilvl;
    @XmlAttribute(name="n")
    public String name;
    @XmlAttribute(name="nbid")
    public long nextBid;
    @XmlAttribute(name="qual")
    public Quality quality;
    @XmlAttribute(name="quan")
    public int quantity;
    @XmlAttribute(name="req")
    public int req;
    @XmlAttribute(name="seed")
    public long seed;
    @XmlAttribute(name="seller")
    public String seller;
    @XmlAttribute(name="time")
    public int time;
}
