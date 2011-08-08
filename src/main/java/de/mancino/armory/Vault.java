package de.mancino.armory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mancino.armory.datatypes.ArmoryBaseUri;
import de.mancino.armory.exceptions.RequestException;
import de.mancino.armory.json.api.auction.Auction;
import de.mancino.armory.json.vault.AuctionFaction;
import de.mancino.armory.json.vault.bid.Bid;
import de.mancino.armory.json.vault.money.Money;
import de.mancino.armory.requests.RetryableRequest;
import de.mancino.armory.requests.api.GuildApiRequest;
import de.mancino.armory.requests.vault.AuctionBidVaultRequest;
import de.mancino.armory.requests.vault.LoginVaultRequest;
import de.mancino.armory.requests.vault.MoneyVaultRequest;
import de.mancino.armory.requests.vault.SelectCharacterVaultRequest;

public class Vault {
    private final ArmoryBaseUri armoryBaseUri; 
    private final String accountName; 
    private final String password; 
    private final String charName; 
    private final String realmName; 

    public Vault(final ArmoryBaseUri armoryBaseUri, final String accountName, final String password, 
            final String charName, final String realmName) {
        this.armoryBaseUri = armoryBaseUri;
        this.accountName = accountName;
        this.password = password;
        this.charName = charName;
        this.realmName = realmName;
    }

    /**
     * Logger instance of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Vault.class);

    private void login() throws RequestException {
        LOG.info("login(accountName={} password={}***{})", new Object[] {accountName, password.charAt(0), 
                password.charAt(password.length()-1)});
        final LoginVaultRequest request = new LoginVaultRequest(armoryBaseUri, accountName, password);
        request.post();
    }

    private void selectActiveChar() throws RequestException {
        LOG.info("selectActiveChar(realmName={}, charName={})", realmName, charName);
        final RetryableRequest<SelectCharacterVaultRequest> request = 
                new RetryableRequest<SelectCharacterVaultRequest>(
                        new SelectCharacterVaultRequest(armoryBaseUri, realmName, charName)) {
            @Override
            protected void errorCleanup() throws Throwable {
                login();
            }
        };
        request.post();
    }
    
    private void verifyActiveChar() throws RequestException {
        final Money money = getMoney();
        if(!money.character.name.equals(charName) || money.character.realmName.equals(realmName)) {
            selectActiveChar();
        }
    }

    public Money getMoney() throws RequestException {
        LOG.info("getMoney()");
        final RetryableRequest<MoneyVaultRequest> request = 
                new RetryableRequest<MoneyVaultRequest>(new MoneyVaultRequest(armoryBaseUri)) {
            @Override
            protected void errorCleanup() throws Throwable {
                login();
            }
        };
        request.post();
        return request.getRequest().getObject();
    }


    public Bid bid(AuctionFaction faction, Auction auction, long bid) throws RequestException {
        return bid(faction, auction.auc, bid);
    }
    
    public Bid buyout(AuctionFaction faction, Auction auction) throws RequestException {
        return bid(faction ,auction.auc, auction.buyout);
    }

    public Bid bid(AuctionFaction faction, long auctionId, long bid) throws RequestException {
        verifyActiveChar();
        final RetryableRequest<AuctionBidVaultRequest> request = 
                new RetryableRequest<AuctionBidVaultRequest>(new AuctionBidVaultRequest(armoryBaseUri, faction, auctionId, bid)) {
            @Override
            protected void errorCleanup() throws Throwable {
                login();
            }
        };
        request.post();
        return request.getRequest().getObject();
    }
}
