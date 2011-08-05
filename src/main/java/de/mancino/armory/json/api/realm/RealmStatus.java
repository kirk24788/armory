package de.mancino.armory.json.api.realm;

import java.util.List;

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
      "type":"pve",
      "queue":false,
      "status":true,
      "population":"medium",
      "name":"Medivh",
      "slug":"medivh"
    }
  ]
}
 * @author mmancino
 */
public class RealmStatus {
    public List<Realm> realms;
}
