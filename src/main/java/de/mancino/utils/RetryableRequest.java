package de.mancino.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Executes the given request in {@link #request()} up to given number of
 * times. If an exception occurs the {@link #errorCleanup()} method is
 * called - default implementation is empty, override if necessary.
 *
 * @param T Request response type
 *
 * @author mmancino
 */
public abstract class RetryableRequest<T> {
    /**
     * Logger instance of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(RetryableRequest.class);

    /**
     * Default number of retries if none is set.
     */
    public final static int DEFAULT_MAX_REQUEST_RETRIES = 3;

    /**
     * Number of retries for this instance
     */
    private final int maxRetries;

    /**
     * Executes the given request in {@link #request()} up to {@link #DEFAULT_MAX_REQUEST_RETRIES}
     * times. If an exception occurs the {@link #errorCleanup()} method is
     * called - default implementation is empty, override if necessary.
     */
    public RetryableRequest() {
        this.maxRetries = DEFAULT_MAX_REQUEST_RETRIES;
    }

    /**
     * Executes the given request in {@link #request()} up to given number of
     * times. If an exception occurs the {@link #errorCleanup()} method is
     * called - default implementation is empty, override if necessary.
     *
     * @param maxRetries maximum number of retries per request
     */
    public RetryableRequest(final int maxRetries) {
        this.maxRetries = maxRetries;
    }


    /**
     * Start the requests and retry on errors.
     *
     * @return Request-Result
     *
     * @throws Throwable After the given number of retries the error persisted.
     */
    public T requestWithRetries() throws Throwable {
        // TODO: Eigene Exception als Sammler von den einzelnen Exceptions?
        Throwable lastException = null;
        for(int currentTry=1; currentTry<=maxRetries ; currentTry++) {
            try {
                return request();
            } catch (Throwable queryException) {
                LOG.error("Error during Request " + currentTry + ": " + queryException.getLocalizedMessage());
                LOG.debug("Stacktrace: ", queryException);
                lastException = queryException;
                try {
                    errorCleanup();
                } catch (Throwable cleanupException) {
                    LOG.error("Error during Cleanup for Request " + currentTry + ": " + cleanupException.getLocalizedMessage());
                    LOG.debug("Stacktrace: ", cleanupException);
                }
            }
        }
        throw lastException;
    }

    /**
     * The actual request.
     *
     * @return Request-Result
     *
     * @throws Throwable Error while executing request.
     */
    protected abstract T request() throws Throwable;

    /**
     * Error-Cleanup, which is called if an exception occurs during the request.
     * Default implementation is empty, override if necessary.
     *
     * @throws Throwable Error while cleaning up erroneous request.
     */
    protected void errorCleanup() throws Throwable {
    }
}
