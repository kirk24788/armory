package de.mancino.armory.xml.characterInfo.charactertab.glyphs;

import javax.xml.bind.annotation.XmlAttribute;

import de.mancino.armory.xml.enums.GlyphType;

/**
*
* Example Snippet:
   <glyph
    effect="If your Guardian Spirit lasts its entire duration without being triggered, the cooldown is reset to 1 min."
    icon="ui-glyph-rune-13"
    id="709"
    name="Glyph of Guardian Spirit"
    type="major"/>
* @author mmancino
*/
public class Glyph {
    @XmlAttribute
    public String effect;
    @XmlAttribute
    public String icon;
    @XmlAttribute
    public int id;
    @XmlAttribute
    public String name;
    @XmlAttribute
    public GlyphType type;
}
