package de.mancino.armory.xml.itemtooltips.setdata;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
      <setData>
        <name>Crimson Acolyte's Raiment</name>
        <item name="Crimson Acolyte Leggings"/>
        <item name="Crimson Acolyte Robe"/>
        <item name="Crimson Acolyte Shoulderpads"/>
        <item name="Crimson Acolyte Gloves"/>
        <item name="Crimson Acolyte Hood"/>
        <setBonus desc="Your Flash Heal has a 33% chance to cause the target to heal for 33% of the healed amount over 9 sec." 
                  threshold="2"/>
        <setBonus desc="Increases the effect of Power Word: Shield by 5% and Circle of Healing by 10%." threshold="4"/>
      </setData>

 * @author mmancino
 */
public class SetData {
    public String name;
    @XmlElement(name = "item")
    public List<Item> items;
    @XmlElement(name = "setBonus")
    public List<SetBonus> setBonuses;
}
