package de.mancino.armory.requests.api;

import de.mancino.armory.ArmoryBaseUri;
import de.mancino.armory.json.api.auction.AuctionFiles;

public class AuctionFilesApiRequest extends ArmoryApiJsonRequest<AuctionFiles> {

    public AuctionFilesApiRequest(final String realmName) {
        this(new ArmoryBaseUri(), realmName);
    }
    
    public AuctionFilesApiRequest(final ArmoryBaseUri armoryBaseUri, final String realmName) {
        super(armoryBaseUri, "auction/data/" + urlEncode(realmName), AuctionFiles.class);
    }
}
