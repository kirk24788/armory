/*
 * Test.java 15.03.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino;

import de.mancino.data.ArmoryData;
import de.mancino.data.ItemInfo;
import de.mancino.exceptions.ArmoryConnectionException;

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            ArmoryData chev = new ArmoryData("Chevron", "Forscherliga");
            System.err.println(chev.characterInfo.fullCharName);
            ItemInfo itm = new ItemInfo(50983);
            System.err.println(itm.armor);
        } catch (ArmoryConnectionException e) {
            e.printStackTrace();
        }
    }

}
