package de.mancino.armory.json.api.character.professions;

import java.util.List;

/**
"secondary":[
      {
        "id":129,
        "name":"First Aid",
        "icon":"spell_holy_sealofsacrifice",
        "rank":525,
        "max":525,
        "recipes":[
          3275,
          3276,
          3277,
          3278,
          7928
        ]
      },
      {
        "id":794,
        "name":"Archaeology",
        "icon":"trade_archaeology",
        "rank":406,
        "max":450,
        "recipes":[ ]
      },
 * @author mmancino
 */
public class Profession {
    public int id;
    public String name;
    public String icon;
    public int rank;
    public int max;
    public List<Integer> recipes;
}
