/*
 * Strength.java 16.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.xml.characterInfo.charactertab.basestats;

import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * Example Snippet:
    <strength
     attack="113"
     base="113"
     block="-1"
     effective="123"/>
 * @author mmancino
 */
public class Strength {
    @XmlAttribute
    public int attack;
    @XmlAttribute
    public int base;
    @XmlAttribute
    public int block;
    @XmlAttribute
    public int effective;
}