/*
 * Item.java 16.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.xml.characterInfo.charactertab.items;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import de.mancino.armory.xml.enums.Quality;
import de.mancino.armory.xml.enums.Slot;

/**
 *
 * Example Snippet:
    <item
     displayInfoId="64570"
     durability="100"
     gem0Id="41380"
     gem1Id="40130"
     gem2Id="0"
     gemIcon0="inv_jewelcrafting_shadowspirit_02"
     gemIcon1="inv_jewelcrafting_gem_40"
     icon="inv_helmet_158"
     id="51218"
     level="264"
     maxDurability="100"
     name="Sanctified Ymirjar Lord's Greathelm"
     permanentEnchantIcon="ability_warrior_swordandboard"
     permanentEnchantItemId="44878"
     permanentenchant="3818"
     pickUp="PickUpLargeChain"
     putDown="PutDownLArgeChain"
     randomPropertiesId="0"
     rarity="4"
     seed="0"
     slot="0" />
 * @author mmancino
 */
@XmlType(name = "characterInfo.charactertab.Item")
public class Item {
    @XmlAttribute
    public int displayInfoId;
    @XmlAttribute
    public int durability;
    @XmlAttribute
    public int gem0Id;
    @XmlAttribute
    public int gem1Id;
    @XmlAttribute
    public int gem2Id;
    @XmlAttribute
    public String gemIcon0;
    @XmlAttribute
    public String gemIcon1;
    @XmlAttribute
    public String gemIcon2;
    @XmlAttribute
    public String icon;
    @XmlAttribute
    public int id;
    @XmlAttribute
    public int level;
    @XmlAttribute
    public int maxDurability;
    @XmlAttribute
    public String name;
    @XmlAttribute
    public String permanentEnchantIcon;
    @XmlAttribute
    public int permanentEnchantItemId;
    @XmlAttribute
    public int permanentenchant;
    @XmlAttribute
    public String pickUp;
    @XmlAttribute
    public String putDown;
    @XmlAttribute
    public int randomPropertiesId;
    @XmlAttribute
    public Quality rarity;
    @XmlAttribute
    public int seed;
    @XmlAttribute
    public Slot slot;
}
