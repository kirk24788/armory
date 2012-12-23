package de.mancino.armory.exceptions;

public class ResponseParsingException extends RequestException {
    private static final long serialVersionUID = 1L;

    public ResponseParsingException(final String msg) {
        super(msg);
    }
    
    public ResponseParsingException(final String msg, final Throwable cause) {
        super(msg, cause);
    }
}
