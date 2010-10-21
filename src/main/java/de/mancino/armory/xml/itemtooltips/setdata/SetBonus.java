package de.mancino.armory.xml.itemtooltips.setdata;

import javax.xml.bind.annotation.XmlAttribute;


/**
 *
    <setBonus
     desc="Your Flash Heal has a 33% chance to cause the target to heal for 33% of the healed amount over 9 sec."
     threshold="2"/>

 * @author mmancino
 */
public class SetBonus {
    @XmlAttribute
    public String desc;
    @XmlAttribute
    public int threshold;
}
