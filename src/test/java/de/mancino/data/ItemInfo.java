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
import de.mancino.utils.XmlDataWrapper;

public class ItemInfo extends XmlDataWrapper {

    public final int itemId;
    public final int overallQualityId;
    public final int bonding;
    public final int classId;
    public final int armor;
    public final int requiredLevel;
    public final int itemLevel;

/*

    public final int bonusStrength;
    public final int bonusAgility;
    public final int bonusStamina;
    public final int bonusIntellect;
    public final int bonusSpirit;
    public final int bonusArcaneResist;
    public final int bonusFireResist;
    public final int bonusNatureResist;
    public final int bonusFrostResist;
    public final int bonusShadowResist;
    public final int bonusDefenseSkillRating;
    public final int bonusExpertise;
    public final int bonusBlock;
    public final int bonusBlockValue;
    public final int bonusDodge;
    public final int bonusParry;
    public final int bonusResilience;
    public final int bonusArmorPen;
    public final int bonusAttackPower;
    public final int bonusCritRating;
    public final int bonusRangedCrit;
    public final int bonusToHit;
    public final int bonusRangedToHit;
    public final int bonusHasteRating;
    public final int bonusDamageUndead;
    public final int bonusArcaneDamage;
    public final int bonusFireDamage;
    public final int bonusFrostDamage;
    public final int bonusHolyDamage;
    public final int bonusNatureDamage;
    public final int bonusShadowDamage;
    public final int bonusSpellPenetration;
    public final int bonusSpellPower;
    public final int bonusHealthRegen;
    public final int bonusManaRegen;*/

    public ItemInfo(int itemId) throws ArmoryConnectionException {
        super(parseArmory(itemId));
        this.itemId = itemId;
        this.overallQualityId = getAttribute("overallQualityId");
        this.bonding = getAttribute("bonding");
        this.classId = getAttribute("classId");

        this.armor = getAttribute("armor");
        this.requiredLevel = getAttribute("requiredLevel");
        this.itemLevel = getAttribute("itemLevel");
/*
        this.bonusStrength = getAttribute("bonusStrength");
        this.bonusAgility = getAttribute("bonusAgility");
        this.bonusStamina = getAttribute("bonusStamina");
        this.bonusIntellect = getAttribute("bonusIntellect");
        this.bonusSpirit = getAttribute("bonusSpirit");
        this.bonusFireResist = getAttribute("arcaneResist");

        this.bonus = getAttribute("fireResist");
        this.bonus = getAttribute("natureResist");
        this.bonus = getAttribute("frostResist");
        this.bonus = getAttribute("shadowResist");
        this.bonusDefenseSkillRating = getAttribute("bonusDefenseSkillRating");
        this.bonus = getAttribute("bonusExpertiseRating");
        this.bonus = getAttribute("bonusBlockRating");
        this.bonus = getAttribute("bonusBlockValue");
        this.bonus = getAttribute("bonusDodgeRating");
        this.bonus = getAttribute("bonusParryRating");
        this.bonus = getAttribute("bonusResilienceRating");
        this.bonus = getAttribute("bonusArmorPenetration");
        this.bonus = getAttribute("bonusAttackPower");
        this.bonusCritRating = getAttribute("bonusCritRating");
        this.bonus = getAttribute("");
        this.bonus = getAttribute("");
        this.bonus = getAttribute("");
        this.bonus = getAttribute("");
        this.bonus = getAttribute("");

        this.bonusSpellPower = getAttribute("bonusSpellPower");
        this.bonusHasteRating = getAttribute("bonusHasteRating");*/
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
