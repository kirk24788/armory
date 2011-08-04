package de.mancino.utils;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;


/**
 * POST Redirect Handler which follows redirected POST-Requests even
 * if the Redirect-Method is GET.
 *
 * @author mmancino
 */
public class PostRedirectStrategy extends DefaultRedirectStrategy {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRedirected(
            final HttpRequest request,
            final HttpResponse response,
            final HttpContext context) throws ProtocolException {
        if (response == null) {
            throw new IllegalArgumentException("HTTP response may not be null");
        }

        int statusCode = response.getStatusLine().getStatusCode();
        String method = request.getRequestLine().getMethod();
        Header locationHeader = response.getFirstHeader("location");
        switch (statusCode) {
        case HttpStatus.SC_MOVED_TEMPORARILY:
            return (method.equalsIgnoreCase(HttpGet.METHOD_NAME)
                || method.equalsIgnoreCase(HttpPost.METHOD_NAME)
                || method.equalsIgnoreCase(HttpHead.METHOD_NAME)) && locationHeader != null;
        case HttpStatus.SC_MOVED_PERMANENTLY:
        case HttpStatus.SC_TEMPORARY_REDIRECT:
            return method.equalsIgnoreCase(HttpGet.METHOD_NAME)
                || method.equalsIgnoreCase(HttpHead.METHOD_NAME)
                || method.equalsIgnoreCase(HttpPost.METHOD_NAME);
        case HttpStatus.SC_SEE_OTHER:
            return true;
        default:
            return false;
        } //end of switch
    }
}
