package de.mancino.armory.json.api.auction;

import java.io.Serializable;
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
public class AuctionHouse implements Serializable {
    private static final long serialVersionUID = 2L;
    
    public List<Auction> auctions;
}
