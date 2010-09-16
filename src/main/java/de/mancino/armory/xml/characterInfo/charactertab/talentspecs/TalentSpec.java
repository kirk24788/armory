/*
 * TalentSpec.java 16.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.xml.characterInfo.charactertab.talentspecs;

import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * Example Snippet:
 *  <talentSpec active="1"
 *   group="1"
 *   icon="spell_shadow_metamorphosis"
 *   prim="Demonology"
 *   treeOne="0"
 *   treeThree="15"
 *   treeTwo="56"/>
 * @author mmancino
 */
public class TalentSpec {
    @XmlAttribute
    public boolean active;
    @XmlAttribute
    public int group;
    @XmlAttribute
    public String icon;
    @XmlAttribute
    public String prim;
    @XmlAttribute
    public int treeOne;
    @XmlAttribute
    public int treeThree;
    @XmlAttribute
    public int treeTwo;

}
