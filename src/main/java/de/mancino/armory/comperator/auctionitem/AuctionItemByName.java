package de.mancino.armory.comperator.auctionitem;

import java.io.Serializable;
import java.util.Comparator;

import de.mancino.armory.xml.auctionsearch.AuctionItem;

/**
 * Compares two auction items by name.
 *
 * @author mmancino
 */
public class AuctionItemByName implements Comparator<AuctionItem>, Serializable {
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Compares two auction items by by name.     *
     * @param o1 first auction item to be compared
     * @param o2 second auction item to be compared
     *
     * @return the result of {@link String}.compare().
     */
    public int compare(AuctionItem o1, AuctionItem o2) {
        return o1.name.compareTo(o2.name);
    }
}