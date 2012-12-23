package de.mancino.armory.requests;

import org.apache.commons.lang.NotImplementedException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mancino.armory.exceptions.RequestException;


public class GetRequest extends GenericRequest {
    /**
     * Logger instance of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(PostRequest.class);

    protected final String requestPath;
    
    public GetRequest(final String requestPath) {
        this.requestPath = requestPath;
    }

    // XXX: conveniece-constructor for parameters?
    
    @Override
    protected HttpPost preparePostMethod() {
        throw new NotImplementedException("POST not supported for GET Requests");
    }


    @Override
    protected HttpGet prepareGetMethod() throws RequestException {
        LOG.debug("Preparing GET Request for URI: " + requestPath);
        final HttpGet httpGet = new HttpGet(requestPath);
        return httpGet;
    }
}