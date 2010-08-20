/*
 * Quality.java 20.08.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.enums;

public enum Quality {
    ALL(0),
    POOR(1), // Grey
    COMMON(2), // White
    UNCOMMON(3), // Green
    RARE(4), // Blue
    EPIC(5), // Purple
    LEGENDARY(6), // Orange
    ARTIFACT(7), // Light gold
    HEIRLOOM(8); // Light gold

    public final int numericValue;
    Quality(final int numericValue) {
        this.numericValue = numericValue;
    }

    public static Quality parse(int quality) {
        for(Quality possibleQuality : Quality.values()) {
            if(possibleQuality.numericValue == quality) {
                return possibleQuality;
            }
        }
        // TODO: Exception?
        return ALL;
    }
}
