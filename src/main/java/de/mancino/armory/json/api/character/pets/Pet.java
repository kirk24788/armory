package de.mancino.armory.json.api.character.pets;

/**
{"name":"Lil' XT",
"spellId":75906,
"creatureId":40703,
"itemId":54847,
"qualityId":3,
"icon":"achievement_boss_xt002deconstructor_01",
"stats":{"speciesId":256,"breedId":7,"petQualityId":3,"level":11,"health":736,"power":142,"speed":100},
"battlePetGuid":"0000000000B05F97",
"isFavorite":true,
"creatureName":"Lil' XT",
"canBattle":true},
 *  * @author mmancino
 */
public class Pet {
    public String name;
    public int spellId;
    public int creatureId;
    public int itemId;
    public int qualityId;
    public String icon;
    public Stats stats;
    public String battlePetGuid;
    public boolean isFavorite;
    public String creatureName;
    public boolean canBattle;
}
