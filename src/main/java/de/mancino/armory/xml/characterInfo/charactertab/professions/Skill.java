/*
 * Skill.java 16.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.xml.characterInfo.charactertab.professions;

import javax.xml.bind.annotation.XmlAttribute;

/**
*
* Example Snippet:
   <skill id="171" key="alchemy" max="450" name="Alchemy" value="450"/>
* @author mmancino
*/
public class Skill {
    @XmlAttribute
    public int id;
    @XmlAttribute
    public String key;
    @XmlAttribute
    public int max;
    @XmlAttribute
    public String name;
    @XmlAttribute
    public int value;
}

