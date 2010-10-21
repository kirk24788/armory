package de.mancino.armory.xml.generics;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Generic String value attribute.
 * 
 * @author mario
 */
public class StringValueAttribute {
    /**
     * String attribute.
     */
    @XmlAttribute
    public String value;
}
