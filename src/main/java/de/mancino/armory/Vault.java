package de.mancino.armory;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mancino.armory.datatypes.ArmoryBaseUri;
import de.mancino.armory.exceptions.RequestException;
import de.mancino.armory.json.api.auction.Auction;
import de.mancino.armory.json.vault.AuctionFaction;
import de.mancino.armory.json.vault.bid.Bid;
import de.mancino.armory.json.vault.money.Money;
import de.mancino.armory.requests.RetryableRequest;
import de.mancino.armory.requests.vault.AuctionBidVaultRequest;
import de.mancino.armory.requests.vault.LoginVaultRequest;
import de.mancino.armory.requests.vault.MoneyVaultRequest;
import de.mancino.armory.requests.vault.SelectCharacterVaultRequest;

public class Vault implements Serializable {
    private static final long serialVersionUID = 2L;

    protected final ArmoryBaseUri armoryBaseUri;
    protected final String accountName;
    protected final String password;
    protected String charName;
    protected String realmName;
    protected AuctionFaction faction;

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

    protected void login() throws RequestException {
        LOG.info("login(accountName={} password={}***{})", new Object[] {accountName, password.charAt(0),
                password.charAt(password.length()-1)});
        final LoginVaultRequest request = new LoginVaultRequest(armoryBaseUri, accountName, password);
        request.post();
    }

    public void changeActiveChar(final String charName, final AuctionFaction faction, final String realmName) throws RequestException {
        this.charName = charName;
        this.realmName = realmName;
        this.faction = faction;
        selectActiveChar();
    }

    protected void selectActiveChar() throws RequestException {
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

    protected void verifyActiveChar() throws RequestException {
        final Money money = getMoney();
        if(!money.character.name.equals(charName) || money.character.realmName.equals(realmName)) {
            selectActiveChar();
        }
    }

    public Money getMoney() throws RequestException {
        LOG.info("getMoney()");
        final RetryableRequest<MoneyVaultRequest> request =
                new RetryableRequest<MoneyVaultRequest>(new MoneyVaultRequest(armoryBaseUri, faction)) {
            @Override
            protected void errorCleanup() throws Throwable {
                login();
            }
        };
        request.post();
        return request.getRequest().getObject();
    }


    public Bid bid(final AuctionFaction faction, final Auction auction, final long bid) throws RequestException {
        return bid(faction, auction.auc, bid);
    }

    public Bid buyout(final AuctionFaction faction, final Auction auction) throws RequestException {
        return bid(faction ,auction.auc, auction.buyout);
    }

    public Bid bid(final AuctionFaction faction, final long auctionId, final long bid) throws RequestException {
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
