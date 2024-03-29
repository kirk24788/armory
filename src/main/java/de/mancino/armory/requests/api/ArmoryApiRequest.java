package de.mancino.armory.requests.api;

import de.mancino.armory.datatypes.ArmoryBaseUri;
import de.mancino.armory.requests.GetRequest;

public class ArmoryApiRequest extends GetRequest {
    private final ArmoryBaseUri armoryBaseUri;
    
    public ArmoryApiRequest(final ArmoryBaseUri armoryBaseUri, final String requestPath) {
        super(armoryBaseUri.getApiUri() + requestPath);
        this.armoryBaseUri = armoryBaseUri;
    }
    
    protected ArmoryBaseUri getArmoryBaseUri() {
        return armoryBaseUri;
    }
}
