package de.mancino.armory.xml.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


@XmlEnum
public enum Quality {
    @XmlEnumValue(value="0")
    POOR(0, "Poor", "Grey"), // Grey
    @XmlEnumValue(value="1")
    COMMON(1, "Common", "White"), // White
    @XmlEnumValue(value="2")
    UNCOMMON(2, "Uncommon", "Green"), // Green
    @XmlEnumValue(value="3")
    RARE(3, "Rare", "Blue"), // Blue
    @XmlEnumValue(value="4")
    EPIC(4, "Epic", "Purple"), // Purple
    @XmlEnumValue(value="5")
    LEGENDARY(5, "Legendary", "Orange"), // Orange
    @XmlEnumValue(value="6")
    ARTIFACT(6, "Artifact", "Light gold"), // Light gold
    @XmlEnumValue(value="7")
    HEIRLOOM(7, "Heirloom", "Light gold"); // Light gold

    public final int numericValue;
    public final String description;
    public final String color;

    Quality(final int numericValue, final String description, final String color) {
        this.numericValue = numericValue;
        this.description = description;
        this.color = color;
    }

    @Override
    public String toString() {
        return description + " (" + color + ")";
    }
}

