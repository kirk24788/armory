package de.mancino.armory.json.api.character.guild;

/**
{
  "guild":{
    "name":"Roll the Dice",
    "realm":"Medivh",
    "level":25,
    "members":177,
    "achievementPoints":790,
    "emblem":{
      "icon":119,
      "iconColor":"ffffffb1",
      "border":-1,
      "borderColor":"ffffffff",
      "backgroundColor":"ffffff91"
    }
  }
}

 * @author mmancino
 *
 */
public class Guild {
    public String name;
    public String realm;
    public int level;
    public int members;
    public int achievementPoints;
    public Emblem emblem;
}
