package de.mancino.armory.json.api.character.items;

import java.util.List;

/**
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
 * @author mmancino
 */
public class TooltipParams {
    public int enchant;
    public int gem0;
    public int gem1;
    public int reforge;
    public boolean extraSocket;
    public List<Integer> set;
}
