package de.mancino.armory.exceptions;

public class RequestIoException extends RequestException {
    private static final long serialVersionUID = 1L;

    public RequestIoException(final String msg, final Throwable cause) {
        super(msg, cause);
    }
}
