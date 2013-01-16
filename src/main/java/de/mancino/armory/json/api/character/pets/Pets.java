/**
 * 
 */
package de.mancino.armory.json.api.character.pets;

import java.util.List;

/**
 * "pets":{"numCollected":134,"numNotCollected":389,"
 * collected":[
 * {"name":"Lil' XT","spellId":75906,"creatureId":40703,"itemId":54847,"qualityId":3,"icon":"achievement_boss_xt002deconstructor_01","stats":{"speciesId":256,"breedId":7,"petQualityId":3,"level":11,"health":736,"power":142,"speed":100},"battlePetGuid":"0000000000B05F97","isFavorite":true,"creatureName":"Lil' XT","canBattle":true},
 * {"name":"Deathy","spellId":94070,"creatureId":51122,"itemId":67418,"qualityId":3,"icon":"inv_dragonwhelpcataclysm","stats":{"speciesId":294,"breedId":8,"petQualityId":3,"level":9,"health":568,"power":104,"speed":104},"battlePetGuid":"0000000000B05F88","isFavorite":true,"creatureName":"Deathy","canBattle":true},
 * @author mmancino
 */
public class Pets {
    public int numCollected;
    public int numNotCollected;
    public List<Pet> collected;
}
