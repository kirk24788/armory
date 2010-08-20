/*
 * CharacterInfo.java 15.03.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.character;


import org.jdom.Document;

import de.mancino.utils.XmlDataWrapper;

public class CharacterSheet extends XmlDataWrapper {

    public final String battleGroup;

    public final String className;

    public final String charName;

    public final int classId;

    public final String factionName;

    public final int factionId;

    public final String genderName;

    public final int genderId;

    public final String guildName;

    public final String lastModified;

    public final int level;

    public final int points;

    public final String raceName;

    public final int raceId;

    public final String realm;

    public final String fullCharName;

    public final int titleId;


    public CharacterSheet(final Document xmlCharacterSheet) {
        super(xmlCharacterSheet);
        this.battleGroup = getCharacterAttribute("battleGroup");
        this.className = getCharacterAttribute("class");
        this.factionName = getCharacterAttribute("faction");
        this.charName = getCharacterAttribute("name");
        this.fullCharName = getCharacterAttribute("prefix") + charName + getCharacterAttribute("suffix");
        this.genderName = getCharacterAttribute("gender");
        this.guildName = getCharacterAttribute("guildName");
        this.lastModified = getCharacterAttribute("lastModified");
        this.raceName = getCharacterAttribute("race");
        this.realm = getCharacterAttribute("realm");
        this.classId = Integer.parseInt(getCharacterAttribute("classId"));
        this.factionId = Integer.parseInt(getCharacterAttribute("factionId"));
        this.genderId = Integer.parseInt(getCharacterAttribute("genderId"));
        this.level = Integer.parseInt(getCharacterAttribute("level"));
        this.points = Integer.parseInt(getCharacterAttribute("points"));
        this.raceId = Integer.parseInt(getCharacterAttribute("raceId"));
        this.titleId = Integer.parseInt(getCharacterAttribute("titleId"));
    }

    private String getCharacterAttribute(String attribName) {
        return getTextContent("//characterInfo/character/@" + attribName);
    }
}
