package de.mancino.armory.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mancino.armory.exceptions.RequestException;

public class RetryableRequest<T extends Request> extends Request {
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
     * Number of retries for this instance
     */
    private final T request;

    /**
     * Executes the given request up to {@link #DEFAULT_MAX_REQUEST_RETRIES}
     * times. If an exception occurs the {@link #errorCleanup()} method is
     * called - default implementation is empty, override if necessary.
     * 
     * @param request Request which should be retried
     */
    public RetryableRequest(final T request) {
        this.request = request;
        this.maxRetries = DEFAULT_MAX_REQUEST_RETRIES;
    }

    /**
     * Executes the given request up to given number of
     * times. If an exception occurs the {@link #errorCleanup()} method is
     * called - default implementation is empty, override if necessary.
     *
     * @param request Request which should be retried
     * @param maxRetries maximum number of retries per request
     */
    public RetryableRequest(final T request, final int maxRetries) {
        this.request = request;
        this.maxRetries = maxRetries;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int get() throws RequestException {
        RequestException lastException = null;
        for(int currentTry=1; currentTry<=maxRetries ; currentTry++) {
            try {
                return getRequest().get();
            } catch (RequestException requestException) {
                LOG.error("Error during Request " + currentTry + ": " + requestException.getLocalizedMessage());
                LOG.debug("Stacktrace: ", requestException);
                lastException = requestException;
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
     * {@inheritDoc}
     */
    @Override
    public int post() throws RequestException {
        RequestException lastException = null;
        for(int currentTry=1; currentTry<=maxRetries ; currentTry++) {
            try {
                return getRequest().post();
            } catch (RequestException requestException) {
                LOG.error("Error during Request " + currentTry + ": " + requestException.getLocalizedMessage());
                LOG.debug("Stacktrace: ", requestException);
                lastException = requestException;
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
     * Error-Cleanup, which is called if an exception occurs during the request.
     * Default implementation is empty, override if necessary.
     *
     * @throws Throwable Error while cleaning up erroneous request.
     */
    protected void errorCleanup() throws Throwable {
    }

    /**
     * Returns the request.
     * 
     * @return the request
     */
    public T getRequest() {
        return request;
    }
}
