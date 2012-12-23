package de.mancino.armory.requests.api;

import java.io.Serializable;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import de.mancino.armory.datatypes.ArmoryBaseUri;
import de.mancino.armory.exceptions.RequestException;
import de.mancino.armory.json.api.auction.AuctionFile;
import de.mancino.armory.json.api.auction.Auctions;
import de.mancino.armory.requests.ICachableRequest;

public class AuctionApiRequest extends ArmoryApiJsonRequest<Auctions> implements Serializable, ICachableRequest<Auctions> {
    private static final long serialVersionUID = 2L;

    private final String realmName;
    private final boolean forceReload;
    private long lastFetchTimestamp = 0;
    private long nextFetchTimestamp = 0;
    private Auctions lastAuctions = new Auctions();

    public AuctionApiRequest(final ArmoryBaseUri armoryBaseUri, final String realmName) {
        this(armoryBaseUri, realmName, false);
    }

    public AuctionApiRequest(final ArmoryBaseUri armoryBaseUri, final String realmName, final boolean forceReload) {
        super(armoryBaseUri, "", Auctions.class);
        this.realmName = realmName;
        this.forceReload = forceReload;
    }

    @Override
    protected HttpGet prepareGetMethod() throws RequestException {
        final AuctionFilesApiRequest filesRequest = new AuctionFilesApiRequest(getArmoryBaseUri(),realmName);
        filesRequest.get();
        if(filesRequest.getObject().files == null | filesRequest.getObject().files.size()==0) {
            throw new RequestException("No auction Files for Realm '" + realmName +"'");
        }
        AuctionFile oldestFile = filesRequest.getObject().files.get(0);
        for(final AuctionFile auctionFile : filesRequest.getObject().files) {
            if(auctionFile.lastModified > oldestFile.lastModified) {
                oldestFile = auctionFile;
            }
        }
        nextFetchTimestamp = oldestFile.lastModified;
        return new HttpGet(oldestFile.url);
    }

    @Override
    protected int executeRequest(final HttpUriRequest request) throws RequestException {
        if(forceReload || lastFetchTimestamp<nextFetchTimestamp) {
            final int returnCode = super.executeRequest(request);
            lastFetchTimestamp = nextFetchTimestamp;
            lastAuctions = super.getObject();
            lastAuctions.timestamp = lastFetchTimestamp;
            return returnCode;
        } else {
            return 200;
        }
    }

    @Override
    public Auctions getObject() {
        return lastAuctions;
    }

    @Override
    public long getObjectTimestamp() {
        return lastFetchTimestamp;
    }
}
