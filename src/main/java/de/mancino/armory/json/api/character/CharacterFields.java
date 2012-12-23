package de.mancino.armory.json.api.character;

public enum CharacterFields {
    GUILD("guild"),
    STATS("stats"),
    TALENTS("talents"),
    ITEMS("items"),
    REPUTATION("reputation"),
    TITLES("titles"),
    PROFESSIONS("professions"),
    APPEARANCE("appearance"),
    COMPANIONS("companions"),
    MOUNTS("mounts"),
    PETS("pets"),
    ACHIEVEMENTS("achievements"),
    PROGRESSION("progression");
    
    public final String key;
    private CharacterFields(final String key) {
        this.key = key;
    }
}
