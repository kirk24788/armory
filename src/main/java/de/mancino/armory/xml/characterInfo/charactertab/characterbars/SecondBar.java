package de.mancino.armory.xml.characterInfo.charactertab.characterbars;

import javax.xml.bind.annotation.XmlAttribute;

/**
*
* Example Snippet:
        <secondBar casting="-1" effective="100" notCasting="-1" type="e"/>
* @author mmancino
*/
public class SecondBar {
    @XmlAttribute
    public int casting;
    @XmlAttribute
    public int effective;
    @XmlAttribute
    public int notCasting;
    @XmlAttribute
    public String type; // TODO: ENUM!!!
}
