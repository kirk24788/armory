/*
 * Item.java 24.08.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.item;

import de.mancino.armory.enums.Quality;

public class Item {
    public final long id;
    public final String name;
    public final Quality quality;

    public Item(final long id, final String name, final Quality quality) {
        this.id = id;
        this.name = name;
        this.quality = quality;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + name;
    }
}
