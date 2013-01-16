/**
 * 
 */
package de.mancino.armory.json.api.character.mounts;

import java.util.List;

import de.mancino.armory.json.JsonResponse;

/**
 * "mounts":{"numCollected":131,"numNotCollected":168,"collected":[
 * {"name":"Albino Drake","spellId":60025,"creatureId":32158,"itemId":44178,"qualityId":4,"icon": "ability_mount_drake_blue","isGround":true,"isFlying":true,"isAquatic":false,"isJumping":false},
 * ...
 * {"name":"Dreadsteed","spellId":23161,"creatureId":14505,"itemId":0,"qualityId":1,"icon":"ability_mount_dreadsteed","isGround":true,"isFlying":false,"isAquatic":true,"isJumping":true},{"name":"Felsteed","spellId":5784,"creatureId":304,"itemId":0,"qualityId":1,"icon":"spell_nature_swiftness","isGround":true,"isFlying":false,"isAquatic":true,"isJumping":true}]},
 * @author mmancino
 */
public class Mounts extends JsonResponse {
    public int numCollected;
    public int numNotCollected;
    public List<Mount> collected;
}
