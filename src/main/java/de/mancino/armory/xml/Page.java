/*
 * Page.java 16.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import de.mancino.armory.xml.armorysearch.ArmorySearch;
import de.mancino.armory.xml.characterInfo.CharacterInfo;
import de.mancino.armory.xml.itemtooltips.ItemTooltip;
import de.mancino.armory.xml.tabinfo.TabInfo;

/**
 *
 * Example Snippet:
 *  <page globalSearch="1"
 *   lang="en_us"
 *   requestQuery="searchQuery=H%C3%A9lios&amp;searchType=all&amp;rhtml=n"
 *   requestUrl="/search.xml">
 *    <!-- Armory Search -->
 *    <armorySearch />
 *
 *    <!-- Character Info -->
 *    <tabInfo />
 *    <characterInfo />
 *
 *    <!-- Item Info -->
 *    <itemTooltips>
 *      <itemTooltip />
 *      ...
 *      <itemTooltip />
 *    </itemTooltips>
 *  </page>
 * @author mmancino
 */
@XmlRootElement(name = "page")
public class Page {
    @XmlAttribute(name = "globalSearch")
    public int globalSearch;
    @XmlAttribute(name = "lang")
    public String lang;
    @XmlAttribute(name = "requestQuery")
    public String requestQuery;
    @XmlAttribute(name = "requestUrl")
    public String requestUrl;
    public ArmorySearch armorySearch;
    public TabInfo tabInfo;
    public CharacterInfo characterInfo;

    @XmlElement(name = "itemTooltip")
    @XmlElementWrapper(name="itemTooltips")
    public List<ItemTooltip> itemTooltips;
    // TODO: Waht if no search?!?
}
