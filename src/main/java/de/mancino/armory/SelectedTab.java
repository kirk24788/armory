package de.mancino.armory;

public enum SelectedTab {
    CHARACTERS("characters"),
    ARENA_TEAMS("arenateams"),
    ITEMS("items"),
    GUILDS("guilds");
    
    public final String tabName;
    
    SelectedTab(final String tabName) {
        this.tabName = tabName;
    }
}
