/*
 * Armory.java 20.08.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import de.mancino.armory.auction.AuctionSearch;
import de.mancino.armory.character.CharacterSheet;
import de.mancino.armory.enums.Quality;
import de.mancino.armory.item.Item;
import de.mancino.armory.item.ItemInfo;
import de.mancino.armory.item.ItemSearch;
import de.mancino.exceptions.ArmoryConnectionException;

public class Armory {
    private static final int AUCTION_PAGE_SIZE = 40;

    private static final int MAX_AUCTION_ENTRIES = 200;

    /**
     * Logger instance of this class.
     */
    private static final Log LOG = LogFactory.getLog(Armory.class);

    private static final String REGION = "eu";
    private static final String ARMORY_BASE_URL = "http://" + REGION + ".wowarmory.com/";
    private static final String BATTLENET_BASE_URL = "https://" + REGION + ".battle.net/";

    private final HttpClient globalHttpClient;

    public Armory(final String accountName, final String password) throws ArmoryConnectionException {
        globalHttpClient = login(accountName, password);
    }

    private HttpClient login(final String accountName, final String password) throws ArmoryConnectionException {
        final DefaultHttpClient httpClient = new DefaultHttpClient();
        //httpClient. getParams().setCookiePolicy(CookiePolicy.RFC_2109);
        final String armoryUrl = BATTLENET_BASE_URL + "login/en/login.xml?app=armory&ref=http%3A%2F%2Feu.wowarmory.com%2Findex.xml&cr=true";

        if(LOG.isDebugEnabled()) {
            LOG.debug("Logging in to Armory ("+accountName +":" + password.charAt(0) + "***"
                    + password.charAt(password.length()-1) + "): " + armoryUrl);
        }
        final HttpPost postLogin = new HttpPost(armoryUrl);

        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair("accountName", accountName));
        nvps.add(new BasicNameValuePair("password", password));
        nvps.add(new BasicNameValuePair("persistLogin", "on"));

        try {
            postLogin.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            final HttpResponse postResponse = httpClient.execute(postLogin);
            LOG.trace("Response: " + postResponse.getStatusLine());
            postResponse.getEntity().consumeContent();
            final String redirectUrl = postResponse.getHeaders("Location")[0].getValue();
            LOG.trace("Login redirected to: " + redirectUrl);
            final HttpGet loginRedirect = new HttpGet(redirectUrl);
            try {
                final HttpResponse redirectResponse = httpClient.execute(loginRedirect);
                LOG.trace("Response: " + redirectResponse.getStatusLine());
                redirectResponse.getEntity().consumeContent();
            } catch (IOException e) {
                throw new ArmoryConnectionException("Login failed!", e);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new ArmoryConnectionException("Login failed!", e);
        }
        return httpClient;
    }

    protected Document executeXmlQuery(final String armoryRequest) throws ArmoryConnectionException {
        final String armoryUrl = ARMORY_BASE_URL + armoryRequest + "&rhtml=n";
        LOG.debug("Executing GET Method: " + armoryUrl);
        final HttpGet httpMethod = new HttpGet(armoryUrl);
        try {
            HttpResponse httpResponse = globalHttpClient.execute(httpMethod);
            SAXBuilder parser = new SAXBuilder();
            final Document document = parser.build(httpResponse.getEntity().getContent());
            LOG.trace("Received XML Data:\n" + xmlToString(document));
            return document;
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
            LOG.debug("Stacktrace:", e);
            throw new ArmoryConnectionException( e);
        }
    }

    public CharacterSheet getCharacterSheet(final String charName, final String realmName) throws ArmoryConnectionException {
        return new CharacterSheet(executeXmlQuery("character-sheet.xml?r=" + realmName + "&n=" + URLEncoder.encode(charName)));
    }

    public ItemInfo getItemInfo(final Item item) throws ArmoryConnectionException {
        return getItemInfo(item.id);
    }

    public ItemInfo getItemInfo(final long itemId) throws ArmoryConnectionException {
        return new ItemInfo(executeXmlQuery("item-tooltip.xml?i=" + itemId));
    }

    public ItemSearch searchItem(final String name) throws ArmoryConnectionException {
        return new ItemSearch(executeXmlQuery("search.xml?searchQuery=" + URLEncoder.encode(name)
                + "&fl%5Bsource%5D=all"
                + "&fl%5Btype%5D=all"
                + "&fl%5BusbleBy%5D=all"
                + "&fl%5BrqrMin%5D="
                + "&fl%5BrqrMax%5D="
                + "&fl%5Brrt%5D=all"
                + "&advOptName=none"
                + "&fl%5Bandor%5D=and"
                + "&searchType=items"));
    }


    public AuctionSearch searchAuction(final String searchTerm, Quality quality) throws ArmoryConnectionException {
        // TODO: Optimieren, u.U. sind die letzten Requests unnötig, wenn bereits vorher keine mehr gefunden wurdne...
        /*
         GET /auctionhouse/search/?sort=rarity&reverse=false&filterId=-1&n=adder&maxLvl=0&minLvl=0&qual=0&start=20&total=59&pageSize=20&rhtml=true&cn=Chevron&r=Forscherliga&f=0&sk=0330fce3-9be0-4adf-b57b-78f09474b7f4 HTTP/1.1
         */
        final Document searchResults = new Document();
        searchResults.setRootElement(new Element("combined-search-results"));

        int maxAuctionEntries = MAX_AUCTION_ENTRIES;
        for(int start = 0 ; start < maxAuctionEntries ; start += AUCTION_PAGE_SIZE) {
            Document partialResult = executeXmlQuery("auctionhouse/search/?"+
                    "sort=rarity&"+
                    "reverse=false"+
                    "&n="+ URLEncoder.encode(searchTerm) +
                    "&qual="+ quality.numericValue +
                    "&cn=Chevron"+
                    "&r=Forscherliga"+
                    "&f=0"+
                    "&start=" + start +
                    "&pageSize=" + AUCTION_PAGE_SIZE);
            maxAuctionEntries = Integer.parseInt(
                    partialResult.getRootElement().getChild("auctionSearch").getAttribute("total").getValue());
            searchResults.getRootElement().addContent(
                    partialResult.getRootElement().getChild("auctionSearch").detach());
        }

        return new AuctionSearch(searchResults);
    }


    private static String xmlToString(Document document) {
        XMLOutputter outputter = new XMLOutputter();
        try {
            StringWriter sw = new StringWriter();
            outputter.output(document, sw);
            return sw.toString();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
