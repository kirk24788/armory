package de.mancino.armory.requests.api;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import de.mancino.armory.datatypes.ArmoryBaseUri;
import de.mancino.armory.exceptions.RequestException;
import de.mancino.armory.json.api.auction.AuctionFile;
import de.mancino.armory.json.api.auction.AuctionFiles;
import de.mancino.armory.json.api.auction.Auctions;

public class AuctionApiRequest extends ArmoryApiJsonRequest<Auctions> {

    private final String realmName;
    private final boolean forceReload;
    private static long lastFetchTimestamp = 0;
    private long nextFetchTimestamp = 0;
    private static Auctions lastAuctions = new Auctions();
    
    public AuctionApiRequest(final String realmName) {
        this(new ArmoryBaseUri(), realmName, false);
    }
    public AuctionApiRequest(final String realmName, boolean forceReload) {
        this(new ArmoryBaseUri(), realmName, forceReload);
    }

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
        final AuctionFilesApiRequest filesRequest = new AuctionFilesApiRequest(realmName);
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
            return returnCode;
        } else {
            return 200;
        }
    }
    
    @Override
    public Auctions getObject() {
        return lastAuctions;
    }
    
    public long getLastFetchTimestamp() {
        return lastFetchTimestamp;
    }
}
