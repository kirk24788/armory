package de.mancino.armory;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mancino.armory.exceptions.RequestException;
import de.mancino.armory.requests.wowhead.ItemWowheadRequest;
import de.mancino.armory.xml.wowhead.item.Item;

public class Wowhead {
    /**
     * Logger instance of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Wowhead.class);

    Wowhead() {
    }
    
    public Item getItem(final int itemId) throws RequestException {
        LOG.info("getItem(itemId={})", itemId);
        ItemWowheadRequest request = new ItemWowheadRequest(itemId);
        request.get();
        return request.getData().item;
    }

    public URL getItemURL(final Item item) {
        LOG.info("getItem(item={})", item);
        final String url = "http://static.wowhead.com/images/wow/icons/large/" + item.icon.toLowerCase() + ".jpg";
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("No valid URL: " + url);
        }
    }
}
