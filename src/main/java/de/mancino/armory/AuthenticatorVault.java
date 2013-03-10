package de.mancino.armory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mancino.armory.authenticator.Authenticator;
import de.mancino.armory.datatypes.ArmoryBaseUri;
import de.mancino.armory.exceptions.RequestException;
import de.mancino.armory.requests.vault.LoginAuthenticatorRequest;
import de.mancino.armory.requests.vault.LoginVaultRequest;

public class AuthenticatorVault extends Vault {
    private static final long serialVersionUID = 3L;
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

    @Override
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

    public String getAuthenticatorToken() throws RequestException {
        if(authenticator == null) {
            initAuthenticator();
        }
        return authenticator.getAuthKey();
    }
}
