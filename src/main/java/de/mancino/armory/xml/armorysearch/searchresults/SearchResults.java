package de.mancino.armory.xml.armorysearch.searchresults;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import de.mancino.armory.xml.armorysearch.searchresults.character.Character;

/**
 *
 * Example Snippet:
 *  <searchResults pageCount="1"
 *   pageCurrent="1"
 *   searchError=""
 *   searchFilter=""
 *   searchMsg=""
 *   searchText="Hélios"
 *   searchType="all"
 *   url="searchType=all&amp;searchQuery=H%C3%A9lios&amp;p=1&amp;selectedTab=characters&amp;sd=a"
 *   version="1.0">
 *    <filters />
 *    <characters />
 *  </searchResults>
 * @author mmancino
 */
public class SearchResults {
    @XmlAttribute(name = "pageCurrent")
    public int pageCurrent;
    @XmlAttribute(name = "searchError")
    public String searchError;
    @XmlAttribute(name = "searchFilter")
    public String searchFilter;
    @XmlAttribute(name = "searchMsg")
    public String searchMsg;
    @XmlAttribute(name = "searchText")
    public String searchText;
    @XmlAttribute(name = "searchType")
    public String searchType;
    @XmlAttribute(name = "url")
    public String url;
    @XmlAttribute(name = "version")
    public String version;
    // TODO: Filters
    @XmlElement(name = "character")
    @XmlElementWrapper(name="characters")
    public List<Character> characters;
    @XmlElement(name = "item")
    @XmlElementWrapper(name="items")
    public List<Character> items;
}
