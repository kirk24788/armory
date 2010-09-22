/*
 * RssClient.java 21.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.rss;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.core.executors.ApacheHttpClientExecutor;
import org.jboss.resteasy.plugins.providers.FormUrlEncodedProvider;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RssClient {
    /**
     * Logger instance of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(RssClient.class);

    private final String serviceUrl;
    private final ClientExecutor clientExecutor;

    public RssClient(final String serviceUrl, final String username, final String password) {
        this.serviceUrl = serviceUrl;
        final Credentials credentials = new UsernamePasswordCredentials(username, password);
        HttpClient httpClient = new HttpClient();
        httpClient.getState().setCredentials(AuthScope.ANY, credentials);
        httpClient.getParams().setAuthenticationPreemptive(true);
        clientExecutor = new ApacheHttpClientExecutor(httpClient);

        ResteasyProviderFactory.getInstance().addBuiltInMessageBodyWriter(new FormUrlEncodedProvider());
    }

    public boolean createFeed(final String feedName, final String title, final String description) {
        ClientRequest request = new ClientRequest(serviceUrl + "/feeds/admin/{feed}.rss" , clientExecutor);
        MultivaluedMap<String,String> requestValues = new MultivaluedMapImpl<String, String>();
        requestValues.add("title", title);
        requestValues.add("description", description);
        request.pathParameter("feed", feedName).body( MediaType.APPLICATION_FORM_URLENCODED, requestValues);

        ClientResponse<String> response;
        try {
            LOG.debug("PUT: " + request.getUri());
            response = request.put(String.class);
            final int statusCode = response.getResponseStatus().getStatusCode();
            LOG.debug("Response: " + statusCode);
            LOG.debug(response.getEntity());
            return statusCode == 204;
        } catch (Exception e) {
            LOG.error("Fehler!", e);
            return false;
        }
    }

    public boolean deleteFeed(final String feedName) {
        ClientRequest request = new ClientRequest(serviceUrl + "/feeds/admin/{feed}.rss" , clientExecutor);
        request.pathParameter("feed", feedName);

        ClientResponse<String> response;
        try {
            LOG.debug("DELETE: " + request.getUri());
            response = request.delete(String.class);
            final int statusCode = response.getResponseStatus().getStatusCode();
            LOG.debug("Response: " + statusCode);
            LOG.debug(response.getEntity());
            return statusCode == 204;
        } catch (Exception e) {
            LOG.error("Fehler!", e);
            return false;
        }
    }

    public boolean addEntry(final String feedName, final String title, final String description) {
        ClientRequest request = new ClientRequest(serviceUrl + "/feeds/admin/{feed}.rss/add" , clientExecutor);
        MultivaluedMap<String,String> requestValues = new MultivaluedMapImpl<String, String>();
        requestValues.add("title", title);
        requestValues.add("description", description);
        request.pathParameter("feed", feedName).body( MediaType.APPLICATION_FORM_URLENCODED, requestValues);

        ClientResponse<String> response;
        try {
            LOG.debug("PUT: " + request.getUri());
            response = request.put(String.class);
            final int statusCode = response.getResponseStatus().getStatusCode();
            LOG.debug("Response: " + statusCode);
            LOG.debug(response.getEntity());
            return statusCode == 204;
        } catch (Exception e) {
            LOG.error("Fehler!", e);
            return false;
        }
    }
}
