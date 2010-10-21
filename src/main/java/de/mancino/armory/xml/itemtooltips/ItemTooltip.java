package de.mancino.armory.xml.itemtooltips;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import de.mancino.armory.xml.enums.Quality;
import de.mancino.armory.xml.generics.StringValueAttribute;
import de.mancino.armory.xml.itemtooltips.armor.Armor;
import de.mancino.armory.xml.itemtooltips.damagedata.DamageData;
import de.mancino.armory.xml.itemtooltips.durability.Durability;
import de.mancino.armory.xml.itemtooltips.equipdata.EquipData;
import de.mancino.armory.xml.itemtooltips.setdata.SetData;
import de.mancino.armory.xml.itemtooltips.socketdata.Socket;

/**
*
* Example Snippet:
* Siehe : http://eu.wowarmory.com/_layout/items/tooltip.xsl
<itemTooltip>
  <id>4023</id>
  <name>Fine Pointed Dagger</name>
  <icon>inv_weapon_shortblade_05</icon>
  <overallQualityId>0</overallQualityId>
  <bonding>0</bonding>
  <classId>2</classId>
  <equipData />
  <damageData />
  <durability/>
  <requiredLevel>39</requiredLevel>
  <itemLevel>44</itemLevel>
  <itemSource value="sourceType.none"/>
</itemTooltip>
* @author mmancino
*/
public class ItemTooltip {
    public int id;
    public String name;
    public String icon;
    @XmlElement(name="overallQualityId")
    public Quality quality;
    public int bonding;
    public int classId; // TODO: Enum?!?
    public EquipData equipData;
    public DamageData damageData;
    public int bonusStrength;
    public int bonusAgility;
    public int bonusStamina;
    public int bonusIntellect;
    public int bonusSpirit;
    public int bonusArcaneResist;
    public int bonusFireResist;
    public int bonusNatureResist;
    public int bonusFrostResist;
    public int bonusShadowResist;
    public int bonusDefenseSkillRating;
    public int bonusExpertise;
    public int bonusBlock;
    public int bonusBlockValue;
    public int bonusDodge;
    public int bonusParry;
    public int bonusResilience;
    public int bonusArmorPen;
    public int bonusAttackPower;
    public int bonusCritRating;
    public int bonusRangedCrit;
    public int bonusToHit;
    public int bonusRangedToHit;
    public int bonusHasteRating;
    public int bonusDamageUndead;
    public int bonusArcaneDamage;
    public int bonusFireDamage;
    public int bonusFrostDamage;
    public int bonusHolyDamage;
    public int bonusNatureDamage;
    public int bonusShadowDamage;
    public int bonusSpellPenetration;
    public int bonusSpellPower;
    public int bonusHealthRegen;
    public int bonusManaRegen;
    public Armor armor;
    @XmlElement(name = "socket")
    @XmlElementWrapper(name="socketData")
    public List<Socket> socketData;
    public Durability durability;
    @XmlElement(name = "class")
    @XmlElementWrapper(name="allowableClasses")
    public List<String> allowableClasses;
    public int requiredLevel;
    public int itemLevel;
    public SetData setData;
    public StringValueAttribute itemSource;
}


