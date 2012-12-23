package de.mancino.armory.requests.vault;

import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mancino.armory.datatypes.ArmoryBaseUri;
import de.mancino.armory.requests.PostRequest;

public class LoginAuthenticatorRequest extends PostRequest {
    /**
     * Logger instance of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(LoginAuthenticatorRequest.class);

    public LoginAuthenticatorRequest(final ArmoryBaseUri armoryBaseUri, final String authCode) {
        super(armoryBaseUri.getBaseUri() + "login/" + armoryBaseUri.getLanguage().language + 
                "/authenticator?app=armory&ref=https%3A%2F%2Feu.battle.net%2Fwow%2F" 
                + armoryBaseUri.getLanguage().language + "%2F", 
                new BasicNameValuePair("authValue", authCode),
                new BasicNameValuePair("persistAuthenticator", "true"));
        
        if(LOG.isInfoEnabled()) {
            LOG.info("Creating Authenticator Login-Request with Authenticator Code "+authCode);
        }
    }
}
