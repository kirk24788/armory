package de.mancino.armory.xml.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * Glyph Type (prime, major or minor).
 * 
 * @author mmancino
 */
@XmlEnum
public enum GlyphType {
    @XmlEnumValue(value="prime")
    PRIME("prime"),
    @XmlEnumValue(value="major")
    MAJOR("major"),
    @XmlEnumValue(value="minor")
    MINOR("minor");

    /**
     * Glyph name.
     */
    public final String description;

    /**
     * Glyph Type (prime, major or minor).
     * 
     * @param description
     */
    GlyphType(final String description) {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return description;
    }
}


