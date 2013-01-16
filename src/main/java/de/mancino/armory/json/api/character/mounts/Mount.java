/**
 * 
 */
package de.mancino.armory.json.api.character.mounts;

import de.mancino.armory.json.JsonResponse;


/**
 * {"name":"Albino Drake",
 * "spellId":60025,
 * "creatureId":32158,
 * "itemId":44178,
 * "qualityId":4,
 * "icon":"ability_mount_drake_blue",
 * "isGround":true,
 * "isFlying":true,
 * "isAquatic":false,
 * "isJumping":false},
 * @author mmancino
 */
public class Mount extends JsonResponse {
    public String name;
    public int spellId;
    public int creatureId;
    public int itemId;
    public int qualityId;
    public String icon;
    public boolean isGround;
    public boolean isFlying;
    public boolean isAquatic;
    public boolean isJumping;
}
