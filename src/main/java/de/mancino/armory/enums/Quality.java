/*
 * Quality.java 20.08.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.enums;

public enum Quality {
    POOR(0), // Grey
    COMMON(1), // White
    UNCOMMON(2), // Green
    RARE(3), // Blue
    EPIC(4), // Purple
    LEGENDARY(5), // Orange
    ARTIFACT(6), // Light gold
    HEIRLOOM(7); // Light gold

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
        throw new RuntimeException("Error parsing quality: " + quality);
    }
}
