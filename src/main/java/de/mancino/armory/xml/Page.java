package de.mancino.armory.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import de.mancino.armory.xml.armorysearch.ArmorySearch;
import de.mancino.armory.xml.auctionsearch.AuctionSearch;
import de.mancino.armory.xml.characterInfo.CharacterInfo;
import de.mancino.armory.xml.itemtooltips.ItemTooltip;
import de.mancino.armory.xml.tabinfo.TabInfo;

/**
 *
 * Example Snippet:<br/>
 *  <code>
 *  &lt;page globalSearch="1"<br/>
 *  &nbsp;lang="en_us"<br/>
 *  &nbsp;requestQuery="searchQuery=H%C3%A9lios&amp;searchType=all&amp;rhtml=n"<br/>
 *  &nbsp;requestUrl="/search.xml"&gt;<br/>
 *  &nbsp;&nbsp;&lt;!-- Armory Search --&gt;<br/>
 *  &nbsp;&nbsp;&lt;armorySearch /&gt;<br/>
 *
 *  &nbsp;&nbsp;&lt;!-- Character Info --&gt;<br/>
 *  &nbsp;&nbsp;&lt;tabInfo /&gt;<br/>
 *  &nbsp;&nbsp;&lt;characterInfo /&gt;<br/>
 *
 *  &nbsp;&nbsp;&lt;!-- Item Info --&gt;<br/>
 *  &nbsp;&nbsp;&lt;itemTooltips&gt;<br/>
 *  &nbsp;&nbsp;&nbsp;&lt;itemTooltip /&gt;<br/>
 *  &nbsp;&nbsp;&nbsp;...<br/>
 *  &nbsp;&nbsp;&nbsp;&lt;itemTooltip /&gt;<br/>
 *  &nbsp;&nbsp;&lt;/itemTooltips&gt;<br/>
 *  &lt;/page&gt;<br/>
 *  </code>
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
    @XmlElement(name = "auctionSearch")
    public AuctionSearch auctionSearch;
    public ArmoryError error;
    // TODO: Waht if no search?!?
    //TODO:
// <command classId="-1" cn="Chevron" end="40" f="0" filterId="-1" id="0" maxLvl="0" minLvl="0" n="King's Amber" pageSize="40" qual="4" r="Forscherliga" reverse="false" sort="RARITY" start="0"/>

}
