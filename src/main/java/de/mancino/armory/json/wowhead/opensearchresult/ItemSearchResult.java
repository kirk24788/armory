package de.mancino.armory.json.wowhead.opensearchresult;

import java.util.List;

/**
 * [3, 1361, "INV_Misc_LeatherScrap_08", 1]
 * @author mmancino
 */
public class ItemSearchResult extends OpenSearchResult {
    public int id;
    public String icon;
    
    @Override
    public void parseData(final String data) {
        List<String> tokens = tokenizeData(data);
        id = Integer.parseInt(tokens.get(1));
        icon = tokens.get(2);
    }
}
