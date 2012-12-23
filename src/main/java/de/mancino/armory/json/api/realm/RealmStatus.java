package de.mancino.armory.json.api.realm;

import java.util.List;

import de.mancino.armory.json.JsonResponse;

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
public class RealmStatus extends JsonResponse {
    public List<Realm> realms;
}
