/*
 * Agility.java 16.09.2010
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
    <agility
     armor="3456"
     attack="1718"
     base="189"
     critHitPercent="20.44"
     effective="1728"/>
 * @author mmancino
 */
public class Agility {
    @XmlAttribute
    public int armor;
    @XmlAttribute
    public int attack;
    @XmlAttribute
    public int base;
    @XmlAttribute
    public float critHitPercent;
    @XmlAttribute
    public int effective;
}