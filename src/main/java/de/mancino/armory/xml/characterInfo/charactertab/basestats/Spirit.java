/*
 * Spirit.java 16.09.2010
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
    <spirit
     base="69"
     effective="79"
     healthRegen="27"
     manaRegen="-1"/>
 * @author mmancino
 */
public class Spirit {
    @XmlAttribute
    public int base;
    @XmlAttribute
    public int effective;
    @XmlAttribute
    public int healthRegen;
    @XmlAttribute
    public int manaRegen;
}
