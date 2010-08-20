/*
 * Price.java 20.08.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.enums;

public class Price {
    public final long price;
    public final long gold;
    public final long silver;
    public final long copper;

    public Price(String price) {
        this(Long.parseLong(price));
    }

    public Price(long price) {
        this.price = price;
        this.copper = price % 100;
        this.silver = (price/100) % 100;
        this.gold = price/10000;
    }

    @Override
    public String toString() {
        return gold + "g" + silver + "s" + copper + "c";
    }
}
