package de.mancino.armory.json.api.auction;

import de.mancino.armory.json.JsonResponse;

/**
{
    "realm": {
        "name": "Medivh",
        "slug": "medivh"
    },
    "alliance": {
        "auctions": [
            {
                "auc": 500,
                "item": 49284,
                "owner": "Uther",
                "bid": 150000,
                "buyout": 450000,
                "quantity": 11
            }
        ]
    },
    "horde": {
        "auctions": [
            {
                "auc": 501,
                "item": 44575,
                "owner": "Thrall",
                "bid": 26751,
                "buyout": 57665,
                "quantity": 1
            }
        ]
    },
    "neutral": {
        "auctions": [
            {
                "auc": 502,
                "item": 63271,
                "owner": "Arthas",
                "bid": 20000,
                "buyout": 50000,
                "quantity": 1
            }
        ]
    }
}
 * @author mmancino
 */
public class Auctions extends JsonResponse {
    public Realm realm;
    public AuctionHouse alliance;
    public AuctionHouse horde;
    public AuctionHouse neutral;
    public long timestamp = 0;
}
