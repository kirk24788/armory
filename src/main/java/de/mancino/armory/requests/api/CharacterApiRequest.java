package de.mancino.armory.requests.api;

import de.mancino.armory.ArmoryBaseUri;
import de.mancino.armory.json.api.character.Character;
import de.mancino.armory.json.api.character.CharacterFields;

public class CharacterApiRequest extends ArmoryApiJsonRequest<Character> {

    public CharacterApiRequest(final String realmName, final String charName, final boolean allFields) {
        this(new ArmoryBaseUri(), realmName, charName, allFields);
    }
    
    public CharacterApiRequest(final String realmName, final String charName, final CharacterFields ... fields) {
        this(new ArmoryBaseUri(), realmName, charName, fields);
    }

    public CharacterApiRequest(final ArmoryBaseUri armoryBaseUri, final String realmName, final String charName,
            final boolean allFields) {
        this(new ArmoryBaseUri(), realmName, charName, allFields ? CharacterFields.values() : new CharacterFields[]{});
    }
    public CharacterApiRequest(final ArmoryBaseUri armoryBaseUri, final String realmName, final String charName,
            final CharacterFields ... fields) {
        super(armoryBaseUri, buildRequestPath(realmName, charName, fields), Character.class);
    }

    private static String buildRequestPath(final String realmName, final String charName, final CharacterFields ... fields) {
        String path = "character/" + urlEncode(realmName) + "/" + urlEncode(charName);
        if(fields.length > 0) {
            path += "?fields=" + fields[0].key;
            for(int pos = 1; pos < fields.length ; pos++) {
                path+=","+fields[pos].key;
            }
        }
        return path;
    }

}
