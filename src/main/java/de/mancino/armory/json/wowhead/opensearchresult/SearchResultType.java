package de.mancino.armory.json.wowhead.opensearchresult;

public enum SearchResultType {
    ITEM_SEARCH("(Item)", ItemSearchResult.class),
    UNKNOWN("(Unknown)", UnknownSearchResult.class);
    public final String nameSuffix;
    public final Class<? extends OpenSearchResult> resultClass;
    
    private SearchResultType(final String nameSuffix, final Class<? extends OpenSearchResult> resultClass) {
        this.nameSuffix = nameSuffix;
        this.resultClass = resultClass;
    }
}
