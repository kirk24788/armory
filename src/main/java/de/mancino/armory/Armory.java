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
public class Armory {
    /**
     * Logger instance of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Armory.class);

    public final Api api;
    
    public final Vault vault;

    
    public Armory(final String accountName, 
            final String password,
            final String charName, 
            final String realmName) {
        this(new ArmoryBaseUri(), accountName, password, charName, realmName);
    }
    
    public Armory(final ArmoryRegion region,
            final ArmoryLanguage language,
            final String accountName, 
            final String password,
            final String charName, 
            final String realmName) {
        this(new ArmoryBaseUri(region, language), accountName, password, charName, realmName);
    }

    public Armory(final ArmoryBaseUri armoryBaseUri,
            final String accountName, 
            final String password,
            final String charName, 
            final String realmName) {
        LOG.info("Initializing Armory...(baseUri={} accountName={} password={}***{} charName={} realmName={})",
                new Object[] {armoryBaseUri.getBaseUri(), accountName, password.charAt(0), 
                password.charAt(password.length()-1), charName, realmName});
        api = new Api(armoryBaseUri, realmName);
        vault = new Vault(armoryBaseUri, accountName, password, charName, realmName);
    }
}
