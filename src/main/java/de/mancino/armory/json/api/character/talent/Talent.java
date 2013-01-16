/**
 * 
 */
package de.mancino.armory.json.api.character.talent;


/** 
 * {"tier":2,
 * "column":2,
 * "spell":{"id":110913,"name":"Dark Bargain","icon":"ability_deathwing_bloodcorruption_death","description":"Prevents all damage for 8 sec.\n\n\n\nWhen the shield fades, 50% of the damage prevented is dealt over 8 sec. Can be cast while suffering from control impairing effects.","castTime":"Instant","cooldown":"3 min cooldown"}}
 * @author mmancino
 */
public class Talent {
    public int tier;
    public int column;
    public Spell spell;
}
