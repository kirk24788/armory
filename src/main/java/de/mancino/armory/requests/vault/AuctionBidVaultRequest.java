package de.mancino.armory.requests.vault;

import org.apache.http.message.BasicNameValuePair;

import de.mancino.armory.ArmoryBaseUri;
import de.mancino.armory.exceptions.ResponseParsingException;
import de.mancino.armory.json.vault.AuctionFaction;

public class AuctionBidVaultRequest extends ArmoryVaultRequest {

    public AuctionBidVaultRequest(final AuctionFaction faction,  final long auctionId, final long bid) {
        this(new ArmoryBaseUri(), faction, auctionId, bid);
    }
    public AuctionBidVaultRequest(final ArmoryBaseUri armoryBaseUri, final AuctionFaction faction,  final long auctionId, final long bid) {
        super(armoryBaseUri, "vault/character/auction/" + faction.key + "/bid", new BasicNameValuePair[] {
                new BasicNameValuePair("auc",  String.valueOf(auctionId)),
                new BasicNameValuePair("money",  String.valueOf(bid)),
                new BasicNameValuePair("xstoken", getCookieValue("xstoken"))});
    }

    @Override
    protected void parseResponse(byte[] responseAsBytes) throws ResponseParsingException {
        System.err.println(new String(responseAsBytes));
    }
}
