/*
 * RssTest.java 13.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino;

import java.io.File;
import java.io.IOException;

import de.mancino.rss.RssEntry;
import de.mancino.rss.RssFeed;

public class RssTest {
    public static void main(String[] args) throws IOException {
        RssFeed feed = new RssFeed(new File("/var/www/http/rss_text.xml"), "MyTest Spy", "http://www.mancino-net.de", "Spy Description");

        int id = 1;
        while(true) {
            try {
                feed.addEntry(new RssEntry("Title " + id++ , "My Message: " + System.currentTimeMillis(), "http://www.mancino-net.de/"));
                feed.write();
                Thread.sleep(60000L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
