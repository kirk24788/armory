package de.mancino.armory.json.wowhead.opensearchresult;

public class UnknownSearchResult extends OpenSearchResult {
    public String data;
    
    @Override
    public void parseData(final String data) {
        this.data = data;
    }
}
