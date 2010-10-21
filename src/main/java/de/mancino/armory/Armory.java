package de.mancino.armory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
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
import de.mancino.utils.PostRedirectHandler;
import de.mancino.utils.RetryableRequest;

/**
 * Class for controlling Blizzard's WoW-Armory Interface.
 * The <code>Armory</code> class represents rougly Blizzards
 * WoW Armory Web-Interface.
 *
 * The Armory can be used with oder without login, although it is
 * recommended to always use a login.
 * While the Armory will work without login, most functions won't
 * work as expected / throw errors.
 *
 * @author mmancino
 */
public class Armory {
    /**
     * Logger instance of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Armory.class);

    /**
     * Page-Size for Auction Fetches. Higher Page-Sizes reduce request-counts and
     * missed/duplicate auctions due to high activity, but Blizzard seems to have
     * a page-size cap of 40 (?).
     */
    private static final int AUCTION_PAGE_SIZE = 40;

    /**
     * Region. This is mainly used for URL-Prefixes.
     */
    private static final String REGION = "eu";

    /**
     * Base-URL for WoW Armory.
     */
    private static final String ARMORY_BASE_URL = "http://" + REGION + ".wowarmory.com/";

    /**
     * Base-URL for BattleNet.
     */
    private static final String BATTLENET_BASE_URL = "https://" + REGION + ".battle.net/";

    /**
     * BattleNet Account Name.
     */
    private final String accountName;

    /**
     * BattleNet Account Password.
     */
    private final String password;

    /**
     * Character for Armory interaction.
     */
    private String primaryCharname;

    /**
     * Character's Realm.
     */
    private String primaryRealm;

    /**
     * HttpClient used for Armory Requests
     */
    private DefaultHttpClient globalHttpClient;

    /**
     * Class for controlling Blizzard's WoW-Armory Interface.
     * The <code>Armory</code> class represents rougly Blizzards
     * WoW Armory Web-Interface.
     *
     * Please refrain from using this constructor, since you wont'
     * be logged in to the armory.
     * While the Armory will work without login, most functions won't
     * work as expected / throw errors.
     */
    public Armory() {
        registerResteasyProviders();
        globalHttpClient = new DefaultHttpClient();
        primaryCharname = "";
        primaryRealm = "";
        accountName = "";
        password = "";
    }


    /**
     * Class for controlling Blizzard's WoW-Armory Interface.
     * The <code>Armory</code> class represents rougly Blizzards
     * WoW Armory Web-Interface.
     *
     * @param accountName BattleNet Account Name
     * @param password Password for the BattleNet Account
     * @param primaryCharname Charname used for armory interaction
     * @param primaryRealm Characters realm
     *
     * @throws ArmoryConnectionException Error logging in, this happen if
     *         username/password or the charname/realm combination is wrong
     */
    public Armory(final String accountName, final String password,
            final String primaryCharname, final String primaryRealm) throws ArmoryConnectionException {
        registerResteasyProviders();
        this.primaryCharname = primaryCharname;
        this.primaryRealm = primaryRealm;
        this.accountName = accountName;
        this.password = password;
        login();
        selectPrimaryCharacter(primaryCharname, primaryRealm);
    }

    /**
     * Register RestEasy Message Providers.
     */
    private static void registerResteasyProviders() {
        ResteasyProviderFactory.getInstance().addBuiltInMessageBodyReader(new StringTextStar());
        ResteasyProviderFactory.getInstance().addBuiltInMessageBodyReader(new DataSourceProvider());
        ResteasyProviderFactory.getInstance().addBuiltInMessageBodyReader(new FormUrlEncodedProvider());
    }

    /**
     * Attempt to relog into BattleNet.
     * This shouldn't be necessary any more, since a relog is automatically attempted.
     * This method is deprecated and will be removed in future version.
     *
     * @throws ArmoryConnectionException Error while relogging.
     */
    @Deprecated
    public void relog() throws ArmoryConnectionException {
        login();
        selectPrimaryCharacter(primaryCharname, primaryRealm);
    }

