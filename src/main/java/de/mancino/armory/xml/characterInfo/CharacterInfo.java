package de.mancino.armory.xml.characterInfo;

import javax.xml.bind.annotation.XmlRootElement;

import de.mancino.armory.xml.characterInfo.character.Character;
import de.mancino.armory.xml.characterInfo.charactertab.CharacterTab;

/**
 *
 * Example Snippet:
 *  <characterInfo>
 *    <character />
 *    <characterTab />
 *    <summary />
 *  </characterInfo>
 * @author mmancino
 */
@XmlRootElement(name = "characterInfo")
public class CharacterInfo {
    public Character character;
    public CharacterTab characterTab;

}
