package de.mancino.armory.requests.api;

import de.mancino.armory.datatypes.ArmoryBaseUri;
import de.mancino.armory.json.api.character.Character;
import de.mancino.armory.json.api.character.CharacterFields;
import de.mancino.armory.json.api.guild.Guild;
import de.mancino.armory.json.api.guild.GuildFields;

public class GuildApiRequest extends ArmoryApiJsonRequest<Guild> {

    public GuildApiRequest(final String realmName, final String guildName, final boolean allFields) {
        this(new ArmoryBaseUri(), realmName, guildName, allFields);
    }
    
    public GuildApiRequest(final String realmName, final String guildName, final GuildFields ... fields) {
        this(new ArmoryBaseUri(), realmName, guildName, fields);
    }

    public GuildApiRequest(final ArmoryBaseUri armoryBaseUri, final String realmName, final String guildName,
            final boolean allFields) {
        this(new ArmoryBaseUri(), realmName, guildName, allFields ? GuildFields.values() : new GuildFields[]{});
    }
    public GuildApiRequest(final ArmoryBaseUri armoryBaseUri, final String realmName, final String guildName,
            final GuildFields ... fields) {
        super(armoryBaseUri, buildRequestPath(realmName, guildName, fields), Guild.class);
    }

    private static String buildRequestPath(final String realmName, final String guildName, final GuildFields ... fields) {
        String path = "guild/" + urlEncode(realmName) + "/" + urlEncode(guildName);
        if(fields.length > 0) {
            path += "?fields=" + fields[0].key;
            for(int pos = 1; pos < fields.length ; pos++) {
                path+=","+fields[pos].key;
            }
        }
        return path;
    }

}
