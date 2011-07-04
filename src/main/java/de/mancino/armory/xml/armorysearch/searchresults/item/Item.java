package de.mancino.armory.xml.armorysearch.searchresults.item;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import de.mancino.armory.xml.enums.Quality;

/**
*
* Example Snippet:
* 
* 
* <item 
*  canAuction="1" 
*  icon="inv_elemental_eternal_life" 
*  id="35625" 
*  name="Eternal Life" 
*  rarity="2" 
*  url="i=35625">
*         <filter name="itemLevel" value="75"/>
*         <filter name="source" value="sourceType.createdBySpell"/>
*         <filter name="relevance" value="1"/>
*  </item>
*  
* @author mmancino
*/
@XmlType(name="armorysearch.searchresults.Item")
public class Item {
    @XmlAttribute
    public boolean canAuction;
    @XmlAttribute
    public String icon;
    @XmlAttribute
    public int id;
    @XmlAttribute
    public String name;
    @XmlAttribute
    public Quality rarity;
    @XmlAttribute
    public String url;
}
