/*
 * Error.java 11.10.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.xml;

import javax.xml.bind.annotation.XmlAttribute;

/**
*
* Example Snippet:
* Siehe : http://eu.wowarmory.com/_layout/items/tooltip.xsl
    <error
     code="10005"
     error="true"
     message="You must log in."/>
* @author mmancino
*/
public class ArmoryError {
    @XmlAttribute
    public int code;
    @XmlAttribute
    public boolean error;
    @XmlAttribute
    public String message;
}