    /**
     * Login to BattleNet
     *
     * @throws ArmoryConnectionException Error while logging in.
     */
    private void login() throws ArmoryConnectionException {
        globalHttpClient = new DefaultHttpClient();
        //httpClient. getParams().setCookiePolicy(CookiePolicy.RFC_2109);
        final String armoryUrl = BATTLENET_BASE_URL
            + "login/en/login.xml?app=armory&ref=http%3A%2F%2Feu.wowarmory.com%2Findex.xml&cr=true";
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

    /**
     * Fetch Character-Sheet for a given Character.
     *
     * @param charName Character name.
     * @param realmName Character's realm
     *
     * @return Character-Sheet
     *
     * @throws ArmoryConnectionException Error connecting to Armory
     */
    public CharacterInfo getCharacterSheet(final String charName, final String realmName) throws ArmoryConnectionException {
        return executeRestQuery("character-sheet.xml?r=" + realmName
                + "&n=" + EncodingUtils.urlEncode(charName, "UTF-8")).characterInfo;
    }

    /**
     * Fetch Item Info for a given item-id.
     *
     * @param itemId Item-ID
     *
     * @return Item Tooltip information
     *
     * @throws ArmoryConnectionException Error connecting to Armory
     */
    public ItemTooltip getItemInfo(final long itemId) throws ArmoryConnectionException {
        return executeRestQuery("item-tooltip.xml?i=" + itemId).itemTooltips.get(0);
    }

    /**
     * Search Armory for the given String.
     *
     * @param searchTerm Search-Term
     *
     * @return Armory Search Result
     *
     * @throws ArmoryConnectionException Error connecting to Armory
     */
    public ArmorySearch searchArmory(final String searchTerm) throws ArmoryConnectionException {
        return searchArmory(searchTerm, "all");
    }


    /**
     * Search Armory for the given String.
     *
     * @param searchTerm Search-Term
     * @param searchType Search-Type
     *
     * @return Armory Search Result
     *
     * @throws ArmoryConnectionException Error connecting to Armory
     */
    public ArmorySearch searchArmory(final String searchTerm, final String searchType) throws ArmoryConnectionException {
        return executeRestQuery("search.xml?searchQuery=" + EncodingUtils.urlEncode(searchTerm, "UTF-8")
                + "&searchType=" + EncodingUtils.urlEncode(searchType, "UTF-8")).armorySearch;
    }

    /**
     * Attempts to buyout the given Auction.
     *
     * @param auctionItem Auction Item
     *
     * @throws ArmoryConnectionException Error connecting to Armory
     */
    public void buyAuction(final AuctionItem auctionItem) throws ArmoryConnectionException {
        LOG.info("Buying Auction {} ({})", auctionItem.auctionId, auctionItem.name);
        executeJsonPost("auctionhouse/bid.json",
                new BasicNameValuePair("auc", String.valueOf(auctionItem.auctionId)),
                new BasicNameValuePair("money", String.valueOf(auctionItem.buy)),
                new BasicNameValuePair("sk", getSkValue()));
    }

    /**
     * Returns the SK(Security-Key?) Cookie Value.
     *
     * @return SK(Security-Key?) Cookie Value
     *
     * @throws ArmoryConnectionException Error connecting to Armory
     */
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

    /**
     * Updates the primary Character for armory interaction.
     *
     * @param charName character name
     * @param realm realm name
     *
     * @throws ArmoryConnectionException Error conenction to armory
     */
    public void selectPrimaryCharacter(final String charName, final String realm) throws ArmoryConnectionException {
        LOG.info("Selecting Primary Char '{}' on realm '{}'", charName, realm);
        executeJsonPost("vault/character-select-submit.json",
                new BasicNameValuePair("cn", charName),
                new BasicNameValuePair("r", realm));
        this.primaryCharname = charName;
        this.primaryRealm = realm;
    }

    /**
     * Executes JSON Requests to the armory. If errors occur
     * it will be tried to relog and connect again up to {@link #MAX_REQUEST_RETRIES} times.
     *
     * @param requestPath Request-Path
     * @param parameters Request-Parameters
     * @throws ArmoryConnectionException Error while connecting to armory
     */
    protected void executeJsonPost(final String requestPath,
                   final BasicNameValuePair ...parameters) throws ArmoryConnectionException {
        try {
            new RetryableRequest<Void>() {
                @Override
                protected Void request() throws Throwable {
                    internalExecuteJsonPost(requestPath, parameters);
                    return null;
                }

                @Override
                protected void errorCleanup() throws Throwable {
                    relog();
                }
            }.requestWithRetries();
        } catch (Throwable t) {
            throw new ArmoryConnectionException(t);
        }
    }



    /**
     * Executes JSON Requests to the armory.
     *
     * @param requestPath Request-Path
     * @param parameters Request-Parameters
     *
     * @throws ArmoryConnectionException Error connencting to armory
     */
    private void internalExecuteJsonPost(final String requestPath,
                 final BasicNameValuePair ...parameters) throws ArmoryConnectionException {
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

    /**
     * Executes XML Requests to the armory. If errors occur
     * it will be tried to relog and connect again up to {@link #MAX_REQUEST_RETRIES} times.
     *
     * @param armoryRequest Armory Request
     *
     * @return Reponse XML Page
     *
     * @throws ArmoryConnectionException Error conenction to armory
     */
    protected Page executeRestQuery(final String armoryRequest) throws ArmoryConnectionException {
        try {
            return new RetryableRequest<Page>() {
                @Override
                protected Page request() throws Throwable {
                    return internalExecuteRestQuery(armoryRequest);
                }

                @Override
                protected void errorCleanup() throws Throwable {
                    relog();
                }
            }.requestWithRetries();
        } catch (Throwable t) {
            throw new ArmoryConnectionException(t);
        }
    }

    /**
     * Executes XML Requests to the armory.
     *
     * @param armoryRequest Armory Request
     *
     * @return Reponse XML Page
     *
     * @throws ArmoryConnectionException Error conenction to armory
     */
    private Page internalExecuteRestQuery(final String armoryRequest) throws ArmoryConnectionException {
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

    /**
     * Searches the auction-house of the given primary character for items with given name and Quality.
     * If requested the auctions will be filtered to only contain exact matches.
     *
     * @param searchTerm Search-Term
     * @param quality Quality
     * @param exactMatch true if result should only contain exact matches, false otherwise
     *
     * @return Auction Search Result
     *
     * @throws ArmoryConnectionException Error conenction to armory
     */
    public AuctionSearch searchAuction(final String searchTerm,
                         final Quality quality, final boolean exactMatch) throws ArmoryConnectionException {
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
