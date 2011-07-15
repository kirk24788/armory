package de.mancino;

import java.util.ArrayList;
import java.util.List;

public class MaxPrice {
    public final long maxPrice;
    public final String itemName;
    
    private MaxPrice(final long maxPrice, final String itemName) {
        this.maxPrice = maxPrice;
        this.itemName = itemName;
    }
    
    public static MaxPrice maxPrice(final String itemName, final long maxPrice) {
        return new MaxPrice(maxPrice, itemName);
    }
    
    public static List<MaxPrice> maxPrices(final int multiplier, final String itemName, List<MaxPrice> otherPrices) {
        final MaxPrice lastPrice = otherPrices.get(0);
        otherPrices.add(0, new MaxPrice(lastPrice.maxPrice * multiplier, itemName));
        return otherPrices;
    }
    
    public static List<MaxPrice> maxPrices(final int multiplier, final String itemName, MaxPrice lastPrice) {
        final List<MaxPrice> otherPrices = new ArrayList<MaxPrice>();
        otherPrices.add(0, new MaxPrice(lastPrice.maxPrice * multiplier, itemName));
        return otherPrices;
    }
}
