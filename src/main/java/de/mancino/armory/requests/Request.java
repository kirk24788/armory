package de.mancino.armory.requests;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.DeserializationProblemHandler;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mancino.utils.PostRedirectStrategy;

public abstract class Request implements IRequest {
    /**
     * Logger instance of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Request.class);

    /**
     * HTTP Client used for ALL requests
     */
    private static final DefaultHttpClient HTTP_CLIENT = createHttpClient();
    
    public static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/536.26.17 (KHTML, like Gecko) Version/6.0.2 Safari/536.26.17";

    public static final int DEFAULT_CONNECTION_TIMEOUT = 5000;
    public static final int DEFAULT_SOCKET_TIMEOUT = 5000;

    private static DefaultHttpClient createHttpClient() {
        ClientConnectionManager cm = new ThreadSafeClientConnManager();
        final HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, DEFAULT_CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams, DEFAULT_SOCKET_TIMEOUT);
        final DefaultHttpClient defaultHttpClient = new DefaultHttpClient(cm, httpParams);
        defaultHttpClient.setRedirectStrategy(new PostRedirectStrategy());
        return defaultHttpClient;
    }

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

