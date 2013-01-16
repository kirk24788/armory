package de.mancino.armory.json.api.character.talent;

import java.util.List;


/**
{
  "talents":[
    {
      "selected":true,
      "name":"Protection",
      "icon":"ability_paladin_shieldofthetemplar",
      "build":"000000000000000000003222300312110112123103203200000000000000",
      "trees":[
        {
          "points":"00000000000000000000",
          "total":0
        },
        {
          "points":"32223003121101121231",
          "total":31
        },
        {
          "points":"03203200000000000000",
          "total":10
        }
      ],
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
    },
    {...}
  ]
}

 * @author mmancino
 *
 */
public class Talents {
    public boolean selected;
    public List<Talent> talents;
    public Glyphs glyphs;
    public Spec spec;
    public String calcTalent;
    public String calcSpec;
    public String calcGlyph;
}
