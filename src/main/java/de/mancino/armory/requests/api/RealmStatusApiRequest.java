package de.mancino.armory.requests.api;

import de.mancino.armory.datatypes.ArmoryBaseUri;
import de.mancino.armory.json.api.realm.RealmStatus;

public class RealmStatusApiRequest extends ArmoryApiJsonRequest<RealmStatus> {
    public RealmStatusApiRequest(final ArmoryBaseUri armoryBaseUri, final String ... realms) {
        super(armoryBaseUri, buildRequestPath(realms), RealmStatus.class);
    }

    private static String buildRequestPath(final String ... realms) {
        String path = "realm/status";
        if(realms.length > 0) {
            path += "?realms=" + realms[0];
            for(int pos = 1; pos < realms.length ; pos++) {
                path+=","+realms[pos];
            }
        }
        return path;
    }

}
