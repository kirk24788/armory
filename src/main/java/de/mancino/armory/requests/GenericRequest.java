package de.mancino.armory.requests;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.StatusLine;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mancino.armory.exceptions.RequestException;
import de.mancino.armory.exceptions.RequestIoException;
import de.mancino.armory.exceptions.ResponseParsingException;

public abstract class GenericRequest extends Request {
    /**
     * Logger instance of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(GenericRequest.class);

    /**
     * Execute POST Request.
     * 
     * @return HTTP Status Code
     * 
     * @throws RequestException Error during Request
     */
    @Override
    public int post() throws RequestException {
        final HttpPost httpPost = preparePostMethod();
        return executeRequest(httpPost);
    }

    protected abstract HttpPost preparePostMethod();

    /**
     * Execute GET Request.
     * 
     * @return HTTP Status Code
     * 
     * @throws RequestException Error during Request
     */
    @Override
    public int get() throws RequestException {
        final HttpGet httpGet = prepareGetMethod();
        return executeRequest(httpGet);
    }


    protected abstract HttpGet prepareGetMethod() throws RequestException;

    protected int executeRequest(final HttpUriRequest request) throws RequestException {
        LOG.info("Executing Request for URI: '{}'", request.getURI());
        for(final Header additionalHeader : getAdditionalHeaders()) {
            request.setHeader(additionalHeader);
        }
        final HttpResponse response;
        try {
            response = getHttpClient().execute(request);
        } catch (IOException e) {
            throw new RequestIoException("Couldn't execute Request for URI: " + request.getURI(), e);
        }
        final byte[] responseAsBytes;
        try {
            responseAsBytes = IOUtils.toByteArray(response.getEntity().getContent());
        } catch (IOException e) {
            final String msg = "Couldn't consume Response for URI: " + request.getURI() + 
                    " - HTTP Status Line: " + response.getStatusLine();
            LOG.error(msg);
            throw new RequestIoException(msg, e);
        }            
        LOG.debug("Request for URI: {} returned: {}", request.getURI(), response.getStatusLine());
        if(LOG.isTraceEnabled()) {
            LOG.trace(new String(responseAsBytes));
        }
        handleResponseStatus(request, response.getStatusLine());
        parseResponse(responseAsBytes);
        return response.getStatusLine().getStatusCode();
    }

    /**
     * Additional HTTP Headers to add to the requests.
     * Create new Headers with new BasicHeader(name, value).
     * 
     * @return List of additional Http Headers
     */
    protected Header[] getAdditionalHeaders() {
        return new Header[] { };
    }

    protected void parseResponse(final byte[] responseAsBytes) throws ResponseParsingException {
    }
    
    protected void handleResponseStatus(final HttpUriRequest request, final StatusLine statusLine) throws RequestException  {
        if (statusLine.getStatusCode() < 200) {
            throw new IllegalStateException("Received illegal Status from HTTP Client! " + statusLine);
        } else if (statusLine.getStatusCode() < 300) {
            return; // OGOG
        } else if (statusLine.getStatusCode() < 400) {
            throw new IllegalStateException("PostRedirectStrategy isn't working! Redirects shouldn't occur! "  + statusLine);
        } else {
            throw new RequestException("Received Unexpected '" + statusLine + "' Status Code! for URI: " + request.getURI());
        }
    }
}

