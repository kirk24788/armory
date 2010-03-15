/*
 * ItemInfo.java 15.03.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.data;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;

import de.mancino.exceptions.ArmoryConnectionException;

public class ItemInfo extends XmlDataWrapper {

    public final int itemId;
    public final int overallQualityId;
    public final int bonding;
    public final int classId;
    public final int armor;
    public final int requiredLevel;
    public final int itemLevel;



    public final int bonusStrength;
    public final int bonusAgility;
    public final int bonusStamina;
    public final int bonusIntellect;
    public final int bonusSpirit;
    public final int bonusArcaneResist;
    public final int bonusFireResist;
    public final int bonusNatureResist;
    public final int bonus;
    public final int bonus;
    public final int bonus;
    public final int bonus;
    public final int bonus;
    public final int bonus;
    public final int bonus;
    public final int bonus;
    public final int bonus;
    public final int bonus;
    public final int bonus;
    public final int bonusSpellPower;
    public final int bonusCritRating;
    public final int bonusHasteRating;

    public ItemInfo(int itemId) throws ArmoryConnectionException {
        super(parseArmory(itemId));
        this.itemId = itemId;
        this.overallQualityId = getAttribute("overallQualityId");
        this.bonding = getAttribute("bonding");
        this.classId = getAttribute("classId");
        this.bonusStamina = getAttribute("bonusStamina");
        this.bonusIntellect = getAttribute("bonusIntellect");
        this.armor = getAttribute("armor");
        this.requiredLevel = getAttribute("requiredLevel");
        this.itemLevel = getAttribute("itemLevel");
        this.bonusSpellPower = getAttribute("bonusSpellPower");
        this.bonusCritRating = getAttribute("bonusCritRating");
        this.bonusHasteRating = getAttribute("bonusHasteRating");
    }

    private static Document parseArmory(int itemId) throws ArmoryConnectionException {
        try {
            HttpClient httpClient = new HttpClient();
            GetMethod armoryRequest = new GetMethod("http://eu.wowarmory.com/item-tooltip.xml?i="
                    + itemId + "&rhtml=n");

            httpClient.executeMethod(armoryRequest);
            return DocumentBuilderFactory.newInstance().newDocumentBuilder()
            .parse(armoryRequest.getResponseBodyAsStream());
        } catch (Exception e) {
            throw new ArmoryConnectionException(e);
        }
    }


    private int getAttribute(String attribName) {
        final String value = getTextContent("//itemTooltips/itemTooltip/" + attribName);
        if(StringUtils.isEmpty(value)) {
            return 0;
        } else {
            return Integer.parseInt(value);
        }

    }



    private String getCharacterAttribute(String attribName) {
        return getTextContent("//characterInfo/character/@" + attribName);
    }
}
