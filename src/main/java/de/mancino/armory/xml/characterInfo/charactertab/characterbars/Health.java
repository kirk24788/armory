/*
 * Health.java 16.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.xml.characterInfo.charactertab.characterbars;

import javax.xml.bind.annotation.XmlAttribute;

/**
*
* Example Snippet:
        <health effective="22174"/>
* @author mmancino
*/
public class Health {
    @XmlAttribute
    public int effective;
}