package de.mancino.armory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mancino.armory.datatypes.ArmoryBaseUri;
import de.mancino.armory.datatypes.ArmoryLanguage;
import de.mancino.armory.datatypes.ArmoryRegion;

/**
 * Class for controlling Blizzard's WoW-Armory Interface.
 * The <code>Armory</code> class represents rougly Blizzards
 * WoW Armory Web-Interface.
 *
 * The Armory can be used with oder without login, although it is
 * recommended to always use a login.
 * While the Armory will work without login, most functions won't
 * work as expected / throw errors.
 *
 * @author mmancino
 */
public class AuthenticatorArmory extends Armory {
    private static final long serialVersionUID = 4L;
    /**
     * Logger instance of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticatorArmory.class);
    
    public AuthenticatorArmory(final String accountName, 
            final String password,
            final String charName, 
            final String realmName,
            final String authenticatorSerial,
            final String restorationCode) {
        this(new ArmoryBaseUri(), accountName, password, charName, realmName, authenticatorSerial, restorationCode);
    }
    
    public AuthenticatorArmory(final ArmoryRegion region,
            final ArmoryLanguage language,
            final String accountName, 
            final String password,
            final String charName, 
            final String realmName,
            final String authenticatorSerial,
            final String restorationCode) {
        this(new ArmoryBaseUri(region, language), accountName, password, charName, realmName, authenticatorSerial, restorationCode);
    }

    public AuthenticatorArmory(final ArmoryBaseUri armoryBaseUri,
            final String accountName, 
            final String password,
            final String charName, 
            final String realmName,
            final String authenticatorSerial,
            final String restorationCode) {
        super(new Api(armoryBaseUri, realmName), 
                new AuthenticatorVault(armoryBaseUri, accountName, password, charName, realmName, authenticatorSerial, restorationCode));
        LOG.info("Initialized Armory...(baseUri={} accountName={} password={}***{} charName={} realmName={} authenticatorSerial={} restorationCode={}***{})",
                new Object[] {armoryBaseUri.getBaseUri(), accountName, password.charAt(0), 
                password.charAt(password.length()-1), charName, realmName, authenticatorSerial,
                restorationCode.charAt(0),  restorationCode.charAt(restorationCode.length()-1)});
    }
}
