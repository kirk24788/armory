/*
 * XmlTest.java 16.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import de.mancino.armory.xml.Page;
import de.mancino.armory.xml.armorysearch.searchresults.character.Character;

public class XmlTest {

    /**
     * @param args
     */
    public static void main(String[] args) {

        ClientRequest request = new ClientRequest("http://eu.wowarmory.com/search.xml?searchQuery=H%C3%A9lios&searchType=all&rhtml=n");
        request.accept(MediaType.APPLICATION_XML);

        try {
            ClientResponse<String> sresponse = request.get(String.class);
            System.err.println(sresponse.getEntity());


            ClientResponse<Page> response = request.get(Page.class);
            System.err.println(response.getResponseStatus());
            Page page = response.getEntity();
            System.err.println(page.lang);
            for(Character character : page.armorySearch.searchResults.characters) {
                System.err.println(character.name + "@" + character.realm + ": " + character.level);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } //get response and automatically unmarshall to a string.
    }

}
