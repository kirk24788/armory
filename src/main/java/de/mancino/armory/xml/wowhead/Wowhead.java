package de.mancino.armory.xml.wowhead;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.mancino.armory.xml.wowhead.item.Item;

@XmlRootElement(name = "wowhead")
public class Wowhead {
    @XmlElement
    public Item item;
    @XmlElement
    public String error;
}
