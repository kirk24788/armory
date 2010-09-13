/*
 * Slot.java 13.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.enums;

public enum Slot {
    // AMMO(0, "Ammo"),
    HEAD(0, "Head"),
    NECK(1, "Neck"),
    SHOULDER(2, "Shoulder"),
    SHIRT(3, "Shirt"),
    CHEST(4, "Chest"),
    BELT(5, "Belt"),
    LEGS(6, "Legs"),
    FEET(7, "Feet"),
    WRIST(8, "Wrist"),
    GLOVES(9, "Gloves"),
    FINGER_1(10, "Finger 1"),
    FINGER_2(11, "Finger 2"),
    TRINKET_1(12, "Trinket 1"),
    TRINKET_2(13, "Trinket 2"),
    BACK(14, "Back"),
    MAIN_HAND(15, "Main Hand"),
    OFF_HAND(16, "Off Hand"),
    RANGED(17, "Ranged"),
    TABARD(18, "Tabard");

    public final int numericValue;
    public final String description;

    Slot(final int numericValue, final String description) {
        this.numericValue = numericValue;
        this.description = description;
    }

    public static Slot parse(int slot) {
        for(Slot possibleQuality : Slot.values()) {
            if(possibleQuality.numericValue == slot) {
                return possibleQuality;
            }
        }
        throw new RuntimeException("Error parsing slot: " + slot);
    }

    @Override
    public String toString() {
        return description;
    }
}

/*
http://www.wowwiki.com/InventorySlotId
Seems to be wrong...
0 = ammo
1 = head
2 = neck
3 = shoulder
4 = shirt
5 = chest
6 = belt
7 = legs
8 = feet
9 = wrist
10 = gloves
11 = finger 1
12 = finger 2
13 = trinket 1
14 = trinket 2
15 = back
16 = main hand
17 = off hand
18 = ranged
19 = tabard
20 = first bag (the rightmost one)
21 = second bag
22 = third bag
23 = fourth bag (the leftmost one)

40 to 67 = the 28 bank slots
68 = first bank bag slot
69 = second bank bag slot
70 = third bank bag slot
71 = fourth bank bag slot
72 = fifth bank bag slot
73 = sixth bank bag slot
74 = seventh bank bag slot
*/