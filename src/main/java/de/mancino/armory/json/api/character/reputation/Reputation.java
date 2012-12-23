package de.mancino.armory.json.api.character.reputation;

/**
{
  "reputation":[
    {
      "id":369,
      "name":"Gadgetzan",
      "standing":5,
      "value":10740,
      "max":12000
    },
    {
      ...
    },
    {
      ...
    }
  ]
}
 * @author mmancino
 */
public class Reputation {
    public int id;
    public String name;
    public int standing; // XXX: Enum!
    public int value;
    public int max;
}
