package de.mancino.armory.requests.vault;

import org.apache.http.message.BasicNameValuePair;

import de.mancino.armory.ArmoryBaseUri;
import de.mancino.armory.requests.PostRequest;

public class ArmoryVaultRequest extends PostRequest {
    public ArmoryVaultRequest(final ArmoryBaseUri armoryBaseUri, final String requestPath, 
            final BasicNameValuePair ... parameters) {
        super(armoryBaseUri.getHompageUri() + requestPath, parameters);
    }
}
