/**
 * 
 */
package de.mancino.armory.json.api.character.talent;

/**
 *  * "spell":{
 *  "id":110913,
 *  "name":"Dark Bargain",
 *  "icon":"ability_deathwing_bloodcorruption_death",
 *  "description":"Prevents all damage for 8 sec.\n\n\n\nWhen the shield fades, 50% of the damage prevented is dealt over 8 sec. Can be cast while suffering from control impairing effects.",
 *  "castTime":"Instant",
 *  "cooldown":"3 min cooldown"}}

 * @author mmancino
 */
public class Spell {
    public int id;
    public String name;
    public String icon;
    public String description;
    public String castTime;
    public String range;
    public String powerCost;
    public String cooldown;
}
