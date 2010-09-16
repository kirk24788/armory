/*
 * GlyphType.java 16.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.xml.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


@XmlEnum
public enum GlyphType {
    @XmlEnumValue(value="major")
    MAJOR("major"),
    @XmlEnumValue(value="minor")
    MINOR("minor");

    public final String description;

    GlyphType(final String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}


