package de.mancino.armory.requests;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.DeserializationProblemHandler;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
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
    protected static DefaultHttpClient getHttpClient() {
        return Request.HTTP_CLIENT;
    }
    
    protected static String getCookieValue(final String cookieName) {
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

    
    protected ObjectMapper createJsonObjectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.getDeserializationConfig().addHandler(new DeserializationProblemHandler() {
            @Override
            public boolean handleUnknownProperty(DeserializationContext ctxt, JsonDeserializer<?> deserializer,
                    Object beanOrClass, String propertyName) throws IOException, JsonProcessingException {
                LOG.info("Unknown Property '{}' in Class '{}'!",  propertyName , beanOrClass.getClass().getCanonicalName());
                ctxt.getParser().skipChildren();
                return true;
            }
        });
     //   mapper.enableDefaultTyping(); // default to using DefaultTyping.OBJECT_AND_NON_CONCRETE
    //    mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        return mapper;
    }
    
    public static String urlEncode(final String s) {
        try {
            return urlEncode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // once again - ignore utf-8 encoding exceptions
            throw new RuntimeException(e);
        }
    }
    
    public static String urlEncode(final String s, final String enc) throws UnsupportedEncodingException {
        return URLEncoder.encode(s, enc);
    }
    
}

