package de.mancino.armory;

public enum ArmoryLanguage {
    ENGLISH("en"),
    GERMAN("de"),
    FRENCH("fr");
    
    public final String language;

    ArmoryLanguage(final String language) {
        this.language = language;
    }
}
