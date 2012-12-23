package de.mancino.armory.json.api.item;

import java.util.List;

import de.mancino.armory.json.JsonResponse;
import de.mancino.armory.json.api.item.bonusstat.BonusStat;
import de.mancino.armory.json.api.item.itemsource.ItemSource;
import de.mancino.armory.json.api.item.itemspell.ItemSpell;
import de.mancino.armory.json.api.item.socketinfo.SocketInfo;
import de.mancino.armory.json.api.item.weaponinfo.WeaponInfo;

/**
{
  "id":38268,
  "disenchantingSkillRank":-1,
  "description":"Give to a Friend",
  "name":"Spare Hand",
  "stackable":1,
  "itemBind": 0,
  "bonusStats":[],
  "itemSpells":[],
  "buyPrice":12,
  "itemClass":{
    "class":2,
    "name":"Weapon"
  },
  "itemSubClass":{
    "subclassId":14,
    "name":"Miscellaneous"
  },
  "containerSlots":0,
  "weaponInfo":{
    "damage":[
      {
        "minDamage":1,
        "maxDamage":2
      }
    ],
    "weaponSpeed":2.5,
    "dps":0.6
  },
  "inventoryType":13,
  "equippable":true,
  "itemLevel":1,
  "maxCount":0,
  "maxDurability":16,
  "minFactionId":0,
  "minReputation":0,
  "quality":0,
  "sellPrice":2,
  "requiredLevel":70,
  "requiredSkill":0,
  "requiredSkillRank":0,
  "itemSource":{
    "sourceId":0,
    "sourceType":"NONE"
  },
  "baseArmor":0,
  "hasSockets":false,
  "isAuctionable":true
}
 * @author mmancino
 */
public class Item extends JsonResponse {
    public List<Integer> allowableClasses;
    public int id;
    public int disenchantingSkillRank;
    public String description;
    public String name;
    public String icon;
    public int stackable;
    public int itemBind;
    public List<BonusStat> bonusStats;
    public List<ItemSpell> itemSpells;
    public WeaponInfo weaponInfo;
    public long buyPrice;
    public int itemClass;
    public int itemSubClass;
    public int itemSet;
    public int containerSlots;
    public int inventoryType;
    public boolean equippable;
    public int itemLevel;
    public int maxCount;
    public int maxDurability;
    public int minFactionId;
    public int minReputation;
    public int quality;
    public long sellPrice;
    public int requiredSkill;
    public int requiredLevel;
    public int requiredSkillRank;
    public SocketInfo socketInfo;
    public ItemSource itemSource;
    // bonusStats???
    public int baseArmor;
    public boolean hasSockets;
    public boolean isAuctionable;
    
    // itemSpells ???
 /*
    "bonusStats":[],
    "weaponInfo":{
      "damage":[
        {
          "minDamage":1,
          "maxDamage":2
        }
      ],
      "weaponSpeed":2.5,
      "dps":0.6
    },
    
    */
}
