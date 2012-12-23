package de.mancino.armory.xml.wowhead.item;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class ItemClass {
    @XmlAttribute
    public int id;
    @XmlElement
    public String className;
}
