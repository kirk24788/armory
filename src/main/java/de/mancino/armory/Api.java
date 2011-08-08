package de.mancino.armory;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mancino.armory.datatypes.ArmoryBaseUri;
import de.mancino.armory.exceptions.RequestException;
import de.mancino.armory.json.api.auction.Auctions;
import de.mancino.armory.json.api.character.Character;
import de.mancino.armory.json.api.guild.Guild;
import de.mancino.armory.json.api.realm.RealmStatus;
import de.mancino.armory.requests.api.AuctionApiRequest;
import de.mancino.armory.requests.api.CharacterApiRequest;
import de.mancino.armory.requests.api.GuildApiRequest;
import de.mancino.armory.requests.api.RealmStatusApiRequest;

public class Api {
    /**
     * Logger instance of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Armory.class);
    
    private final ArmoryBaseUri armoryBaseUri;
    private final String defaultRealmName;

    private Map<String,AuctionApiRequest> auctionApiRequests = new HashMap<String, AuctionApiRequest>();

    Api(final ArmoryBaseUri armoryBaseUri, final String defaultRealmName) {
        this.armoryBaseUri = armoryBaseUri;
        this.defaultRealmName = defaultRealmName;
    }

    public Auctions getAuctions() throws RequestException {
        return getAuctions(defaultRealmName);
    }

    public synchronized Auctions getAuctions(final String realmName) throws RequestException {
        LOG.info("getAuctions(realmName={})", realmName);
        if(auctionApiRequests.get(realmName)==null) {
            auctionApiRequests.put(realmName, new AuctionApiRequest(armoryBaseUri, realmName));
        }
        final AuctionApiRequest request = auctionApiRequests.get(realmName);
        request.get();
        return request.getObject();
    }

    public Character getCharacterInfo(final String charName) throws RequestException {
        return getCharacterInfo(defaultRealmName, charName);
    }

    public Character getCharacterInfo(final String realmName, final String charName) throws RequestException {
        LOG.info("getCharacterInfo(realmName={}, charName={})", realmName, charName);
        final CharacterApiRequest request = new CharacterApiRequest(armoryBaseUri, realmName, charName, true);
        request.get();
        return request.getObject();
    }

    public Guild getGuildInfo(final String guildName) throws RequestException {
        return getGuildInfo(defaultRealmName, guildName);
    }

    public Guild getGuildInfo(final String realmName, final String guildName) throws RequestException {
        LOG.info("getGuildInfo(realmName={}, guildName={})", realmName, guildName);
        final GuildApiRequest request = new GuildApiRequest(armoryBaseUri, realmName, guildName, true);
        request.get();
        return request.getObject();
    }

    public RealmStatus getRealmStatus() throws RequestException {
        return getRealmStatus(defaultRealmName);
    }

    public RealmStatus getRealmStatus(final String realmName) throws RequestException {
        LOG.info("getRealmStatus(realmName={})", realmName);
        final RealmStatusApiRequest request = new RealmStatusApiRequest(armoryBaseUri, realmName);
        request.get();
        return request.getObject();
    }
}
