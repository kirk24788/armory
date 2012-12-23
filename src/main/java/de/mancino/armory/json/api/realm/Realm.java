package de.mancino.armory.json.api.realm;

/**
{
  "realms":[
    {
      "type":"pve",
      "queue":false,
      "status":true,
      "population":"high",
      "name":"Lightbringer",
      "slug":"lightbringer"
    },
    {
      ...
    }
  ]
}
 * @author mmancino
 */
public class Realm {
    public String type; // XXX: ENUM!
    public boolean queue;
    public boolean status;
    public String population;  // XXX: ENUM!
    public String name;
    public String slug;
}
