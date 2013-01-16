package de.mancino.armory.requests;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostRequest extends GenericRequest {
    /**
     * Logger instance of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(PostRequest.class);

    protected final String requestPath;
    private final BasicNameValuePair parameters[];

    public PostRequest(final String requestPath) {
        this.requestPath = requestPath;
        this.parameters = new BasicNameValuePair[] {};
    }
    
    public PostRequest(final String requestPath, final BasicNameValuePair ... parameters) {
        this.requestPath = requestPath;
        this.parameters = parameters;
    }

    @Override
    protected HttpPost preparePostMethod() {
        LOG.debug("Preparing POST Request for URI: " + requestPath);
        final HttpPost httpPost = new HttpPost(requestPath);
        if(getParameters().length > 0) {
            LOG.trace("Adding URL-Encoded Parameters:");
            final List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            for(final BasicNameValuePair parameter : getParameters()) {
                nvps.add(parameter);
                LOG.trace(" * {}: {}", parameter.getName(), parameter.getValue());
            }
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            } catch (final UnsupportedEncodingException e) {
                // If UTF-8 is unknown something is seriously wrong...
                throw new RuntimeException(e);
            }
        }
        httpPost.setHeader("User-Agent", USER_AGENT);
        return httpPost;
    }


    @Override
    protected HttpGet prepareGetMethod() {
        throw new NotImplementedException("GET not supported for POST Requests");
    }

    /**
     * @return the parameters
     */
    public BasicNameValuePair[] getParameters() {
        return parameters;
    }
}

