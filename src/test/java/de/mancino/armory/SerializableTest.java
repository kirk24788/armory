package de.mancino.armory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.junit.Test;

import de.mancino.armory.exceptions.RequestException;
import de.mancino.armory.json.vault.AuctionFaction;

public class SerializableTest {
    @Test
    public void testSerializable() throws IOException, RequestException {
        final Armory objectUnderTest = new Armory("accountName", "password", "charName", AuctionFaction.NEUTRAL, "realmName");
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        objectUnderTest.api.getAuctions("Forscherliga");
        oos.writeObject(objectUnderTest);
    }
}
