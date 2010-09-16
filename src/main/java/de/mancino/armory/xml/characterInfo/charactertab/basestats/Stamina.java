/*
 * Stamina.java 16.09.2010
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
    <stamina
     base="105"
     effective="1475"
     health="14570"
     petBonus="-1"/>
 * @author mmancino
 */
public class Stamina {
    @XmlAttribute
    public int base;
    @XmlAttribute
    public int effective;
    @XmlAttribute
    public int health;
    @XmlAttribute
    public int petBonus;
}