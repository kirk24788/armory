package de.mancino.exceptions;

/**
 * Error connecting to armory.
 *
 * @author mmancino
 */
public class ArmoryConnectionException extends Exception {
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Error connecting to armory.
     *
     * @param e cause
     */
    public ArmoryConnectionException(Exception e) {
        super(e);
    }

    /**
     * Error connecting to armory.
     *
     * @param msg message
     * @param e cause
     */
    public ArmoryConnectionException(String msg, Exception e) {
        super(msg, e);
    }

    /**
     * Error connecting to armory.
     *
     * @param msg message
     */
    public ArmoryConnectionException(String msg) {
        super(msg);
    }

}
