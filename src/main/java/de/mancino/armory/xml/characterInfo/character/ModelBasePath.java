/*
 * ModelBasePath.java 16.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.xml.characterInfo.character;

import javax.xml.bind.annotation.XmlAttribute;

/**
*
* Example Snippet:
*  <modelBasePath value="http://eu.media.battle.net.edgesuite.net/"/>
* @author mmancino
*/
public class ModelBasePath {
    @XmlAttribute
    public String value;
}
