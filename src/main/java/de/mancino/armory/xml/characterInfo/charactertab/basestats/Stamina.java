package de.mancino.armory.xml.characterInfo.charactertab.basestats;

import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * Example Snippet:
    <stamina
     base="105"
     effective="1475"
     health="14570"
     petBonus="-1"/>
 * @author mmancino
 */
public class Stamina {
    @XmlAttribute
    public int base;
    @XmlAttribute
    public int effective;
    @XmlAttribute
    public int health;
    @XmlAttribute
    public int petBonus;
}