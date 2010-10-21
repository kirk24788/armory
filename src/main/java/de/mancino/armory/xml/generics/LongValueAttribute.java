package de.mancino.armory.xml.generics;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Generic Long value attribute.
 * 
 * @author mario
 */
public class LongValueAttribute {
    /**
     * Long attribute
     */
    @XmlAttribute
    public long value;
}