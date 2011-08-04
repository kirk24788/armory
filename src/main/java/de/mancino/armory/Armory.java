package de.mancino.armory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;
import org.jboss.resteasy.plugins.providers.DataSourceProvider;
import org.jboss.resteasy.plugins.providers.FormUrlEncodedProvider;
import org.jboss.resteasy.plugins.providers.StringTextStar;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import de.mancino.utils.PostRedirectStrategy;
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
     * Language.
     */
    private static final String LANGUAGE = "de";

    /**
     * Base-URL for WoW Armory.
     */
    private static final String ARMORY_BASE_URL = "https://" + REGION + ".battle.net/wow/" + LANGUAGE + "/";
    private static final String PLAIN_BASE_URL = "http://" + REGION + ".battle.net/wow/" + LANGUAGE + "/";
    private static final String SECURE_BASE_URL = "https://" + REGION + ".battle.net/wow/" + LANGUAGE + "/";
//https://eu.battle.net/wow/de/vault/character/
    //https://eu.battle.net/wow/de/vault/character/auction/alliance/bid
    //https://eu.battle.net/wow/de/vault/character/auction/alliance/bid
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
        globalHttpClient = createHttpClient();
        primaryCharname = "";
        primaryRealm = "";
        accountName = "";
        password = "";
    }

    public DefaultHttpClient createHttpClient() {
        // Create and initialize HTTP parameters
        HttpParams params = new BasicHttpParams();
        ConnManagerParams.setMaxTotalConnections(params, 100);
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

        // Create and initialize scheme registry·
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", PlainSocketFactory.getSocketFactory(), 433));

        // Create an HttpClient with the ThreadSafeClientConnManager.
        // This connection manager must be used if more than one thread will
        // be using the HttpClient.
        ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
        return new DefaultHttpClient(cm, params);

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
     */
    public Armory(final String accountName, final String password,
            final String primaryCharname, final String primaryRealm) {
        registerResteasyProviders();
        this.primaryCharname = primaryCharname;
        this.primaryRealm = primaryRealm;
        this.accountName = accountName;
        this.password = password;
        try {
            login();
         //  selectPrimaryCharacter(primaryCharname, primaryRealm);
        } catch (ArmoryConnectionException e) {
            LOG.error("Armory-Login wasn't possible during initialization!");
        }
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
     * Login to BattleNet
     *
     * @throws ArmoryConnectionException Error while logging in.
     */
    private void login() throws ArmoryConnectionException {
        globalHttpClient = new DefaultHttpClient();
        //httpClient. getParams().setCookiePolicy(CookiePolicy.RFC_2109);
        final String armoryUrl = BATTLENET_BASE_URL
                + "login/en/?app=armory&ref=https%3A%2F%2Feu.battle.net%2Fwow%2Fen%2F";
        globalHttpClient.setRedirectStrategy(new PostRedirectStrategy());
        if(LOG.isInfoEnabled()) {
            LOG.info("Logging in to Armory ("+accountName +":" + password.charAt(0) + "***"
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
    
    private String getCookieValue(final String cookieName) {
        LOG.debug("Searching for '{}' cookie", cookieName);
        for(final Cookie cookie : globalHttpClient.getCookieStore().getCookies()) {
            LOG.trace(" {}: {}", cookie.getName(), cookie.getValue());
            if(cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return "";
    }

    public void bid(final long auctionId, final long money) throws ArmoryConnectionException {
        // https://eu.battle.net/wow/de/vault/character/auction/alliance/bid
        LOG.info("Buying Auction {} ({})", auctionId, money);
        internalExecuteJsonPost("vault/character/auction/alliance/bid",
                new BasicNameValuePair("auc", String.valueOf(auctionId)),
                new BasicNameValuePair("money", String.valueOf(money)),
                new BasicNameValuePair("token", getCookieValue("xstoken")));
    }
    
public void selectCharacter(final int characterId) throws ArmoryConnectionException {
    // /pref/character

    LOG.info("Selecting Character {}", characterId);
    // "/wow/de/pref/character"
    /*
     * 
  Anfrage-URL:http://eu.battle.net/wow/de/pref/character
Anfragemethode:POST
Status-Code:200 OK
Anfrage-HeaderQuelltext anzeigen
Accept: * /*
Content-Type:application/x-www-form-urlencoded
Origin:http://eu.battle.net
Referer:http://eu.battle.net/wow/de/character/forscherliga/B%C3%A6n/advanced
User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7) AppleWebKit/534.48.3 (KHTML, like Gecko) Version/5.1 Safari/534.48.3
X-Requested-With:XMLHttpRequest
FormulardatenURL-Codiert anzeigen
index:3
xstoken:b08d3418-39bf-4836-914e-3e2600b604b8
     */
    try {
        System.err.println(internalExecutePost(SECURE_BASE_URL, "vault/character/auction/alliance/money"));
        String t = internalExecutePost(SECURE_BASE_URL, "pref/character",
                new BasicNameValuePair("index", String.valueOf(characterId)),
                new BasicNameValuePair("xstoken", getCookieValue("xstoken")));
        
        System.err.println(internalExecutePost(SECURE_BASE_URL, "vault/character/auction/alliance/money"));
    } catch (IllegalStateException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
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
     * @param searchType Search-Type
     *
     * @return Armory Search Result
     *
     * @throws ArmoryConnectionException Error connecting to Armory
     */
    public ArmorySearch searchArmory(final String searchTerm, final SelectedTab searchType) throws ArmoryConnectionException {
        return executeRestQuery("search.xml?searchQuery=" + EncodingUtils.urlEncode(searchTerm, "UTF-8")
                + "&searchType=all"
                + "&selectedTab=" + EncodingUtils.urlEncode(searchType.tabName, "UTF-8")).armorySearch;
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
                true,
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
        } catch (final IOException e) {
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
                false,
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
    protected void executeJsonPost(final String requestPath, final boolean relogOnError,
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
                    if(relogOnError) {
                        login();
                        selectPrimaryCharacter(primaryCharname, primaryRealm);
                    }
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
        internalExecuteJsonPost(ARMORY_BASE_URL, requestPath, parameters);
    }
    
    private String internalExecutePost(final String baseUrl, final String requestPath, 
            final BasicNameValuePair ... parameters) throws IllegalStateException, IOException {
        final String postUrl = baseUrl + requestPath;
        LOG.debug("Executing  POST Method: " + postUrl);
        final HttpPost httpPost = new HttpPost(postUrl);
        if(parameters.length > 0) {
            final List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            for(final BasicNameValuePair parameter : parameters) {
                nvps.add(parameter);
            }
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            } catch (final UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        httpPost.setHeader("X-Requested-With","XMLHttpRequest");
        httpPost.setHeader("Referer","https://eu.battle.net/wow/de/vault/character/auction/alliance/browse");
        httpPost.setHeader("Origin","https://eu.battle.net");
        final HttpResponse summaryResponse = globalHttpClient.execute(httpPost);
        
        final String stringResponse = IOUtils.toString(summaryResponse.getEntity().getContent());
        if(LOG.isTraceEnabled()) {
            LOG.trace(stringResponse);
        }
        return stringResponse;
    }
    
    
    private void internalExecuteJsonPost(final String baseUrl, final String requestPath,
            final BasicNameValuePair ...parameters) throws ArmoryConnectionException {
        final String jsonPostUrl = baseUrl + requestPath;
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
            final String jsonResponse = IOUtils.toString(summaryResponse.getEntity().getContent());
            if(LOG.isTraceEnabled()) {
                LOG.trace(jsonResponse);
            }
            try {
                final JSONObject json = (JSONObject) new JSONParser().parse(jsonResponse);
                if(json.containsKey("error")) {
                    final JSONObject error = (JSONObject) json.get("error");
                    if (error.containsKey("message")) {
                        final String errorMsg = (String) error.get("message");
                        throw new ArmoryConnectionException(errorMsg);
                    }
                }
            } catch (ParseException e) {
                throw new ArmoryConnectionException("Couldn't parse Response JSON at: " + jsonPostUrl, e);
            }

        } catch (final IllegalStateException e) {
            final String msg = "IllegalStateException in JSON Request, this should NEVER happen";
            LOG.error(msg);
            throw new RuntimeException(msg, e);
        } catch (final IOException e) {
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
                    login();
                    selectPrimaryCharacter(primaryCharname, primaryRealm);
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
        return searchAuction(searchTerm, -1, -1, quality, exactMatch);
    }


    /**
     * Searches the auction-house of the given primary character for items with given name and Quality.
     * If requested the auctions will be filtered to only contain exact matches.
     *
     * @param searchTerm Search-Term
     * @param minLevel Minimum Level, -1 if none
     * @param maxLevel Maximum Level, -1 if none
     * @param quality Quality
     * @param exactMatch true if result should only contain exact matches, false otherwise
     *
     * @return Auction Search Result
     *
     * @throws ArmoryConnectionException Error conenction to armory
     */
    public AuctionSearch searchAuction(final String searchTerm,
            final int minLevel, final int maxLevel,
            final Quality quality, final boolean exactMatch) throws ArmoryConnectionException {
        final AuctionSearch auctionSearch = executeRestQuery("auctionhouse/search/?"+
                "sort=rarity"+
                "&reverse=false"+
                "&minLvl=" + minLevel +
                "&maxLvl=" + maxLevel +
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
                    "&reverse=false"+
                    "&minLvl=" + minLevel +
                    "&maxLvl=" + maxLevel +
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
    
    
    
    // Vault:
    /*
     * 
     * 
   https://eu.battle.net/wow/de/vault/character/auction/alliance/money
   
   
   {
  "auctionFaction" : 0,
  "character" : {
    "name" : "Bæn",
    "level" : 58,
    "genderId" : 0,
    "factionId" : 0,
    "classId" : 6,
    "className" : "Todesritter",
    "raceId" : 4,
    "raceName" : "Nachtelf",
    "realmName" : "Forscherliga",
    "achievementPoints" : 530,
    "side" : "ALLIANCE",
    "genderEnum" : "MALE"
  },
  "money" : 20401243
}
     */
}
