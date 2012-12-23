package de.mancino.armory.requests.vault;

import org.apache.http.message.BasicNameValuePair;

import de.mancino.armory.datatypes.ArmoryBaseUri;
import de.mancino.armory.exceptions.RequestException;
import de.mancino.armory.exceptions.ResponseParsingException;
import de.mancino.armory.json.vault.AuctionFaction;
import de.mancino.armory.json.vault.bid.Bid;
import de.mancino.armory.json.vault.money.Money;

public class AuctionBidVaultRequest extends ArmoryVaultJsonRequest<Bid> {
    public AuctionBidVaultRequest(final ArmoryBaseUri armoryBaseUri, final AuctionFaction faction,  final long auctionId, final long bid) {
        super(armoryBaseUri, "vault/character/auction/" + faction.key + "/bid", Bid.class, new BasicNameValuePair[] {
                new BasicNameValuePair("auc",  String.valueOf(auctionId)),
                new BasicNameValuePair("money",  String.valueOf(bid)),
                new BasicNameValuePair("xstoken", getCookieValue("xstoken"))});
    }

    @Override
    protected void parseResponse(byte[] responseAsBytes) throws ResponseParsingException {
        super.parseResponse(responseAsBytes);
        System.err.println(new String(responseAsBytes));
    }
    
    @Override
    public int post() throws RequestException {
        int r = super.post();
        System.err.println("RESP: " + r);
        return r;
    }
    /**
Falscher Account:

Responses:
{
  "error" : {
    "code" : 10006,
    "message" : "You must have a subscription to World of Warcraft Remote in order to buy and sell items on the Auction House.<br />To learn more about World of Warcraft Remote, <a href=\"https://www.wow-europe.com/account/wow-remote-payment-method.html?locale=en-gb\">click here</a>."
  }
}
CODE: 200

Auktion na/
{
  "error" : {
    "code" : 1004,
    "message" : "The specified auction could not found."
  }
}
CODE:200


Zu hohes Gebot (5 statt 4 buyout)
{
  "auctionFaction" : 0,
  "character" : {
    "name" : "Bæn",
    "level" : 58,
    "genderId" : 0,
    "factionId" : 0,
    "classId" : 6,
    "className" : "Death Knight",
    "raceId" : 4,
    "raceName" : "Night Elf",
    "realmName" : "Forscherliga",
    "achievementPoints" : 530,
    "side" : "ALLIANCE",
    "genderEnum" : "MALE"
  },
  "item" : {
    "auctionId" : 973906397,
    "highBidder" : true,
    "owner" : true,
    "currentBid" : 4,
    "currentBidPerUnit" : 2,
    "nextBid" : 5,
    "nextBidPerUnit" : 3,
    "buyout" : 4,
    "buyoutPerUnit" : 2,
    "timeLeft" : 2,
    "name" : "Eternium Ore",
    "guid" : 4611686030096225601,
    "id" : 23427,
    "icon" : "inv_ore_eternium",
    "quality" : 2,
    "itemKey" : 23427,
    "tooltipParams" : {
      "quantity" : 2
    }
  }
}



GEbot ohne Buyout:
{
  "auctionFaction" : 0,
  "character" : {
    "name" : "Bæn",
    "level" : 58,
    "genderId" : 0,
    "factionId" : 0,
    "classId" : 6,
    "className" : "Death Knight",
    "raceId" : 4,
    "raceName" : "Night Elf",
    "realmName" : "Forscherliga",
    "achievementPoints" : 530,
    "side" : "ALLIANCE",
    "genderEnum" : "MALE"
  },
  "item" : {
    "auctionId" : 973905935,
    "highBidder" : true,
    "owner" : false,
    "ownerName" : "Ërñsti",
    "currentBid" : 8,
    "currentBidPerUnit" : 1,
    "nextBid" : 9,
    "nextBidPerUnit" : 1,
    "buyout" : 16,
    "buyoutPerUnit" : 2,
    "timeLeft" : 2,
    "name" : "Mote of Earth",
    "id" : 22573,
    "icon" : "inv_elemental_mote_earth01",
    "quality" : 1,
    "itemKey" : 22573,
    "tooltipParams" : {
      "quantity" : 8
    }
  }
}
RESP: 200



Gebot mit Buyout:
{
  "auctionFaction" : 0,
  "character" : {
    "name" : "Bæn",
    "level" : 58,
    "genderId" : 0,
    "factionId" : 0,
    "classId" : 6,
    "className" : "Death Knight",
    "raceId" : 4,
    "raceName" : "Night Elf",
    "realmName" : "Forscherliga",
    "achievementPoints" : 530,
    "side" : "ALLIANCE",
    "genderEnum" : "MALE"
  },
  "item" : {
    "auctionId" : 973905935,
    "highBidder" : true,
    "bidderName" : "Bæn",
    "owner" : true,
    "currentBid" : 16,
    "currentBidPerUnit" : 2,
    "nextBid" : 17,
    "nextBidPerUnit" : 2,
    "buyout" : 16,
    "buyoutPerUnit" : 2,
    "timeLeft" : 2,
    "name" : "Mote of Earth",
    "guid" : 4611686030096183213,
    "id" : 22573,
    "icon" : "inv_elemental_mote_earth01",
    "quality" : 1,
    "itemKey" : 22573,
    "tooltipParams" : {
      "quantity" : 8
    }
  }
}
RESP: 200

     */
}
