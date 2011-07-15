package de.mancino;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.mancino.armory.comperator.auctionitem.AuctionItemByUnitBuyoutPrice;
import de.mancino.armory.xml.auctionsearch.AuctionItem;
import de.mancino.armory.xml.itemtooltips.ItemTooltip;
import de.mancino.exceptions.ArmoryConnectionException;

public class Utils {


    public static String getStatistics(final List<AuctionItem> items) {
        final StringBuffer sb = new StringBuffer();
        Collections.sort(items, new AuctionItemByUnitBuyoutPrice());
        sb.append(" * Minimaler Preis: ").append(formatPrice(minPricePerUnit(items))).append("\n");
        sb.append(" * Median Preis: ").append(formatPrice(medianPricePerUnit(items))).append("\n");
        return sb.toString();
    }

    private static long minPricePerUnit(final List<AuctionItem> items) {
        return pricePerUnit(items.get(0));
    }

    private static long medianPricePerUnit(final List<AuctionItem> items) {
        if(items.size()%2==1) {
            return pricePerUnit(items.get(items.size()/2));
        } else {
            int pos = items.size()/2;
            long low = pricePerUnit(items.get(pos-1));
            long high = pricePerUnit(items.get(pos));
            return (low+high)/2;
        }
    }

    private static long pricePerUnit(final AuctionItem item) {
        return item.buy / item.quantity;
    }

    public List<AuctionItem> filterByPrice(final List<AuctionItem> items, long maxPricePerItem) {
        List<AuctionItem> filteredItems = new ArrayList<AuctionItem>();
        for(AuctionItem item : items) {
            if(item.buy <= item.quantity * maxPricePerItem && item.buy> 0) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    public List<AuctionItem> filterByByoutOnly(final List<AuctionItem> items) {
        List<AuctionItem> filteredItems = new ArrayList<AuctionItem>();
        for(AuctionItem item : items) {
            if(item.buy> 0) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    public long gold(long gold) {
        return gold * 10000;
    }

    public static long gold(long gold, long silver) {
        return gold * 10000 + silver * 100;
    }

    public static String formatPrice(long price) {
        if(price<=0) {
            return "n/a";
        } else {
            String priceStr = price/10000 + "g";
            long next = (price/100)%100;
            priceStr += next<10 ? "0"+next : next;
            priceStr += "s";
            next = price%100;
            priceStr += next<10 ? "0"+next : next;
            priceStr += "c";
            return  priceStr;
        }
    }

    public long increment(Long integer) {
        if(integer != null) {
            return integer.longValue() + 1;
        } else {
            return 1;
        }
    }
}
