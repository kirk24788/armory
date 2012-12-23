package de.mancino.armory.json.api.auction;

import java.util.List;

import de.mancino.armory.json.JsonResponse;

/**
{
  "files":[
    {
      "url":"http://eu.battle.net/auction-data/forscherliga/auctions.json",
      "lastModified":1312531139000
    }
  ]
}
 * @author mmancino
 */
public class AuctionFiles extends JsonResponse  {
    public List<AuctionFile> files;
}
