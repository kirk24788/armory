package de.mancino.armory.json.api.auction;

import java.io.Serializable;

/**
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
 * @author mmancino
 */
public class Auction implements Serializable {
    private static final long serialVersionUID = 2L;
    
    public long auc;
    public int item;
    public String owner;
    public long bid;
    public long buyout;
    public int quantity;
    public String timeLeft;
}
