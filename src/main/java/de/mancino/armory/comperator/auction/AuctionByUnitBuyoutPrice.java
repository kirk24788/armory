package de.mancino.armory.comperator.auction;

import java.io.Serializable;
import java.util.Comparator;

import de.mancino.armory.json.api.auction.Auction;

/**
 * Compares two auction items by buyout price per unit.
 *
 * Thus 5 units at 2 gold each are cheaper than one unit
 * at 5 gold, although the five units cost 20 gold total compared
 * to 5 gold total.
 *
 * @author mmancino
 */
public class AuctionByUnitBuyoutPrice implements Comparator<Auction>, Serializable {
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Compares two auction items by buyout price per unit.
     *
     * Thus 5 units at 2 gold each are cheaper than one unit
     * at 5 gold, although the five units cost 20 gold total compared
     * to 5 gold total.
     *
     * @param o1 first auction item to be compared
     * @param o2 second auction item to be compared
     *
     * @return a negative integer, zero, or a positive integer as the
     * first auction item is cheaper than, equal to, or more expensive than the second.
     */
    @Override
    public int compare(Auction o1, Auction o2) {
        return (int) ((o1.buyout/o1.quantity) -(o2.buyout/o2.quantity));
    }
}
