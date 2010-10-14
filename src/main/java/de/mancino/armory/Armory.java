/*
 * Armory.java 20.08.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.NotImplementedException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;
import org.jboss.resteasy.plugins.providers.DataSourceProvider;
import org.jboss.resteasy.plugins.providers.FormUrlEncodedProvider;
import org.jboss.resteasy.plugins.providers.StringTextStar;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mancino.armory.xml.Page;
import de.mancino.armory.xml.armorysearch.ArmorySearch;
import de.mancino.armory.xml.auctionsearch.AuctionItem;
import de.mancino.armory.xml.auctionsearch.AuctionSearch;
import de.mancino.armory.xml.characterInfo.CharacterInfo;
import de.mancino.armory.xml.enums.Quality;
import de.mancino.armory.xml.itemtooltips.ItemTooltip;
import de.mancino.exceptions.ArmoryConnectionException;
import de.mancino.exceptions.ArmoryRequestError;
import de.mancino.utils.EncodingUtils;

public class Armory {
    private static final int AUCTION_PAGE_SIZE = 40;

    /**
     * Logger instance of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Armory.class);

    private static final String REGION = "eu";
    private static final String ARMORY_BASE_URL = "http://" + REGION + ".wowarmory.com/";
    private static final String BATTLENET_BASE_URL = "https://" + REGION + ".battle.net/";
    private static final int MAX_REQUEST_RETRIES = 3;
    private final String accountName;
    private final String password;
    private String primaryCharname;
    private String primaryRealm;



    private DefaultHttpClient globalHttpClient;

    public Armory() {
        globalHttpClient = new DefaultHttpClient();
        primaryCharname = "";
        primaryRealm = "";
        accountName = "";
        password = "";
        ResteasyProviderFactory.getInstance().addBuiltInMessageBodyReader(new StringTextStar());
        ResteasyProviderFactory.getInstance().addBuiltInMessageBodyReader(new DataSourceProvider());
        ResteasyProviderFactory.getInstance().addBuiltInMessageBodyReader(new FormUrlEncodedProvider());
    }

    public Armory(final String accountName, final String password,
            final String primaryCharname, final String primaryRealm) throws ArmoryConnectionException {
        /*
        ResteasyProviderFactory providerFactory = ResteasyProviderFactory.getInstance();
        RegisterBuiltin.register(providerFactory);*/

        ResteasyProviderFactory.getInstance().addBuiltInMessageBodyReader(new StringTextStar());
        ResteasyProviderFactory.getInstance().addBuiltInMessageBodyReader(new DataSourceProvider());
        ResteasyProviderFactory.getInstance().addBuiltInMessageBodyReader(new FormUrlEncodedProvider());
        /*
         * org.jboss.resteasy.plugins.providers.
        ResteasyProviderFactory.getInstance().addBuiltInMessageBodyReader(new StringTextStar());
        ResteasyProviderFactory.getInstance().addBuiltInMessageBodyReader(new DataSourceProvider());*/
        this.primaryCharname = primaryCharname;
        this.primaryRealm = primaryRealm;
        this.accountName = accountName;
        this.password = password;
        login();
        selectPrimaryCharacter(primaryCharname, primaryRealm);
    }

    public void relog() throws ArmoryConnectionException {
        login();
        selectPrimaryCharacter(primaryCharname, primaryRealm);
    }

    private void login() throws ArmoryConnectionException {
        globalHttpClient = new DefaultHttpClient();
        //httpClient. getParams().setCookiePolicy(CookiePolicy.RFC_2109);
        final String armoryUrl = BATTLENET_BASE_URL + "login/en/login.xml?app=armory&ref=http%3A%2F%2Feu.wowarmory.com%2Findex.xml&cr=true";
        globalHttpClient.setRedirectHandler(new PostRedirectHandler());
        if(LOG.isDebugEnabled()) {
            LOG.debug("Logging in to Armory ("+accountName +":" + password.charAt(0) + "***"
                    + password.charAt(password.length()-1) + "): " + armoryUrl);
        }
        final HttpPost postLogin = new HttpPost(armoryUrl);

        final List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair("accountName", accountName));
        nvps.add(new BasicNameValuePair("password", password));
        nvps.add(new BasicNameValuePair("persistLogin", "on"));

        try {
            postLogin.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            final HttpResponse postResponse = globalHttpClient.execute(postLogin);
            postResponse.getEntity().consumeContent();
            LOG.debug("Response: " + postResponse.getStatusLine());
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (final IOException e) {
            throw new ArmoryConnectionException("Login failed!", e);
        } catch (final IndexOutOfBoundsException e) {
            throw new ArmoryConnectionException("Login failed! Missing Location Header!", e);
        }
    }

    public CharacterInfo getCharacterSheet(final String charName, final String realmName) throws ArmoryConnectionException {
        return executeRestQuery("character-sheet.xml?r=" + realmName + "&n=" + EncodingUtils.urlEncode(charName, "UTF-8")).characterInfo;
    }

    public ItemTooltip getItemInfo(final long itemId) throws ArmoryConnectionException {
        return executeRestQuery("item-tooltip.xml?i=" + itemId).itemTooltips.get(0);
    }

    public ArmorySearch searchArmory(final String searchTerm) throws ArmoryConnectionException {
        return searchArmory(searchTerm, "all");
    }

    public void buyAuction(final AuctionItem item) throws ArmoryConnectionException {
        LOG.info("Buying Auction {} ({})", item.auctionId, item.name);
        executeJsonPost("auctionhouse/bid.json",
                new BasicNameValuePair("auc", String.valueOf(item.auctionId)),
                new BasicNameValuePair("money", String.valueOf(item.buy)),
                new BasicNameValuePair("sk", getSkValue()));
    }

    protected String getSkValue() throws ArmoryConnectionException {
        try {
            final HttpGet summaryGet = new HttpGet(ARMORY_BASE_URL + "auctionhouse/index.xml#summary");
            final HttpResponse summaryResponse = globalHttpClient.execute(summaryGet);
            summaryResponse.getEntity().consumeContent();
            final String cookieName = "auction_sk";
            LOG.debug("Searching for '{}' cookie", cookieName);
            for(final Cookie cookie : globalHttpClient.getCookieStore().getCookies()) {
                LOG.trace(" {}: {}", cookie.getName(), cookie.getValue());
                if(cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        } catch (final Exception e) {
            throw new ArmoryConnectionException("Error getting auctionhause summary page", e);
        }
        return "";
    }

    public ArmorySearch searchArmory(final String searchTerm, final String searchType) throws ArmoryConnectionException {
        return executeRestQuery("search.xml?searchQuery=" + EncodingUtils.urlEncode(searchTerm, "UTF-8")
                + "&searchType=" + EncodingUtils.urlEncode(searchType, "UTF-8")).armorySearch;
    }

    public void selectPrimaryCharacter(final String charName, final String realm) throws ArmoryConnectionException {
        LOG.info("Selecting Primary Char '{}' on realm '{}'", charName, realm);
        executeJsonPost("vault/character-select-submit.json",
                new BasicNameValuePair("cn", charName),
                new BasicNameValuePair("r", realm));
        this.primaryCharname = charName;
        this.primaryRealm = realm;

    }

    protected void executeJsonPost(final String requestPath, final BasicNameValuePair ...parameters) throws ArmoryConnectionException {
        ArmoryConnectionException lastException = null;
        for(int currentTry=1; currentTry<=MAX_REQUEST_RETRIES ; currentTry++) {
            try {
                _executeJsonPost(requestPath, parameters);
                return;
            } catch (ArmoryConnectionException queryException) {
                lastException = queryException;
                try {
                    relog();
                } catch (ArmoryConnectionException loginException) {
                    lastException = loginException;
                }
            }
        }
        throw lastException;
    }


    private void _executeJsonPost(final String requestPath, final BasicNameValuePair ...parameters) throws ArmoryConnectionException {

        final String jsonPostUrl = ARMORY_BASE_URL + requestPath;
        try {
            LOG.debug("Executing 'JSON' POST Method: " + jsonPostUrl);
            final HttpPost buyoutPost = new HttpPost(jsonPostUrl);
            if(parameters.length > 0) {
                final List <NameValuePair> nvps = new ArrayList <NameValuePair>();
                for(final BasicNameValuePair parameter : parameters) {
                    nvps.add(parameter);
                }
                try {
                    buyoutPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
                } catch (final UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
            buyoutPost.setHeader("Accept", "application/json, text/javascript, */*");
            final HttpResponse summaryResponse = globalHttpClient.execute(buyoutPost);
            if(LOG.isTraceEnabled()) {
                LOG.trace(IOUtils.toString(summaryResponse.getEntity().getContent()));
            } else {
                summaryResponse.getEntity().consumeContent();
            }
        } catch (final Exception e) {
            throw new ArmoryConnectionException("Error sending JSON Request: " + jsonPostUrl, e);
        }
    }


    protected Page executeRestQuery(final String armoryRequest) throws ArmoryConnectionException {
        ArmoryConnectionException lastException = null;
        for(int currentTry=1; currentTry<=MAX_REQUEST_RETRIES ; currentTry++) {
            try {
                return _executeRestQuery(armoryRequest);
            } catch (ArmoryConnectionException queryException) {
                lastException = queryException;
                try {
                    relog();
                } catch (ArmoryConnectionException loginException) {
                    lastException = loginException;
                }
            }
        }
        throw lastException;
    }

    private Page _executeRestQuery(final String armoryRequest) throws ArmoryConnectionException {
        final String requestUrl = ARMORY_BASE_URL + armoryRequest + "&rhtml=n";
        LOG.debug("Executing GET Method: " + requestUrl);
        final ClientRequest request = new ClientRequest(requestUrl, new ApacheHttpClient4Executor(globalHttpClient));
        ClientResponse<Page> response;
        try {
            Thread.sleep(1000); // Grace Period, weil 2 Requests kurz hintereinander zu Problemen führen können!
            if(LOG.isTraceEnabled()) {
                request.accept("*/*; charset=UTF-8");
                LOG.trace(request.get(String.class).getEntity());
                Thread.sleep(2000); // Grace Period, weil 2 Requests kurz hintereinander zu Problemen führen können!
            }
            request.accept(MediaType.APPLICATION_XML);
            response = request.get(Page.class);
        } catch (final Exception e) {
            throw new ArmoryConnectionException("Error connection to Armory: " + requestUrl, e);
        }
        final Status status = response.getResponseStatus();
        if(status == Status.OK) {
            Page page = response.getEntity();
            if(page.error != null && page.error.error) {
                    throw new ArmoryRequestError(page.error);
            }
            return page;
        } else {
            throw new ArmoryConnectionException("Got '" + status + "' Response from Armory: " + requestUrl);
        }
    }

    public void searchItem(final String name) throws ArmoryConnectionException {
        // TODO:
        /*
        return new ItemSearch(executeXmlQuery("search.xml?searchQuery=" + EncodingUtils.urlEncode(name, "UTF-8")
                + "&fl%5Bsource%5D=all"
                + "&fl%5Btype%5D=all"
                + "&fl%5BusbleBy%5D=all"
                + "&fl%5BrqrMin%5D="
                + "&fl%5BrqrMax%5D="
                + "&fl%5Brrt%5D=all"
                + "&advOptName=none"
                + "&fl%5Bandor%5D=and"
                + "&searchType=items"));
         */
        throw new NotImplementedException("searchItem: TODO?");
    }


    public AuctionSearch searchAuction(final String searchTerm, final Quality quality, final boolean exactMatch) throws ArmoryConnectionException {
        final AuctionSearch auctionSearch = executeRestQuery("auctionhouse/search/?"+
                "sort=rarity&"+
                "reverse=false"+
                "&n="+ EncodingUtils.urlEncode(searchTerm, "UTF-8") +
                "&qual="+ quality.numericValue +
                "&cn=" + EncodingUtils.urlEncode(primaryCharname, "UTF-8") +
                "&r=" + EncodingUtils.urlEncode(primaryRealm, "UTF-8") +
                "&f=0"+
                "&start=" + 0 +
                "&pageSize=" + AUCTION_PAGE_SIZE).auctionSearch;
        if(auctionSearch==null) {
            final AuctionSearch search = new AuctionSearch();
            search.auctionItems = new ArrayList<AuctionItem>();
            return search;
        }
        for(int start=AUCTION_PAGE_SIZE; start < auctionSearch.total ; start+= AUCTION_PAGE_SIZE) {
            final Page page = executeRestQuery("auctionhouse/search/?"+
                    "sort=rarity&"+
                    "reverse=false"+
                    "&n="+ EncodingUtils.urlEncode(searchTerm, "UTF-8") +
                    "&qual="+ quality.numericValue +
                    "&cn=" + EncodingUtils.urlEncode(primaryCharname, "UTF-8") +
                    "&r=" + EncodingUtils.urlEncode(primaryRealm, "UTF-8") +
                    "&f=0"+
                    "&start=" + start +
                    "&pageSize=" + AUCTION_PAGE_SIZE);
            if(page.auctionSearch!=null) {
                auctionSearch.auctionItems.addAll(page.auctionSearch.auctionItems);
            } else {
                LOG.error("Auction LookUp failed! ({}/{})", start, auctionSearch.total);
            }
        }
        if(exactMatch) {
            final List<AuctionItem> markedForRemoval = new ArrayList<AuctionItem>();
            for(final AuctionItem item : auctionSearch.auctionItems) {
                if(!item.name.equals(searchTerm)) {
                    markedForRemoval.add(item);
                }
            }
            auctionSearch.auctionItems.removeAll(markedForRemoval);
        }
        auctionSearch.end = auctionSearch.auctionItems.size();
        return auctionSearch;
    }
}
