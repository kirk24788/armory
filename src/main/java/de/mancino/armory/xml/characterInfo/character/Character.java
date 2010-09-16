/*
 * Character.java 16.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.xml.characterInfo.character;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import de.mancino.armory.xml.characterInfo.character.arenateams.ArenaTeam;

/**
 *
 * Example Snippet:
 *  <character
 *   battleGroup="Embuscade / Hinterhalt"
 *   charUrl="r=Forscherliga&amp;cn=H%C3%A9lios"
 *   class="Paladin"
 *   classId="2"
 *   classUrl="c=Paladin"
 *   faction="Alliance"
 *   factionId="0"
 *   gender="Male"
 *   genderId="0"
 *   guildName=""
 *   lastModified="September 14, 2010"
 *   level="38"
 *   name="Hélios"
 *   points="40"
 *   prefix=""
 *   race="Human"
 *   raceId="1"
 *   realm="Forscherliga"
 *   suffix=""
 *   titleId="0">
 *     <arenaTeams>
 *       <arenaTeam />
 *       ...
 *       <arenaTeam />
 *     </arenaTeams>
 *     <modelBasePath />
 *  </character>
 * @author mmancino
 */
@XmlType(name="characterInfo.Character")
public class Character {
    @XmlAttribute
    public String battleGroup;
    @XmlAttribute
    public String charUrl;
    @XmlAttribute(name = "class")
    public String className;
    @XmlAttribute
    public int classId;
    @XmlAttribute
    public String classUrl;
    @XmlAttribute
    public String faction;
    @XmlAttribute
    public int factionId;
    @XmlAttribute
    public String gender;
    @XmlAttribute
    public int genderId;
    @XmlAttribute
    public String guildName;
    @XmlAttribute
    public String lastModified;
    @XmlAttribute
    public int level;
    @XmlAttribute
    public String name;
    @XmlAttribute
    public int points;
    @XmlAttribute
    public String prefix;
    @XmlAttribute
    public String race;
    @XmlAttribute
    public int raceId;
    @XmlAttribute
    public String realm;
    @XmlAttribute
    public String suffix;
    @XmlAttribute
    public int titleId;
    @XmlElement(name = "arenaTeam")
    @XmlElementWrapper(name="arenaTeams")
    public List<ArenaTeam> arenaTeams;
    public ModelBasePath modelBasePath;
}