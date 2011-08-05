package de.mancino.armory.json.api.guild;

public enum GuildFields {
    MEMBERS("members"),
    ACHIEVEMENTS("achievements");
    
    public final String key;
    private GuildFields(final String key) {
        this.key = key;
    }
}
