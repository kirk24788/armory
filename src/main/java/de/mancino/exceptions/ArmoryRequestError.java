package de.mancino.exceptions;

import de.mancino.armory.xml.ArmoryError;

/**
 * Armory Request Error.
 * Armory did respond correctly, but an error-message was given
 *
 * @author mmancino
 */
public class ArmoryRequestError extends ArmoryConnectionException {
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Armory Request Error.
     * Armory did respond correctly, but an error-message was given
     *
     * @param error Armory Error
     */
    public ArmoryRequestError(ArmoryError error) {
        super("Error " + error.code + ": " + error.message);
    }
}
