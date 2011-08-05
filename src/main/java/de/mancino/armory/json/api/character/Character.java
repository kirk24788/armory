package de.mancino.armory.json.api.character;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import de.mancino.armory.json.api.character.appearance.Appearance;
import de.mancino.armory.json.api.character.guild.Guild;
import de.mancino.armory.json.api.character.items.Items;
import de.mancino.armory.json.api.character.pet.Pet;
import de.mancino.armory.json.api.character.professions.Professions;
import de.mancino.armory.json.api.character.progression.Progression;
import de.mancino.armory.json.api.character.reputation.Reputation;
import de.mancino.armory.json.api.character.stats.Stats;
import de.mancino.armory.json.api.character.talent.Talent;
import de.mancino.armory.json.api.character.title.Title;
import de.mancino.armory.json.api.generic.achievments.Achievements;


/**
{"realm": "Medivh", "name": "Uther", "level": 85, "lastModified": 1307596000000, "thumbnail": "medivh/1/1-avatar.jpg",
"race": 1, "achievementPoints": 9745, "gender": 0, "class": 2, "guild": { ... } }
 * @author mmancino
 *
 */
public class Character {
    public String realm;
    public String name;
    public int level;
    public long lastModified;
    public String thumbnail;
    public int race;
    public int achievementPoints;
    public int gender;
    @JsonProperty(value="class")
    public int classId;
    /**
     * A summary of the guild that the character belongs to. If the character does not belong to a guild and 
     * this field is requested, this field will not be exposed.
     */
    public Guild guild;


    /**
     * A map of character attributes and stats.
     */
    public Stats stats;

    /**
     * A list of talent structures.
     */
    public List<Talent> talents;


    /**
     * A list of items equipted by the character. Use of this field will also include the average item level 
     * and average item level equipped for the character.
     */
    public Items items;

    /**
     * A list of the factions that the character has an associated reputation with.
     */
    public List<Reputation> reputation;


    /**
     *  A list of the titles obtained by the character including the currently selected title.
     */
    public List<Title> titles;


    /**
     * A list of the character's professions. It is important to note that when this information is retrieved, 
     * it will also include the known recipes of each of the listed professions.
     */
    public Professions professions;
    
    /**
     * A map of values that describes the face, features and helm/cloak display preferences and attributes.
     */
    public Appearance appearance;

    /**
     * A list of all of the non-combat pets obtained by the character.
     */
    public List<Integer> companions;
    

    /**
     * A list of all of the mounts obtained by the character.
     */
    public List<Integer> mounts;
    

    /**
     * A list of all of the combat pets obtained by the character.
     */
    public List<Pet> pets;
    

    /**
     * A map of achievement data including completion timestamps and criteria information.
     */
    public Achievements achievements;
    

    /**
     * A list of raids and bosses indicating raid progression and completedness.
     */
    public Progression progression;
}
