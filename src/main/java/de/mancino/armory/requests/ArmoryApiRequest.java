package de.mancino.armory.requests;

import de.mancino.armory.ArmoryBaseUri;

public class ArmoryApiRequest extends GetRequest {
        public ArmoryApiRequest(final ArmoryBaseUri armoryBaseUri, final String requestPath) {
            super(armoryBaseUri.getApiUri() + requestPath);
        }
}
