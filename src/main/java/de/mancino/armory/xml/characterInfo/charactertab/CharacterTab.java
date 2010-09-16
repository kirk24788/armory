/*
 * CharacterTab.java 16.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.xml.characterInfo.charactertab;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

import de.mancino.armory.xml.characterInfo.charactertab.basestats.BaseStats;
import de.mancino.armory.xml.characterInfo.charactertab.characterbars.CharacterBars;
import de.mancino.armory.xml.characterInfo.charactertab.glyphs.Glyph;
import de.mancino.armory.xml.characterInfo.charactertab.items.Item;
import de.mancino.armory.xml.characterInfo.charactertab.professions.Skill;
import de.mancino.armory.xml.characterInfo.charactertab.pvp.PvP;
import de.mancino.armory.xml.characterInfo.charactertab.resistances.Resistances;
import de.mancino.armory.xml.characterInfo.charactertab.talentspecs.TalentSpec;
import de.mancino.armory.xml.enums.Slot;

/**
*
* Example Snippet:
*  <characterTab>
    <talentSpecs>
      <talentSpecs />
      ...
      <talentSpecs />
    </talentSpecs>
    <buffs />
    <debuffs />
    <pvp />
    <professions />
    <secondaryProfessions />
    <characterBars />
    <baseStats />
    <resistances />
    <melee />
    <ranged />
    <spell />
    <defenses />
    <items>
      <item />
      ...
      <item />
    </items>
    <glyphs>
      <glyph />
      ...
      <glyph />
    </glyphs>
*  </characterTab>

* @author mmancino
*/
public class CharacterTab {

    @XmlElement(name = "talentSpec")
    @XmlElementWrapper(name="talentSpecs")
    public List<TalentSpec> talentSpecs;
    // TODO: Buffs
    // TODO: Debuffs
    public PvP pvp;
    @XmlElement(name = "skill")
    @XmlElementWrapper(name="professions")
    public List<Skill> professions;
    @XmlElement(name = "skill")
    @XmlElementWrapper(name="secondaryProfessions")
    public List<Skill> secondaryProfessions;
    public CharacterBars characterBars;
    public BaseStats baseStats;
    public Resistances resistances;
    /*
TODO:
     <melee>
        <mainHandDamage dps="617.2" max="1115" min="860" percent="0" speed="1.60"/>
        <offHandDamage dps="0.0" max="0" min="0" percent="0" speed="2.00"/>
        <mainHandSpeed hastePercent="0.00" hasteRating="0" value="1.60"/>
        <offHandSpeed hastePercent="0.00" hasteRating="0" value="2.00"/>
        <power base="4509" effective="4509" increasedDps="322.0"/>
        <hitRating increasedHitPercent="7.11" penetration="0" reducedArmorPercent="0.00" value="233"/>
        <critChance percent="5.73" plusPercent="0.00" rating="0"/>
        <expertise additional="24" percent="7.50" rating="202" value="30"/>
      </melee>
      <ranged>
        <weaponSkill rating="0" value="0"/>
        <damage dps="298.4" max="769" min="484" percent="0" speed="2.10"/>
        <speed hastePercent="0.00" hasteRating="0" value="2.10"/>
        <power base="229" effective="229" increasedDps="16.0" petAttack="-1.00" petSpell="-1.00"/>
        <hitRating increasedHitPercent="7.11" penetration="0" reducedArmorPercent="0.00" value="233"/>
        <critChance percent="6.60" plusPercent="0.87" rating="40"/>
      </ranged>
      <spell>
        <bonusDamage>
          <arcane value="0"/>
          <fire value="0"/>
          <frost value="0"/>
          <holy value="0"/>
          <nature value="0"/>
          <shadow value="0"/>
          <petBonus attack="-1" damage="-1" fromType=""/>
        </bonusDamage>
        <bonusHealing value="0"/>
        <hitRating increasedHitPercent="8.88" penetration="0" reducedResist="0" value="233"/>
        <critChance rating="0">
          <arcane percent="0.00"/>
          <fire percent="0.00"/>
          <frost percent="0.00"/>
          <holy percent="0.00"/>
          <nature percent="0.00"/>
          <shadow percent="0.00"/>
        </critChance>
        <penetration value="0"/>
        <manaRegen casting="0.00" notCasting="0.00"/>
        <hasteRating hastePercent="0.00" hasteRating="0"/>
      </spell>
      <defenses>
        <armor base="31663" effective="31888" percent="67.67" petBonus="-1"/>
        <defense decreasePercent="7.04" increasePercent="7.04" plusDefense="176" rating="869" value="400.00"/>
        <dodge increasePercent="13.24" percent="27.48" rating="599"/>
        <parry increasePercent="9.86" percent="22.84" rating="446"/>
        <block increasePercent="0.00" percent="17.04" rating="0"/>
        <resilience damagePercent="0.35" hitPercent="0.16" value="15.00"/>
      </defenses>
     */
    @XmlElement(name = "item")
    @XmlElementWrapper(name="items")
    public List<Item> items;
    @XmlTransient
    public Item getItemInSlot(final Slot slot) {
        for(Item item : items) {
            if(item.slot.equals(slot)) {
                return item;
            }
        }
        return null;
    }
    @XmlElement(name = "glyph")
    @XmlElementWrapper(name="glyphs")
    public List<Glyph> glyphs;
}
