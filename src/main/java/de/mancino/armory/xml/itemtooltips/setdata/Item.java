package de.mancino.armory.xml.itemtooltips.setdata;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <item name="Crimson Acolyte Leggings"/>
 * @author mmancino
 */
@XmlType(name = "itemtooltips.setdata.Item")
public class Item {
    @XmlAttribute
    public String name;
}
