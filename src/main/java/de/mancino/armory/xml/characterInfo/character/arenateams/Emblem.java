package de.mancino.armory.xml.characterInfo.character.arenateams;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * Example Snippet:
    <emblem
     background="ff77d9b1"
     borderColor="ffac231a"
     borderStyle="6"
     iconColor="ff0a0b0e"
     iconStyle="40"/>
 * @author mmancino
 */
@XmlRootElement(name = "emblem")
public class Emblem {
    @XmlAttribute
    public String background;
    @XmlAttribute
    public String borderColor;
    @XmlAttribute
    public int borderStyle;
    @XmlAttribute
    public String iconColor;
    @XmlAttribute
    public int iconStyle;
}
