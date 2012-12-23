package de.mancino.armory.xml.wowhead.item;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Icon {
    @XmlAttribute
    public int displayId;
    @XmlElement
    public String iconName;
}
