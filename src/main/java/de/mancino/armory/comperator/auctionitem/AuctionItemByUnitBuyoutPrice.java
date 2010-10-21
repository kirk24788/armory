package de.mancino.armory.comperator.auctionitem;

import java.io.Serializable;
import java.util.Comparator;

import de.mancino.armory.xml.auctionsearch.AuctionItem;

/**
 * Compares two auction items by buyout price per unit.
 *
 * Thus 5 units at 2 gold each are cheaper than one unit
 * at 5 gold, although the five units cost 20 gold total compared
 * to 5 gold total.
 *
 * @author mmancino
 */
public class AuctionItemByUnitBuyoutPrice implements Comparator<AuctionItem>, Serializable {
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
    public int compare(AuctionItem o1, AuctionItem o2) {
        return (int) ((o1.buy/o1.quantity) -(o2.buy/o2.quantity));
    }
}
