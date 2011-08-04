package de.mancino.armory;

public enum ArmoryRegion {
    NORTH_AMERICA("us.battle.net"),
    EUROPE("eu.battle.net"),
    KOREA("kr.battle.net"),
    TAIWAN("tw.battle.net"),
    CHINA("battlenet.com.cn");
    
    public final String hostName;

    ArmoryRegion(final String hostName) {
        this.hostName = hostName;
    }
}
