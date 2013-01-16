package de.mancino.armory.json.api.character.items;

import java.util.List;


/**
 { "icon" : "inv_pants_robe_pvppriest_c_01",
          "id" : 60475,
          "name" : "Vicious Gladiator's Satin Leggings",
          "quality" : 4,
          "tooltipParams" : { "enchant" : 4112,
              "gem0" : 52207,
              "gem1" : 52245,
              "set" : [ 60474,
                  60476,
                  60477,
                  60475,
                  60473
                ]
            }
        },
 * @author mmancino
 */
public class Item {
    public String icon;
    public int id;
    public int itemLevel;
    public String name;
    public int quality; // XXX: ENUM!
    public TooltipParams tooltipParams;
    public List<Stat> stats;
    public int armor;
    public WeaponInfo weaponInfo;
}
