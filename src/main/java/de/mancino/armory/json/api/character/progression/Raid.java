package de.mancino.armory.json.api.character.progression;

import java.util.List;

/**
      {
        "name":"Vault of Archavon",
        "normal":2,
        "heroic":0,
        "id":4603,
        "bosses":[
          {
            "name":"Archavon the Stone Watcher",
            "normalKills":8,
            "heroicKills":0,
            "id":31125
          },
          {
            "name":"Emalon the Storm Watcher",
            "normalKills":2,
            "heroicKills":0,
            "id":33993
          },
          {
            "name":"Koralon the Flame Watcher",
            "normalKills":5,
            "heroicKills":0,
            "id":35013
          },
          {
            "name":"Toravon the Ice Watcher",
            "normalKills":5,
            "heroicKills":0,
            "id":38433
          }
        ]
      },
 * @author mmancino
 */
public class Raid {
    public String name;
    public int normal;
    public int heroic;
    public int id;
    public List<Boss> bosses;
}
