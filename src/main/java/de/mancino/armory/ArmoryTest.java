/*
 * ArmoryTest.java 16.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory;

import org.apache.commons.codec.binary.Base64;

import de.mancino.armory.xml.Page;
import de.mancino.armory.xml.itemtooltips.setdata.Item;
import de.mancino.armory.xml.itemtooltips.setdata.SetBonus;
import de.mancino.exceptions.ArmoryConnectionException;

public class ArmoryTest  extends Armory {


    public ArmoryTest(String accountName, String password)
            throws ArmoryConnectionException {
        super(accountName, password);
        // TODO Auto-generated constructor stub
    }

   public static void main(String[] args) {
       try {
        final ArmoryTest aTest = new ArmoryTest(login, passwd);
/*
        Page chev = aTest.executeRestQuery("character-sheet.xml?r=" + realmName + "&n=" + URLEncoder.encode(charName));
        System.err.println(chev.characterInfo.characterTab.pvp.arenacurrency.value);
        System.err.println(chev.characterInfo.characterTab.pvp.lifetimehonorablekills.value);

                <item displayInfoId="64160" durability="60" gem0Id="41401" gem1Id="40123" gem2Id="0" gemIcon0="inv_jewelcrafting_shadowspirit_02" gemIcon1="inv_jewelcrafting_gem_38" icon="inv_helmet_156" id="50006" level="264" maxDurability="60" name="Corp'rethar Ceremonial Crown" permanentEnchantIcon="spell_fire_masterofelements" permanentEnchantItemId="44877" permanentenchant="3820" pickUp="PickUpCloth_Leather01" putDown="PutDownCloth_Leather01" randomPropertiesId="0" rarity="4" seed="1009185856" slot="0"/>
        <item displayInfoId="64194" durability="0" gem0Id="40113" gem1Id="0" gem2Id="0" gemIcon0="inv_jewelcrafting_gem_37" icon="inv_jewelry_necklace_53" id="50609" level="277" maxDurability="0" name="Bone Sentinel's Amulet" permanentenchant="0" pickUp="PickUpRing" putDown="PutDownRing" randomPropertiesId="0" rarity="4" seed="1847542016" slot="1"/>
        <item displayInfoId="64163" durability="60" gem0Id="40123" gem1Id="0" gem2Id="0" gemIcon0="inv_jewelcrafting_gem_38" icon="inv_shoulder_119" id="51175" level="264" maxDurability="60" name="Sanctified Crimson Acolyte Shoulderpads" permanentEnchantIcon="spell_nature_lightningoverload" permanentEnchantItemId="44874" permanentenchant="3810" pickUp="PickUpCloth_Leather01" putDown="PutDownCloth_Leather01" randomPropertiesId="0" rarity="4" seed="0" slot="2"/>
        <item displayInfoId="64162" durability="100" gem0Id="40123" gem1Id="40123" gem2Id="40123" gemIcon0="inv_jewelcrafting_gem_38" gemIcon1="inv_jewelcrafting_gem_38" gemIcon2="inv_jewelcrafting_gem_38" icon="inv_chest_cloth_80" id="50172" level="264" maxDurability="100" name="Sanguine Silk Robes" permanentEnchantIcon="inv_scroll_03" permanentEnchantItemId="44465" permanentenchant="3832" pickUp="PickUpCloth_Leather01" putDown="PutDownCloth_Leather01" randomPropertiesId="0" rarity="4" seed="0" slot="4"/>
        <item displayInfoId="64208" durability="35" gem0Id="40123" gem1Id="40123" gem2Id="40123" gemIcon0="inv_jewelcrafting_gem_38" gemIcon1="inv_jewelcrafting_gem_38" gemIcon2="inv_jewelcrafting_gem_38" icon="inv_belt_68" id="50997" level="264" maxDurability="35" name="Circle of Ossus" permanentenchant="0" pickUp="PickUpCloth_Leather01" putDown="PutDownCloth_Leather01" randomPropertiesId="0" rarity="4" seed="0" slot="5"/>
        <item displayInfoId="64212" durability="75" gem0Id="40123" gem1Id="40123" gem2Id="0" gemIcon0="inv_jewelcrafting_gem_38" gemIcon1="inv_jewelcrafting_gem_38" icon="inv_pants_cloth_35" id="51177" level="264" maxDurability="75" name="Sanctified Crimson Acolyte Leggings" permanentEnchantIcon="spell_nature_astralrecalgroup" permanentEnchantSpellDesc="Permanently embroiders your pants with sanctified spellthread, increasing spell power by 50 and Spirit by 20.&#10;&#10;&#10;&#10;Only the tailor's pants can be embroidered, and doing so will cause them to become soulbound." permanentEnchantSpellName="Sanctified Spellthread" permanentenchant="3872" pickUp="PickUpCloth_Leather01" putDown="PutDownCloth_Leather01" randomPropertiesId="0" rarity="4" seed="0" slot="6"/>
        <item displayInfoId="65408" durability="50" gem0Id="40123" gem1Id="40123" gem2Id="0" gemIcon0="inv_jewelcrafting_gem_38" gemIcon1="inv_jewelcrafting_gem_38" icon="inv_boots_cloth_26" id="49893" level="264" maxDurability="50" name="Sandals of Consecration" permanentEnchantIcon="inv_scroll_03" permanentEnchantItemId="39006" permanentenchant="3232" pickUp="PickUpCloth_Leather01" putDown="PutDownCloth_Leather01" randomPropertiesId="0" rarity="4" seed="1792845440" slot="7"/>
        <item displayInfoId="64165" durability="35" gem0Id="40123" gem1Id="0" gem2Id="0" gemIcon0="inv_jewelcrafting_gem_38" icon="inv_bracer_47" id="50032" level="264" maxDurability="35" name="Death Surgeon's Sleeves" permanentEnchantIcon="inv_scroll_03" permanentEnchantItemId="44470" permanentenchant="2332" pickUp="PickUpCloth_Leather01" putDown="PutDownCloth_Leather01" randomPropertiesId="0" rarity="4" seed="0" slot="8"/>
        <item displayInfoId="64214" durability="35" gem0Id="40123" gem1Id="49110" gem2Id="0" gemIcon0="inv_jewelcrafting_gem_38" gemIcon1="inv_misc_gem_pearl_12" icon="inv_gauntlets_92" id="50176" level="264" maxDurability="35" name="San'layn Ritualist Gloves" permanentEnchantIcon="inv_scroll_03" permanentEnchantItemId="38979" permanentenchant="3246" pickUp="PickUpCloth_Leather01" putDown="PutDownCloth_Leather01" randomPropertiesId="0" rarity="4" seed="1949371648" slot="9"/>
        <item displayInfoId="33864" durability="0" gem0Id="40123" gem1Id="0" gem2Id="0" gemIcon0="inv_jewelcrafting_gem_38" icon="inv_jewelry_ring_ahnqiraj_05" id="49990" level="264" maxDurability="0" name="Ring of Maddening Whispers" permanentEnchantIcon="trade_engraving" permanentEnchantSpellDesc="Permanently enchant a ring to increase spell power by 23.  Only the Enchanter's rings can be enchanted, and enchanting a ring will cause it to become soulbound." permanentEnchantSpellName="Enchant Ring - Greater Spellpower" permanentenchant="3840" pickUp="PickUpRing" putDown="PutDownRing" randomPropertiesId="0" rarity="4" seed="2067519616" slot="10"/>
        <item displayInfoId="31498" durability="0" gem0Id="40123" gem1Id="0" gem2Id="0" gemIcon0="inv_jewelcrafting_gem_38" icon="inv_jewelry_ring_40" id="50424" level="264" maxDurability="0" name="Memory of Malygos" permanentEnchantIcon="trade_engraving" permanentEnchantSpellDesc="Permanently enchant a ring to increase spell power by 23.  Only the Enchanter's rings can be enchanted, and enchanting a ring will cause it to become soulbound." permanentEnchantSpellName="Enchant Ring - Greater Spellpower" permanentenchant="3840" pickUp="PickUpRing" putDown="PutDownRing" randomPropertiesId="0" rarity="4" seed="1482656384" slot="11"/>
        <item displayInfoId="59319" durability="0" gem0Id="0" gem1Id="0" gem2Id="0" icon="achievement_dungeon_ulduar77_25man" id="47041" level="245" maxDurability="0" name="Solace of the Defeated" permanentenchant="0" pickUp="PickUpWand" putDown="PutDownWand" randomPropertiesId="0" rarity="4" seed="997239680" slot="12"/>
        <item displayInfoId="64236" durability="0" gem0Id="0" gem1Id="0" gem2Id="0" icon="inv_jewelry_trinket_02" id="50366" level="277" maxDurability="0" name="Althor's Abacus" permanentenchant="0" pickUp="PickUpWand" putDown="PutDownWand" randomPropertiesId="0" rarity="4" seed="1217006432" slot="13"/>
        <item displayInfoId="64300" durability="0" gem0Id="40113" gem1Id="0" gem2Id="0" gemIcon0="inv_jewelcrafting_gem_37" icon="item_icecrowncape" id="50668" level="277" maxDurability="0" name="Greatcloak of the Turned Champion" permanentEnchantIcon="inv_misc_thread_01" permanentEnchantSpellDesc="Embroiders a magical pattern into your cloak, giving you a chance to restore 400 mana when you cast a spell.&#10;&#10;&#10;&#10;You can only embroider your own cloak and embroidering your cloak will cause it to become soulbound.  Requires 400 Tailoring to use." permanentEnchantSpellName="Darkglow Embroidery" permanentenchant="3728" pickUp="PickUpCloth_Leather01" putDown="PutDownCloth_Leather01" randomPropertiesId="0" rarity="4" seed="662051168" slot="14"/>
        <item displayInfoId="61655" durability="125" gem0Id="0" gem1Id="0" gem2Id="0" icon="inv_mace_99" id="46017" level="245" maxDurability="125" name="Val'anyr, Hammer of Ancient Kings" permanentEnchantIcon="inv_scroll_03" permanentEnchantItemId="44467" permanentenchant="3834" pickUp="PickUpMetalSmall" putDown="PutDownSmallMEtal" randomPropertiesId="0" rarity="5" seed="1120315776" slot="15"/>
        <item displayInfoId="64433" durability="0" gem0Id="0" gem1Id="0" gem2Id="0" icon="inv_misc_bone_elfskull_01" id="50781" level="251" maxDurability="0" name="Scourgelord's Baton" permanentenchant="0" pickUp="PickUpWand" putDown="PutDownWand" randomPropertiesId="0" rarity="4" seed="1363831808" slot="16"/>
        <item displayInfoId="64360" durability="75" gem0Id="0" gem1Id="0" gem2Id="0" icon="inv_wand_34" id="50472" level="264" maxDurability="75" name="Nightmare Ender" permanentenchant="0" pickUp="PickUpWand" putDown="PutDownWand" randomPropertiesId="0" rarity="4" seed="1293831424" slot="17"/>

*/

        final int itemId = 51177;
        Page page = aTest.executeRestQuery("item-tooltip.xml?i=" + itemId);
        System.err.println(page.itemTooltips.get(0).armor.armor);
        System.err.println(page.itemTooltips.get(0).armor.armorBonus);
        for(SetBonus sb : page.itemTooltips.get(0).setData.setBonuses) {
            System.err.println(sb.threshold + ": " + sb.desc );
        }
        for(Item i : page.itemTooltips.get(0).setData.items) {
            System.err.println(i.name );
        }
    } catch (ArmoryConnectionException e) {
        e.printStackTrace();
    }


   }











   final static String login = "mario@mancino-net.de";
   final static String passwd = new String(Base64.decodeBase64("bmtnc2VxcGd5N2E="));
   final static String charName = "Asira";//"Chevron";//"Hélios";
   final static String realmName = "Forscherliga";
}
