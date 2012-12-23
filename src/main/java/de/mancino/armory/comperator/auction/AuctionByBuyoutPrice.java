package de.mancino.armory.comperator.auction;

import java.io.Serializable;
import java.util.Comparator;

import de.mancino.armory.json.api.auction.Auction;

/**
 * Compares two auction items by buyout price.
 *
 * @author mmancino
 */
public class AuctionByBuyoutPrice implements Comparator<Auction>, Serializable {
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Compares two auction items by buyout price.
     *
     * @param o1 first auction item to be compared
     * @param o2 second auction item to be compared
     *
     * @return a negative integer, zero, or a positive integer as the
     * first auction item is cheaper than, equal to, or more expensive than the second.
     */
    public int compare(Auction o1, Auction o2) {
        return (int) (o1.buyout - o2.buyout);
    }
}