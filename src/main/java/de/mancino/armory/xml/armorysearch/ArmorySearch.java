package de.mancino.armory.xml.armorysearch;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import de.mancino.armory.xml.armorysearch.searchresults.SearchResults;
import de.mancino.armory.xml.armorysearch.tabs.Tab;

/**
 *
 * Example Snippet:
 *  <armorySearch>
 *    <tabs>...</tabs>
 *    <searchResults>...</searchResults>
 *  </armorySearch>
 * @author mmancino
 */
public class ArmorySearch {
    @XmlElementWrapper(name="tabs")
    @XmlElement(name ="tab")
    public List<Tab> tabs;
    public SearchResults searchResults;
}
