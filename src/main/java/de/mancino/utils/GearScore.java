package de.mancino.utils;


public class GearScore {
    public static int calculateGearScore() {
        int gearScore = 0;
        double titanGrip = 1.0;
        
        // TODO: 2H Check?
        
        return 0;
    }
    
   
    /*
function GearScore_GetScore(Name, Target)
    if ( UnitIsPlayer(Target) ) then
        local PlayerClass, PlayerEnglishClass = UnitClass(Target);
        local GearScore = 0; local PVPScore = 0; local ItemCount = 0; local LevelTotal = 0; local TitanGrip = 1; local TempEquip = {}; local TempPVPScore = 0

        if ( GetInventoryItemLink(Target, 16) ) and ( GetInventoryItemLink(Target, 17) ) then
            local ItemName, ItemLink, ItemRarity, ItemLevel, ItemMinLevel, ItemType, ItemSubType, ItemStackCount, ItemEquipLoc, ItemTexture = GetItemInfo(GetInventoryItemLink(Target, 16))
            local TitanGripGuess = 0
            if ( ItemEquipLoc == "INVTYPE_2HWEAPON" ) then TitanGrip = 0.5; end
        end

        if ( GetInventoryItemLink(Target, 17) ) then
            local ItemName, ItemLink, ItemRarity, ItemLevel, ItemMinLevel, ItemType, ItemSubType, ItemStackCount, ItemEquipLoc, ItemTexture = GetItemInfo(GetInventoryItemLink(Target, 17))
            if ( ItemEquipLoc == "INVTYPE_2HWEAPON" ) then TitanGrip = 0.5; end
            TempScore, ItemLevel = GearScore_GetItemScore(GetInventoryItemLink(Target, 17));
            if ( PlayerEnglishClass == "HUNTER" ) then TempScore = TempScore * 0.3164; end
            GearScore = GearScore + TempScore * TitanGrip;  ItemCount = ItemCount + 1; LevelTotal = LevelTotal + ItemLevel
            TempEquip[17] = GearScore_GetItemCode(ItemLink)
        else
            TempEquip[17] = "0:0"
        end
        
        for i = 1, 18 do

            if ( i ~= 4 ) and ( i ~= 17 ) then
                ItemLink = GetInventoryItemLink(Target, i)
                if ( ItemLink ) then
                    local ItemName, ItemLink, ItemRarity, ItemLevel, ItemMinLevel, ItemType, ItemSubType, ItemStackCount, ItemEquipLoc, ItemTexture = GetItemInfo(ItemLink)
                    TempScore, ItemLevel, a, b, c, d, TempPVPScore = GearScore_GetItemScore(ItemLink);
                    if ( i == 16 ) and ( PlayerEnglishClass == "HUNTER" ) then TempScore = TempScore * 0.3164; end
                    if ( i == 18 ) and ( PlayerEnglishClass == "HUNTER" ) then TempScore = TempScore * 5.3224; end
                    if ( i == 16 ) then TempScore = TempScore * TitanGrip; end
                    GearScore = GearScore + TempScore;  ItemCount = ItemCount + 1; LevelTotal = LevelTotal + ItemLevel
                    --PVPScore = PVPScore + TempPVPScore
                    TempEquip[i] = GearScore_GetItemCode(ItemLink)
                else
                    TempEquip[i] = "0:0"
                end
            end;
        end
        if ( GearScore <= 0 ) and ( Name ~= UnitName("player") ) then
            GearScore = 0; return;
        elseif ( Name == UnitName("player") ) and ( GearScore <= 0 ) then
            GearScore = 0; end
        
        --if ( GearScore < 0 ) and ( PVPScore < 0 ) then return 0, 0; end
        --if ( PVPScore < 0 ) then PVPScore = 0; end
        --print(GearScore, PVPScore)
        local __, RaceEnglish = UnitRace(Target);
        local __, ClassEnglish = UnitClass(Target);
        local currentzone = GetZoneText()
        if not ( GS_Zones[currentzone] ) then 
            --print("Alert! You have found a zone unknown to GearScore. Please report the zone '"..GetZoneText().." at gearscore.blogspot.com Thanks!"); 
            currentzone = "Unknown Location"
        end
        local GuildName = GetGuildInfo(Target); if not ( GuildName ) then GuildName = "*"; else GuildName = GuildName; end
        GS_Data[GetRealmName()].Players[Name] = { ["Name"] = Name, ["GearScore"] = floor(GearScore), ["PVP"] = 1, ["Level"] = UnitLevel(Target), ["Faction"] = GS_Factions[UnitFactionGroup(Target)], ["Sex"] = UnitSex(Target), ["Guild"] = GuildName,
        ["Race"] = GS_Races[RaceEnglish], ["Class"] =  GS_Classes[ClassEnglish], ["Spec"] = 1, ["Location"] = GS_Zones[currentzone], ["Scanned"] = UnitName("player"), ["Date"] = GearScore_GetTimeStamp(), ["Average"] = floor((LevelTotal / ItemCount)+0.5), ["Equip"] = TempEquip}
    end
end
     */

