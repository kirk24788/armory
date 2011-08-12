package de.mancino.armory.json.wowhead.opensearchresult;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

public abstract class OpenSearchResult {
    public SearchResultType type;
    public String title;


    public static final OpenSearchResult parseFromItemName(final String itemName) {
        final String trimmedItemName = trim(itemName);
        try {
            for(final SearchResultType resultType : SearchResultType.values()) {
                if(trimmedItemName.endsWith(resultType.nameSuffix)) {
                    final OpenSearchResult searchResult = resultType.resultClass.newInstance();
                    searchResult.title = trimmedItemName.substring(0, trimmedItemName.length()-resultType.nameSuffix.length()-1);
                    return searchResult;
                }
            }
            final OpenSearchResult searchResult = new UnknownSearchResult();
            searchResult.type = SearchResultType.UNKNOWN;
            searchResult.title = itemName;
            return searchResult;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void parseData(final String data);
    
    protected List<String> tokenizeData(final String data) {
        final List<String> tokens = new ArrayList<String>();
        final StringTokenizer listTokenizer = new StringTokenizer(data, ",");
        while(listTokenizer.hasMoreTokens()) {
            tokens.add(trim(listTokenizer.nextToken()));
        }
        return tokens;
    }
    
    protected static String trim(final String s) {
        final String s2 = StringUtils.trimToEmpty(s);
        if(s2.startsWith("\"") && s2.endsWith("\"")) {
            return s2.substring(1, s2.length()-1);
        } else {
            return s2;
        }
    }
}
