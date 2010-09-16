/*
 * RssEntry.java 13.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.rss;

import org.apache.commons.lang.StringEscapeUtils;

public class RssEntry {
    public final String title;
    public final String description;
    public final String link;
    public String guid;

    public RssEntry(final String title, final String description, final String link) {
        this.title = htmlLineBreaks(title);
        this.description = htmlLineBreaks(description);
        this.link = htmlLineBreaks(link);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(" <item>\n")
            .append("  <title>").append(title).append("</title>\n")
            .append("  <description>").append(description).append("</description>\n")
            .append("  <link>").append(link).append("</link>\n")
            .append("  <guid>").append(guid).append("</guid>\n")
            .append(" </item>\n");
        return sb.toString();
    }

    private String htmlLineBreaks(String source) {
        return StringEscapeUtils.escapeXml(source.replace("\n", "</br>"));
    }

    void updateGuid(final String guid) {
        this.guid = guid;
    }
}