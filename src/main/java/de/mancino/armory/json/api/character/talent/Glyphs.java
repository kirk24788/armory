package de.mancino.armory.json.api.character.talent;

import java.util.List;

/**
"glyphs":{
        "prime":[
          {
            "glyph":195,
            "item":41105,
            "name":"Glyph of Word of Glory",
            "icon":"inv_helmet_96"
          },
          {
            "glyph":704,
            "item":45744,
            "name":"Glyph of Shield of the Righteous",
            "icon":"ability_paladin_shieldofvengeance"
          },
          {
            "glyph":561,
            "item":43869,
            "name":"Glyph of Seal of Truth",
            "icon":"spell_holy_sealofvengeance"
          }
        ],
        "major":[
          {
            "glyph":197,
            "item":41107,
            "name":"Glyph of the Ascetic Crusader",
            "icon":"spell_holy_crusaderstrike"
          },
          {
            "glyph":559,
            "item":43867,
            "name":"Glyph of Holy Wrath",
            "icon":"spell_holy_purifyingpower"
          },
          {
            "glyph":189,
            "item":41099,
            "name":"Glyph of Consecration",
            "icon":"spell_holy_innerfire"
          }
        ],
        "minor":[
          {
            "glyph":456,
            "item":43368,
            "name":"Glyph of Truth",
            "icon":"spell_holy_sealofvengeance"
          },
          {
            "glyph":453,
            "item":43340,
            "name":"Glyph of Blessing of Might",
            "icon":"spell_holy_greaterblessingofkings"
          },
          {
            "glyph":454,
            "item":43366,
            "name":"Glyph of Insight",
            "icon":"spell_holy_healingaura"
          }
        ]
      }
 * @author mmancino
 */
public class Glyphs {
    public List<Glyph> prime;
    public List<Glyph> major;
    public List<Glyph> minor;
}
