/*
 * RssTest.java 13.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino;

import java.io.IOException;

import de.mancino.rss.RssClient;

public class RssTest {
    final static String data ="Current Level: 46" +
"Current Items:" +
" Head: Phalanx Headguard" +
" Neck: Pendant of the Agate Shield" +
" Shoulder: Glimmering Mail Pauldrons" +
" Shirt: Squire's Shirt" +
" Chest: Banded Armor" +
" Belt: Mail Combat Belt" +
" Legs: Green Iron Leggings" +
" Feet: Ravasaur Scale Boots" +
" Wrist: Green Iron Bracers" +
 "Gloves: Lambent Scale Gloves" +
" Finger 1: Blush Ember Ring" +
" Finger 2: Tundra Ring" +
" Trinket 1: None" +
" Trinket 2: None" +
" Back: Banded Cloak" +
" Main Hand: Thornstone Sledgehammer" +
" Off Hand: None" +
" Ranged: None" +
" Tabard: None";
    public static void main(String[] args) throws IOException, InterruptedException {
        RssClient c = new RssClient("http://www.mancino-net.de:8080", "mario", "Laufae8s");
        c.createFeed("Spy", "Spy on me", "Test");
        c.addEntry("Spy", "Surveillance initialized for Hélios",data);
        c.addEntry("Spy", "E 2", "dies ist der zweite");
        //c.deleteFeed("Spy");
    }
}
