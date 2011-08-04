package de.mancino.armory.requests;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mancino.armory.exceptions.RequestException;
import de.mancino.utils.PostRedirectStrategy;

public abstract class Request {
    /**
     * Logger instance of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Request.class);
    
    /**
     * HTTP Client used for ALL requests
     */
    private static final DefaultHttpClient HTTP_CLIENT = createHttpClient();

    private static DefaultHttpClient createHttpClient() {
        ClientConnectionManager cm = new ThreadSafeClientConnManager();
        final DefaultHttpClient defaultHttpClient = new DefaultHttpClient(cm);
        defaultHttpClient.setRedirectStrategy(new PostRedirectStrategy());
        return defaultHttpClient;
    }

    /**
     * Execute POST Request.
     * 
     * @return HTTP Status Code
     * 
     * @throws RequestException Error during POST Request.
     */
    public abstract int post() throws RequestException;

    /**
     * Execute GET Request.
     * 
     * @return HTTP Status Code
     * 
     * @throws RequestException Error during GET Request.
     */
    public abstract int get() throws RequestException;

    /**
     * Get the HTTP Client for all Requests.
     * 
     * @return HTTP Client
     */
    protected DefaultHttpClient getHttpClient() {
        return Request.HTTP_CLIENT;
    }
    
    protected String getCookieValue(final String cookieName) {
        LOG.debug("Searching for '{}' cookie", cookieName);
        for(final Cookie cookie : getHttpClient().getCookieStore().getCookies()) {
            LOG.trace(" {}: {}", cookie.getName(), cookie.getValue());
            if(cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        LOG.warn("Cookie '{}' not found!", cookieName);
        return "";
    }
}

