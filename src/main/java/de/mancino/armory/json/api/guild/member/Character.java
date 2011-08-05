package de.mancino.armory.json.api.guild.member;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 "character":{
        "name":"Mequieres",
        "realm":"Medivh",
        "class":5,
        "race":1,
        "gender":"female",
        "level":85,
        "achievementPoints":6910,
        "thumbnail":"medivh/66/3930434-avatar.jpg"
      },
 * @author mmancino
 *
 */
public class Character {
    public String name;
    public String realm;
    @JsonProperty(value="class")
    public int classId;
    public int race;
    public String gender;
    public int level;
    public int achievementPoints;
    public String thumbnail;
}
