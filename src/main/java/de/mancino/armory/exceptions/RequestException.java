package de.mancino.armory.exceptions;

public class RequestException extends Exception {
    private static final long serialVersionUID = 1L;

    public RequestException(final String msg) {
        super(msg);
    }

    public RequestException(final String msg, final Throwable cause) {
        super(msg, cause);
    }

}
