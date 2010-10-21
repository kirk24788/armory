package de.mancino.armory.xml.armorysearch.tabs;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
*
* Example Snippet:
*  <tab count="42" label="armory.tabs.characters" type="characters"/>
* @author mmancino
*/
@XmlRootElement(name = "tab")
public class Tab {
    @XmlAttribute(name="count")
    public int count;
    @XmlAttribute(name="label")
    public String label;
    @XmlAttribute(name="type")
    public String type;
}
