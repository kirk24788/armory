package de.mancino.armory.json.vault;

public enum AuctionFaction {
    ALLIANCE("alliance"),
    HORDE("horde"),
    NEUTRAL("neutral");

    public final String key;

    private AuctionFaction(final String key) {
        this.key = key;
    }
}
