package de.mancino.armory;

public class ArmoryBaseUri {

    /**
     * Region. This is mainly used for URL-Prefixes.
     */
    private static final ArmoryRegion DEFAULT_REGION = ArmoryRegion.EUROPE;
    
    /**
     * Language.
     */
    private static final ArmoryLanguage DEFAULT_LANGUAGE = ArmoryLanguage.ENGLISH;

    private ArmoryRegion region;
    private ArmoryLanguage language;

    public ArmoryBaseUri() {
        this(DEFAULT_REGION, DEFAULT_LANGUAGE);
    }
    
    public ArmoryBaseUri(final ArmoryRegion region, ArmoryLanguage language) {
        this.setRegion(region);
        this.setLanguage(language);
    }
// XXX: Create member variables for each getters - but first find out which are really needed...
    public String getBaseUri() {
        return "https://" + region.hostName + "/";
    }

    public String getHompageUri() {
        return "https://" + region.hostName + "/wow/" + language.language + "/";
    }
    public String getApiUri() {
        return "https://" + region.hostName + "/api/wow/";
    }

    /**
     * @return the region
     */
    public ArmoryRegion getRegion() {
        return region;
    }
    /**
     * @param region the region to set
     */
    public void setRegion(ArmoryRegion region) {
        this.region = region;
    }
    /**
     * @return the language
     */
    public ArmoryLanguage getLanguage() {
        return language;
    }
    /**
     * @param language the language to set
     */
    public void setLanguage(ArmoryLanguage language) {
        this.language = language;
    }
}
