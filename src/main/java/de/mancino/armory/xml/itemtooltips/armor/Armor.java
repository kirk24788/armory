package de.mancino.armory.xml.itemtooltips.armor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 *
 * <armor armorBonus="0">309</armor>
 * @author mmancino
 *
 */
@XmlType(name="itemtooltips.Armor")
public class Armor {
    @XmlAttribute
    public int armorBonus;
    @XmlValue
    public int armor;
}
