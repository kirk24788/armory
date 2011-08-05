package de.mancino.armory.json.api.character.professions;

import java.util.List;

/**
{
  "professions":{
    "primary":[
      {
        "id":755,
        "name":"Jewelcrafting",
        "icon":"inv_misc_gem_01",
        "rank":525,
        "max":525,
        "recipes":[
          25255,
          25278,
          25280,
          25283,
          25284,
          25287
        ]
      },
      {
        "id":164,
        "name":"Blacksmithing",
        "icon":"trade_blacksmithing",
        "rank":525,
        "max":525,
        "recipes":[
          2660,
          2661,
          2662,
          2663,
          2664
        ]
      }
    ],
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
      {
        "id":356,
        "name":"Fishing",
        "icon":"trade_fishing",
        "rank":492,
        "max":525,
        "recipes":[ ]
      },
      {
        "id":185,
        "name":"Cooking",
        "icon":"inv_misc_food_15",
        "rank":525,
        "max":525,
        "recipes":[
          2538,
          2539,
          2540,
          2541,
          2542
        ]
      }
    ]
  }
}
 * @author mmancino
 */
public class Professions {
    public List<Profession> primary;
    public List<Profession> secondary;
}
