package de.mancino.armory.comperator.auctionitem;

import java.util.Comparator;

import de.mancino.armory.xml.auctionsearch.AuctionItem;

public class AuctionItemByUnitBuyoutPrice  implements Comparator<AuctionItem>{
    public int compare(AuctionItem o1, AuctionItem o2) {
        return (int) ((o1.buy/o1.quantity) -(o2.buy/o2.quantity));
    }
}
