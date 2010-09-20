/*
 * PostRedirectHandler.java 20.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;


public class PostRedirectHandler extends DefaultRedirectHandler {
    @Override
    public boolean isRedirectRequested(
            final HttpResponse response,
            final HttpContext context) {
        if (response == null) {
            throw new IllegalArgumentException("HTTP response may not be null");
        }

        int statusCode = response.getStatusLine().getStatusCode();
        switch (statusCode) {
        case HttpStatus.SC_MOVED_TEMPORARILY:
        case HttpStatus.SC_MOVED_PERMANENTLY:
        case HttpStatus.SC_TEMPORARY_REDIRECT:
            HttpRequest request = (HttpRequest) context.getAttribute(
                    ExecutionContext.HTTP_REQUEST);
            String method = request.getRequestLine().getMethod();
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
