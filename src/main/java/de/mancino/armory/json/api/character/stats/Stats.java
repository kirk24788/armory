package de.mancino.armory.json.api.character.stats;

import org.codehaus.jackson.annotate.JsonProperty;

/**
{
  "stats":{
    "health":155263,
    "powerType":"mana",
    "power":24732,
    "str":3395,
    "agi":142,
    "sta":8017,
    "int":106,
    "spr":117,
    "attackPower":7025,
    "rangedAttackPower":0,
    "mastery":23.26104,
    "masteryRating":2736,
    "crit":1.351225,
    "critRating":0,
    "hitRating":58,
    "hasteRating":0,
    "expertiseRating":238,
    "spellPower":2133,
    "spellPen":0,
    "spellCrit":3.498852,
    "spellCritRating":0,
    "mana5":1191.0,
    "mana5Combat":1170.0,
    "armor":36505,
    "dodge":12.206712,
    "dodgeRating":1565,
    "parry":13.33957,
    "parryRating":1614,
    "block":57.335938,
    "blockRating":0,
    "resil":0,
    "mainHandDmgMin":2146.0,
    "mainHandDmgMax":2868.0,
    "mainHandSpeed":2.6,
    "mainHandDps":964.1704,
    "mainHandExpertise":10,
    "offHandDmgMin":0.0,
    "offHandDmgMax":0.0,
    "offHandSpeed":2.0,
    "offHandDps":0.0,
    "offHandExpertise":7,
    "rangedDmgMin":-1.0,
    "rangedDmgMax":-1.0,
    "rangedSpeed":-1.0,
    "rangedDps":-1.0,
    "rangedCrit":1.351225,
    "rangedCritRating":0,
    "rangedHitRating":58
  }
}
 * 
 * @author mmancino
 */
public class Stats {
    public int health;
    public String powerType; /// XXX: ENUM
    public int power;
    public int str;
    public int agi;
    public int sta;
    @JsonProperty(value="int")
    public int intellect;
    public int spr;
    public int attackPower;
    public int rangedAttackPower;
    public float mastery;
    public int masteryRating;
    public float crit;
    public int critRating;
    public float hitPercent;
    public int hitRating;
    public float spellHitPercent;
    public int spellHitRating;
    public int hasteRating;
    public int expertiseRating;
    public int spellPower;
    public int spellPen;
    public float spellCrit;
    public int spellCritRating;
    public float mana5;
    public float mana5Combat;
    public int armor;
    public float dodge;
    public int dodgeRating;
    public float parry;
    public int parryRating;
    public float block;
    public int blockRating;
    public int resil;
    public float mainHandDmgMin;
    public float mainHandDmgMax;
    public float mainHandSpeed;
    public float mainHandDps;
    public int mainHandExpertise;
    public float offHandDmgMin;
    public float offHandDmgMax;
    public float offHandSpeed;
    public float offHandDps;
    public int offHandExpertise;
    public float rangedDmgMin;
    public float rangedDmgMax;
    public float rangedSpeed;
    public float rangedDps;
    public float rangedCrit;
    public int rangedCritRating;
    public int rangedHitRating;
    public float rangedHitPercent;
}
