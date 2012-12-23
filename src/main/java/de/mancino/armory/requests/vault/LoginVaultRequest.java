package de.mancino.armory.requests.vault;

import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mancino.armory.datatypes.ArmoryBaseUri;
import de.mancino.armory.requests.PostRequest;

public class LoginVaultRequest extends PostRequest {
    /**
     * Logger instance of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(LoginVaultRequest.class);

    public LoginVaultRequest(final ArmoryBaseUri armoryBaseUri, final String accountName, final String password) {
        super(armoryBaseUri.getBaseUri() + "login/" + armoryBaseUri.getLanguage().language + 
                "/?app=armory&ref=https%3A%2F%2Feu.battle.net%2Fwow%2F" 
                + armoryBaseUri.getLanguage().language + "%2F", 
                new BasicNameValuePair("accountName", accountName),
                new BasicNameValuePair("password", password),
                new BasicNameValuePair("persistLogin", "on"));
        
        if(LOG.isInfoEnabled()) {
            LOG.info("Creating Login-Request for User "+accountName +":" + password.charAt(0) + "***"
                    + password.charAt(password.length()-1));
        }
    }
}
