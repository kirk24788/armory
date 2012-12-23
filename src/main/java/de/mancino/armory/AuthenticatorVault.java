package de.mancino.armory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mancino.armory.authenticator.Authenticator;
import de.mancino.armory.datatypes.ArmoryBaseUri;
import de.mancino.armory.exceptions.RequestException;
import de.mancino.armory.json.api.auction.Auction;
import de.mancino.armory.json.vault.AuctionFaction;
import de.mancino.armory.json.vault.bid.Bid;
import de.mancino.armory.json.vault.money.Money;
import de.mancino.armory.requests.RetryableRequest;
import de.mancino.armory.requests.api.GuildApiRequest;
import de.mancino.armory.requests.vault.AuctionBidVaultRequest;
import de.mancino.armory.requests.vault.LoginAuthenticatorRequest;
import de.mancino.armory.requests.vault.LoginVaultRequest;
import de.mancino.armory.requests.vault.MoneyVaultRequest;
import de.mancino.armory.requests.vault.SelectCharacterVaultRequest;

public class AuthenticatorVault extends Vault {
    private final String authenticatorSerial;
    private final String restorationCode;
    private Authenticator authenticator;

    public AuthenticatorVault(final ArmoryBaseUri armoryBaseUri, final String accountName, final String password, 
            final String charName, final String realmName,
            final String authenticatorSerial,
            final String restorationCode) {
        super(armoryBaseUri, accountName, password, charName, realmName);
        this.authenticatorSerial = authenticatorSerial;
        this.restorationCode = restorationCode;
    }

    /**
     * Logger instance of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticatorVault.class);

    protected void login() throws RequestException {
        LOG.info("login(accountName={} password={}***{})", new Object[] {accountName, password.charAt(0), 
                password.charAt(password.length()-1)});
        final LoginVaultRequest requestLogin = new LoginVaultRequest(armoryBaseUri, accountName, password);
        requestLogin.post();
        authenticator();
    }
    
    protected void authenticator() throws RequestException {
        final String token = getAuthenticatorToken();
        LOG.info("authenticator(authenticatorToken={})", new Object[] {token});
        final LoginAuthenticatorRequest requestAuthenticator = new LoginAuthenticatorRequest(armoryBaseUri, getAuthenticatorToken());
        requestAuthenticator.post();
    }

    private void initAuthenticator() throws RequestException {
        LOG.info("initAuthenticator()");
        try {
            Authenticator tempAuthenticator = new Authenticator();
            tempAuthenticator.net_enroll(authenticatorSerial.substring(0, 2));
            tempAuthenticator.restore(authenticatorSerial, restorationCode);
            tempAuthenticator.net_sync();
            this.authenticator = tempAuthenticator;
        } catch (Exception e) {
            LOG.error("Couldn't init Authenticator: " + e.getLocalizedMessage());
            throw new RequestException("Couldn't init Authenticator", e);
        }
    }
    
    private String getAuthenticatorToken() throws RequestException {
        if(authenticator == null) {
            initAuthenticator();
        }
        return authenticator.getAuthKey();
    }
}
