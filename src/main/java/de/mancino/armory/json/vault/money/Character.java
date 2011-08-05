package de.mancino.armory.json.vault.money;
/**
{
            "name" : "Kawalsky",
            "level" : 85,
            "genderId" : 0,
            "factionId" : 0,
            "classId" : 8,
            "className" : "Mage",
            "raceId" : 7,
            "raceName" : "Gnome",
            "realmName" : "Forscherliga",
            "achievementPoints" : 1470,
            "side" : "ALLIANCE",
            "genderEnum" : "MALE"
          }
 * @author mmancino
 *
 */
public class Character {
    public String name;
    public int level;
    public int genderId;
    public int factionId;
    public int classId;
    public String className;
    public int raceId;
    public String raceName;
    public String realmName;
    public int achievementPoints;
    public String side; // XXX: ENUM
    public String genderEnum; // XXX: ENUM
}
