package de.mancino.armory.requests.wowhead;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import de.mancino.armory.exceptions.ResponseParsingException;
import de.mancino.armory.json.wowhead.opensearchresult.OpenSearchResult;
import de.mancino.armory.requests.GetRequest;

public class OpenSearchWowheadRequest extends GetRequest {

    private String searchTerm;
    private final List<OpenSearchResult> searchResults = new ArrayList<OpenSearchResult>();

    public OpenSearchWowheadRequest(final String searchTerm) {
        super("http://www.wowhead.com/search?q=" + urlEncode(searchTerm) + "&opensearch");
    }

    @Override
    protected void parseResponse(byte[] responseAsBytes) throws ResponseParsingException {
        final String javascriptArrayAsString = new String(responseAsBytes);
        final StringTokenizer arrayTokenizer =  new StringTokenizer(javascriptArrayAsString, "[]");
        try {
            searchTerm = arrayTokenizer.nextToken();
            if(arrayTokenizer.hasMoreTokens()) {
                final String resultList = arrayTokenizer.nextToken();
                final StringTokenizer resultListArrayTokenizer =  new StringTokenizer(resultList, ",");
                while(resultListArrayTokenizer.hasMoreTokens()) {
                    final String resultListToken =  resultListArrayTokenizer.nextToken();
                    searchResults.add(OpenSearchResult.parseFromItemName(resultListToken));
                }
                int pos = 0;
                while(resultListArrayTokenizer.hasMoreTokens()){
                    final String token =  resultListArrayTokenizer.nextToken();
                    if(token.length() > 5) {
                        searchResults.get(pos).parseData(token);
                        pos++;
                    }
                }
            }
        } catch (NoSuchElementException e) {
            throw new ResponseParsingException("Couldn't parse Javascript Array!\n" + javascriptArrayAsString, e);
        }
    }

    /**
     * @return the searchResults
     */
    public List<OpenSearchResult> getSearchResults() {
        return Collections.unmodifiableList(searchResults);
    }

    /**
     * @return the searchResults
     */
    @SuppressWarnings("unchecked")
    public <T extends OpenSearchResult> List<T> getSearchResults(Class<T> typeDef) {
        List<T> filteredResults = new ArrayList<T>();
        for(OpenSearchResult result : searchResults) {
            if(result.getClass().equals(typeDef)) {
                filteredResults.add(((T)result));
            }
        }
        return filteredResults;
    }

    /**
     * @return the searchTerm
     */
    public String getSearchTerm() {
        return searchTerm;
    }
}
