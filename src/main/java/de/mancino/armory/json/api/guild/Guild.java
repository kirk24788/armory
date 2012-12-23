package de.mancino.armory.json.api.guild;

import java.util.List;

import de.mancino.armory.json.JsonResponse;
import de.mancino.armory.json.api.generic.achievments.Achievements;
import de.mancino.armory.json.api.guild.member.Member;

/**
{ "name":"Roll the Dice", "level":25, "side":"alliance", "achievementPoints":800 }
 * @author mmancino
 */
public class Guild extends JsonResponse  {
    public String name;
    public String realm;
    public long lastModified;
    public int level;
    public String side; // XXX: ENUM
    public int achievementPoints;
    public List<Member> members;
    public Achievements achievements;
}
