package de.mancino.armory.xml.characterInfo.charactertab.basestats;

import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * Example Snippet:
    <intellect
     base="43"
     critHitPercent="-1.00"
     effective="53"
     mana="-1"
     petBonus="-1"/>
 * @author mmancino
 */
public class Intellect {
    @XmlAttribute
    public int base;
    @XmlAttribute
    public float critHitPercent;
    @XmlAttribute
    public int effective;
    @XmlAttribute
    public int mana;
    @XmlAttribute
    public int petBonus;
}