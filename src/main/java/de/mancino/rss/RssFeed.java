package de.mancino.rss;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;

public class RssFeed {
    private final File rssXmlFile;
    private final String title;
    private final String link;
    private final String description;
    private final String language = "de-DE";
    private final String copyright = "Mario Mancino";
    private String pubDate;
    private final List<RssEntry> entries = new ArrayList<RssEntry>();
    public boolean needsUpdate = true;

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat( "EEE, dd MMM yyyy" );

    public RssFeed(final File rssXmlFile, final String title, final String link, final String description) {
        this.rssXmlFile = rssXmlFile;
        this.title = htmlLineBreaks(title);
        this.link = htmlLineBreaks(link);
        this.description = htmlLineBreaks(description);
        this.pubDate = DATE_FORMAT.format(new Date());
    }

    public void addEntry(final RssEntry entry) {
        pubDate = DATE_FORMAT.format(new Date());
        entries.add(entry);
        needsUpdate = true;
    }

    public void forceWrite() throws IOException {
        FileUtils.writeStringToFile(this.rssXmlFile, this.toString());
        needsUpdate = false;
    }

    public void write() throws IOException {
        if(needsUpdate) {
            forceWrite();
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
        .append("<rss version=\"2.0\">\n")
        .append("<channel>\n")
        .append("<title>").append(title).append("</title>\n")
        .append("<link>").append(link).append("</link>\n")
        .append("<description>").append(description).append("</description>\n")
        .append("<language>").append(language).append("</language>\n")
        .append("<copyright>").append(copyright).append("</copyright>\n")
        .append("<pubDate>").append(pubDate).append("</pubDate>\n");
        for(RssEntry entry : entries) {
            sb.append(entry);
        }
        sb.append("</channel>\n")
        .append("</rss>\n");
        return sb.toString();
    }

    private String htmlLineBreaks(String source) {
        return StringEscapeUtils.escapeXml(source.replace("\n", "</br>"));
    }
}
/*
<?xml version="1.0" encoding="utf-8"?>

<rss version="2.0">

  <channel>
    <title>Titel des Feeds</title>
    <link>URL der Webpräsenz</link>
    <description>Kurze Beschreibung des Feeds</description>
    <language>Sprache des Feeds (z. B. "de-de")</language>
    <copyright>Autor des Feeds</copyright>
    <pubDate>Erstellungsdatum("Tue, 8 Jul 2008 2:43:19")</pubDate>
    <image>
      <url>URL einer einzubindenden Grafik</url>
      <title>Bildtitel</title>
      <link>URL, mit der das Bild verknüpft ist</link>
    </image>

    <item>
      <title>Titel des Eintrags</title>
      <description>Kurze Zusammenfassung des Eintrags</description>
      <link>Link zum vollständigen Eintrag</link>
      <author>Autor des Artikels, E-Mail-Adresse</author>
      <guid>Eindeutige Identifikation des Eintrages</guid>
      <pubDate>Datum des Items</pubDate>
    </item>

    <item>
      ...
    </item>

  </channel>

</rss>
 */