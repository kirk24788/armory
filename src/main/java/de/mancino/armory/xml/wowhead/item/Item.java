package de.mancino.armory.xml.wowhead.item;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
<wowhead>
 <item id="54582">
  <name>
   <![CDATA[ Bracers of Fiery Night ]]>
  </name>
  <level>284</level>
  <gearScore>117.125</gearScore>
  <quality id="4">Epic</quality>
  <class id="4">
   <![CDATA[ Armor ]]>
  </class>
  <subclass id="1">
   <![CDATA[ Cloth Armor ]]>
  </subclass>
  <icon displayId="64345">inv_bracer_45</icon>
  <inventorySlot id="9">Wrist</inventorySlot>
  <htmlTooltip>
   <![CDATA[
     <table><tr><td><b class="q4">Bracers of Fiery Night</b><br /><span class="q2">Heroic</span><br /><!--bo-->Binds when picked up<table width="100%"><tr><td>Wrist</td><th>Cloth</th></tr></table><!--rf--><span>477 Armor</span><br /><span><!--stat7-->+156 Stamina</span><br /><span><!--stat5-->+101 Intellect</span><!--rs--><!--e--><br /><a href="http://www.wowhead.com/items=3&amp;filter=cr=81;crs=2;crv=0" class="socket-red q0">Red Socket</a><!--ps--><br /><!--sb--><span class="q0">Socket Bonus: +5 Spell Power</span><br />Durability 55 / 55<br />Requires Level 80<br />Item Level 284</td></tr></table><table><tr><td><!--rr--><span class="q2">Equip: Increases your critical strike rating by <!--rtg32-->65&nbsp;<small>(<!--rtg%32-->1.42%&nbsp;@&nbsp;L<!--lvl-->80)</small>.</span><br /><span class="q2">Equip: Increases your haste rating by <!--rtg36-->73&nbsp;<small>(<!--rtg%36-->2.23%&nbsp;@&nbsp;L<!--lvl-->80)</small>.</span><br />Sell Price: <span class="moneygold">5</span> <span class="moneysilver">67</span> <span class="moneycopper">6</span></td></tr></table><!--?54582:1:85:80-->
   ]]>
  </htmlTooltip>
  <json>
   <![CDATA[
    "armor":477,"classs":4,"displayid":64345,"heroic":1,"id":54582,"level":284,"name":"3Bracers of Fiery Night","reqlevel":80,"slot":9,"slotbak":9,"source":[2],"sourcemore":[{"bd":1,"dd":4,"n":"Halion","t":1,"ti":39863,"z":4987}],"subclass":1
   ]]>
  </json>
  <jsonEquip>
   <![CDATA[
    "armor":477,"critstrkrtng":65,"displayid":64345,"dura":55,"hastertng":73,"int":101,"nsockets":1,"reqlevel":80,"sellprice":56706,"slotbak":9,"socket1":2,"socketbonus":3752,"sta":156
   ]]>
  </jsonEquip>
  <link>http://www.wowhead.com/item=54582</link>
 </item>
</wowhead>
 * @author mmancino
 */
public class Item {
    @XmlAttribute
    public int id;
    @XmlElement
    public String name;
    @XmlElement
    public int level;
    @XmlElement
    public float gearScore;
    @XmlElement
    public Quality quality;
    @XmlElement(name="class")
    public ItemClass baseClass;
    @XmlElement(name="subclass")
    public ItemClass subClass;
    @XmlElement
    public String json;
    @XmlElement
    public String jsonEquip;
    @XmlElement
    public String link;
    @XmlElement
    public String icon;
}
