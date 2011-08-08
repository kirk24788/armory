package de.mancino.armory.datatypes;

public enum ArmoryLanguage {
    ENGLISH("en"),
    GERMAN("de"),
    FRENCH("fr");
    
    public final String language;

    ArmoryLanguage(final String language) {
        this.language = language;
    }
}
