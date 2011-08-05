package de.mancino.armory.exceptions;

public class ResponseCodeException extends RequestException {
    private static final long serialVersionUID = 1L;

    public ResponseCodeException(final String msg) {
        super(msg);
    }
}
