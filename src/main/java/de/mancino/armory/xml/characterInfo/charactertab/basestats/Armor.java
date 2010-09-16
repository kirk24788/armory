/*
 * Armor.java 16.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.xml.characterInfo.charactertab.basestats;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * Example Snippet:
    <armor base="7356"
     effective="7356"
     percent="32.57"
     petBonus="-1"/>
 * @author mmancino
 */
@XmlType(name="characterInfo.charactertab.basestats.Armor")
public class Armor {
    @XmlAttribute
    public int base;
    @XmlAttribute
    public int effective;
    @XmlAttribute
    public float percent;
    @XmlAttribute
    public int petBonus;
}
