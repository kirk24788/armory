package de.mancino.armory.requests;

import org.apache.http.message.BasicNameValuePair;

import de.mancino.armory.ArmoryBaseUri;

public class ArmoryHomepageRequest extends PostRequest {
    public ArmoryHomepageRequest(final ArmoryBaseUri armoryBaseUri, final String requestPath, 
            final BasicNameValuePair ... parameters) {
        super(armoryBaseUri.getHompageUri() + requestPath, parameters);
    }
}
