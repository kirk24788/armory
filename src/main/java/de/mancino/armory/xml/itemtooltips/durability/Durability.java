package de.mancino.armory.xml.itemtooltips.durability;

import javax.xml.bind.annotation.XmlAttribute;



/**
*
* Example Snippet:
    <durability current="55" max="55"/>
* @author mmancino
*/
public class Durability {
    @XmlAttribute
    public int current;
    @XmlAttribute
    public int max;

}

