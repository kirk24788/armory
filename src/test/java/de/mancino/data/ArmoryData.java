/*
 * ArmoryData.java 15.03.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.data;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.w3c.dom.Document;

import de.mancino.exceptions.ArmoryConnectionException;

public class ArmoryData {

    private final Document xmlCharacterSheet;

    public final CharacterInfo characterInfo;

    public ArmoryData(String charName, String realmName) throws ArmoryConnectionException {
        xmlCharacterSheet = parseArmory(charName, realmName);
        characterInfo = new CharacterInfo(xmlCharacterSheet);
    }

    private static Document parseArmory(String charName, String realmName) throws ArmoryConnectionException {
        try {
            HttpClient httpClient = new HttpClient();
            GetMethod armoryRequest = new GetMethod("http://eu.wowarmory.com/character-sheet.xml?r="
                    + realmName + "&n=" + charName + "&rhtml=n");

            httpClient.executeMethod(armoryRequest);
            return DocumentBuilderFactory.newInstance().newDocumentBuilder()
            .parse(armoryRequest.getResponseBodyAsStream());
        } catch (Exception e) {
            throw new ArmoryConnectionException(e);
        }
    }


}

