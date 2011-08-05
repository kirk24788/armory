package de.mancino.armory.json.api.auction;

import java.util.List;

/**
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
 * @author mmancino
 */
public class AuctionHouse {
    public List<Auction> auctions;
}
