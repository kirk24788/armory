/*
 * Member.java 16.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.xml.characterInfo.character.arenateams;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * Example Snippet:
    <character
     battleGroup=""
     charUrl="r=Forscherliga&amp;cn=Le%C3%A1nara"
     class="Mage"
     classId="8"
     contribution="0"
     gamesPlayed="0"
     gamesWon="0"
     gender="Female"
     genderId="1"
     guild="The Mysthik of Draenei"
     guildId="3824267"
     guildUrl="r=Forscherliga&amp;gn=The+Mysthik+of+Draenei"
     name="Leánara"
     race="Human"
     raceId="1"
     seasonGamesPlayed="0"
     seasonGamesWon="0"
     teamRank="0"/>
 * @author mmancino
 */
@XmlType(name="characterInfo.character.arenateams.Character")
public class Character {
    @XmlAttribute
    public String battleGroup;
    @XmlAttribute
    public String charUrl;
    @XmlAttribute(name="class")
    public String className;
    @XmlAttribute
    public int classId;
    @XmlAttribute
    public String contribution;
    @XmlAttribute
    public int gamesPlayed;
    @XmlAttribute
    public int gamesWon;
    @XmlAttribute
    public String gender;
    @XmlAttribute
    public int genderId;
    @XmlAttribute
    public String guild;
    @XmlAttribute
    public int guildId;
    @XmlAttribute
    public String guildUrl;
    @XmlAttribute
    public String name;
    @XmlAttribute
    public String race;
    @XmlAttribute
    public int raceId;
    @XmlAttribute
    public int seasonGamesPlayed;
    @XmlAttribute
    public int seasonGamesWon;
    @XmlAttribute
    public int teamRank;
}
