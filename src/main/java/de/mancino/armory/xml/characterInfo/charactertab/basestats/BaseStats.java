package de.mancino.armory.xml.characterInfo.charactertab.basestats;

/**
*
* Example Snippet:
      <baseStats>
        <strength attack="113" base="113" block="-1" effective="123"/>
        <agility armor="3456" attack="1718" base="189" critHitPercent="20.44" effective="1728"/>
        <stamina base="105" effective="1475" health="14570" petBonus="-1"/>
        <intellect base="43" critHitPercent="-1.00" effective="53" mana="-1" petBonus="-1"/>
        <spirit base="69" effective="79" healthRegen="27" manaRegen="-1"/>
        <armor base="7356" effective="7356" percent="32.57" petBonus="-1"/>
      </baseStats>
* @author mmancino
*/
public class BaseStats {
    public Strength strength;
    public Agility agility;
    public Stamina stamina;
    public Intellect intellect;
    public Spirit spirit;
    public Armor armor;
}
