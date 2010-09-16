/*
 * ArenaCurrenty.java 16.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.xml.characterInfo.charactertab.pvp;

import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * Example Snippet:
   <arenacurrency value="0"/>
 * @author mmancino
 */
public class ArenaCurrency {
    @XmlAttribute
    public long value;
}
