/*
 * EquipedItem.java 13.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.item;

import org.jdom.DataConversionException;
import org.jdom.Document;
import org.jdom.Element;

import de.mancino.armory.enums.Quality;
import de.mancino.armory.enums.Slot;
import de.mancino.utils.XmlDataWrapper;

/**
 * @author mmancino
         <item displayInfoId="64274" durability="60" gem0Id="41285"
         gem1Id="40133" gem2Id="0" gemIcon0="inv_jewelcrafting_icediamond_02"
          gemIcon1="inv_jewelcrafting_gem_40" icon="inv_helmet_152" id="51208"
           level="264" maxDurability="60" name="Sanctified Dark Coven Hood"
           permanentEnchantIcon="spell_fire_masterofelements" permanentEnchantItemId="44877"
            permanentenchant="3820" pickUp="PickUpCloth_Leather01" putDown="PutDownCloth_Leather01"
            randomPropertiesId="0" rarity="4" seed="0" slot="0" />

 */
public class EquipedItem extends XmlDataWrapper {
    public final int durability;
    public final int displayInfoId;
    public final Slot slot;
    public final Quality quality;
    public final int level;
    public final String name;
    public final int id;

    public EquipedItem(Element itemElement) {
        super(new Document(itemElement));
        try {
            this.durability = itemElement.getAttribute("durability").getIntValue();
            this.displayInfoId = itemElement.getAttribute("displayInfoId").getIntValue();
            this.slot = Slot.parse(itemElement.getAttribute("slot").getIntValue());
            this.quality = Quality.parse(itemElement.getAttribute("rarity").getIntValue());
            this.level = itemElement.getAttribute("level").getIntValue();
            this.name = itemElement.getAttribute("name").getValue();
            this.id = itemElement.getAttribute("id").getIntValue();
        } catch (DataConversionException e) {
            // ??? soltle nicht passierne?
            throw new RuntimeException(e);
        }
    }
    @Override
    public String toString() {
        return name + " (iLvl " + level +")";
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof EquipedItem && ((EquipedItem)obj).id == this.id);
    }
}
