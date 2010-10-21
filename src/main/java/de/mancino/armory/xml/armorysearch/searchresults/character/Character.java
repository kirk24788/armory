package de.mancino.armory.xml.armorysearch.searchresults.character;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
*
* Example Snippet:
*  <character
*   battleGroup="Ruin"
*   battleGroupId="3"
*   class="Priest"
*   classId="5"
*   faction="Horde"
*   factionId="1"
*   gender="Male"
*   genderId="0"
*   guild="Need More Rage"
*   guildId="13882882"
*   lastLoginDate="2010-07-13 02:28:07.0"
*   level="80"
*   name="Hélios"
*   race="Blood Elf"
*   raceId="10"
*   realm="Warsong"
*   relevance="52"
*   searchRank="1"
*   url="r=Warsong&amp;cn=H%C3%A9lios"/>

* @author mmancino
*/
@XmlType(name="armorysearch.searchresults.Character")
public class Character {
    @XmlAttribute
    public String battleGroup;
    @XmlAttribute
    public int battleGroupId;
    @XmlAttribute(name = "class")
    public String className;
    @XmlAttribute
    public int classId;
    @XmlAttribute
    public String faction;
    @XmlAttribute
    public int factionId;
    @XmlAttribute
    public String gender;
    @XmlAttribute
    public int genderId;
    @XmlAttribute
    public String guild;
    @XmlAttribute
    public int guildId;
    @XmlAttribute
    public String lastLoginDate;
    @XmlAttribute
    public int level;
    @XmlAttribute
    public String name;
    @XmlAttribute
    public String race;
    @XmlAttribute
    public int raceId;
    @XmlAttribute
    public String realm;
    @XmlAttribute
    public int relevance;
    @XmlAttribute
    public int searchRank;
    @XmlAttribute
    public String url;
}
