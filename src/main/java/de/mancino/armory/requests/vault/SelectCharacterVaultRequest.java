package de.mancino.armory.requests.vault;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mancino.armory.datatypes.ArmoryBaseUri;
import de.mancino.armory.exceptions.RequestException;
import de.mancino.armory.exceptions.ResponseParsingException;
import de.mancino.armory.requests.GetRequest;

public class SelectCharacterVaultRequest extends ArmoryVaultRequest {
    /**
     * Logger instance of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(SelectCharacterVaultRequest.class);

    private BasicNameValuePair[] selectCharacterParams;

    private String index;

    public SelectCharacterVaultRequest(final ArmoryBaseUri armoryBaseUri, final String realmName, final String charName) {
        super(armoryBaseUri, "pref/character");
        index = getCharacterIndex(armoryBaseUri, realmName, charName);
        selectCharacterParams = new BasicNameValuePair[] {
                new BasicNameValuePair("index",  index),
                new BasicNameValuePair("xstoken", getCookieValue("xstoken"))
        };
    }

    @Override
    public BasicNameValuePair[] getParameters() {
        return selectCharacterParams;
    }
    
    @Override
    public int post() throws RequestException {
        if(index.equals("-1")) {
            return 200;
        } else {
            return super.post();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Header[] getAdditionalHeaders() {
        return new Header[] {new BasicHeader("X-Requested-With","XMLHttpRequest"),
                new BasicHeader("Referer","https://eu.battle.net/wow/de/vault/character/auction/alliance/browse"),
                new BasicHeader("Origin","https://eu.battle.net")};
    }

    private String getCharacterIndex(final ArmoryBaseUri armoryBaseUri, final String realmName, final String charName) {
        final String encodedRealmName = urlEncode(realmName);
        final String encodedCharName = urlEncode(charName);
        GetRequest getRequest = new GetRequest(armoryBaseUri.getVaultUri()) {
            private static final String PIN_STRING = "CharSelect.pin(";
            private int charId;
            @Override
            protected void parseResponse(byte[] responseAsBytes) throws ResponseParsingException {
                final String responseAsString = new String(responseAsBytes);
                final int startingIndex = StringUtils.indexOfIgnoreCase(responseAsString, PIN_STRING) - 100;
                LOG.trace("Expecting Character Selection to start at Index {}", startingIndex);
                final String linkString = "/character/" + encodedRealmName + "/" + encodedCharName;
                final int linkIndex = StringUtils.indexOfIgnoreCase(responseAsString, linkString, startingIndex);
                if(linkIndex > 0) {
                    LOG.trace("Found linkString '{}' at Index {}", linkString, linkIndex);
                    final int pinStringIndex = StringUtils.indexOfIgnoreCase(responseAsString, PIN_STRING, linkIndex);
                    LOG.trace("Found pinString '{}' at Index {}", PIN_STRING, pinStringIndex);
                    final int idStartIndex = pinStringIndex+PIN_STRING.length();
                    final int idEndIndex = responseAsString.indexOf(',', idStartIndex);
                    LOG.trace("Expecting ID between Index {} and Index {}", idStartIndex, idEndIndex);
                    if(LOG.isTraceEnabled()) {
                        LOG.trace("Range: {}", responseAsString.substring(linkIndex, idEndIndex));
                    }
                    try {
                        final String subString = responseAsString.substring(idStartIndex, idEndIndex);
                        LOG.trace("Found pinString with SubString: {}", subString);
                        charId = Integer.valueOf(subString) ;
                    } catch (NumberFormatException e) {
                        final String msg = "Couldn't parse ID!";
                        LOG.error(msg);
                        throw new ResponseParsingException(msg, e);
                    }
                } else {
                    charId=-1;
                }
            }
            @Override
            public int get() throws RequestException {
                super.get();
                return charId;
            }
        };
        try {
            final String id = String.valueOf(getRequest.get());
            LOG.info("Selecting Character '{}' with ID '{}'", encodedCharName, id);
            return id;
        } catch (RequestException e) {
            return "0";
        }
    }
}