    public static int calculateItemScore() {
        double qualityScale = 1.0;
        double gearScore = 0.0;
        
        return 0;
    }
    /*
    
    local QualityScale = 1; local PVPScale = 1; local PVPScore = 0; local GearScore = 0
    if not ( ItemLink ) then return 0, 0; end
    local ItemName, ItemLink, ItemRarity, ItemLevel, ItemMinLevel, ItemType, ItemSubType, ItemStackCount, ItemEquipLoc, ItemTexture = GetItemInfo(ItemLink); local Table = {}; local Scale = 1.8618
    if ( ItemRarity == 5 ) then QualityScale = 1.3; ItemRarity = 4;
    elseif ( ItemRarity == 1 ) then QualityScale = 0.005;  ItemRarity = 2
    elseif ( ItemRarity == 0 ) then QualityScale = 0.005;  ItemRarity = 2 end
    if ( ItemRarity == 7 ) then ItemRarity = 3; ItemLevel = 187.05; end
    local TokenLink, TokenNumber = GearScore_GetItemCode(ItemLink)
    if ( GS_Tokens[TokenNumber] ) then return GS_Tokens[TokenNumber].ItemScore, GS_Tokens[TokenNumber].ItemLevel, GS_Tokens[TokenNumber].ItemSlot; end
    if ( GS_ItemTypes[ItemEquipLoc] ) then
        if ( ItemLevel > 120 ) then Table = GS_Formula["A"]; else Table = GS_Formula["B"]; end
        if ( ItemRarity >= 2 ) and ( ItemRarity <= 4 )then
            local Red, Green, Blue = GearScore_GetQuality((floor(((ItemLevel - Table[ItemRarity].A) / Table[ItemRarity].B) * 1 * Scale)) * 12.25 )
            GearScore = floor(((ItemLevel - Table[ItemRarity].A) / Table[ItemRarity].B) * GS_ItemTypes[ItemEquipLoc].SlotMOD * Scale * QualityScale)
            if ( ItemLevel == 187.05 ) then ItemLevel = 0; end
            if ( GearScore < 0 ) then GearScore = 0;   Red, Green, Blue = GearScore_GetQuality(1); end
            GearScoreTooltip:SetOwner(GS_Frame1, "ANCHOR_Right")
            if ( PVPScale == 0.75 ) then PVPScore = 1; GearScore = GearScore * 1; 
            else PVPScore = GearScore * 0; end
            GearScore = floor(GearScore)
            PVPScore = floor(PVPScore)
            return GearScore, ItemLevel, GS_ItemTypes[ItemEquipLoc].ItemSlot, Red, Green, Blue, PVPScore, ItemEquipLoc;
        end
    end
    return -1, ItemLevel, 50, 1, 1, 1, PVPScore, ItemEquipLoc
end*/
}
