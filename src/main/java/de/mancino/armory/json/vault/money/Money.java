package de.mancino.armory.json.vault.money;

import de.mancino.armory.json.JsonResponse;


/**
 
{
        "auctionFaction" : 0,
        "character" : {...},
        "money" : 260199832
      }
 
 * @author mmancino
 *
 */
public class Money extends JsonResponse {
    public int auctionFaction;
    public Character character;
    public long money;
}
