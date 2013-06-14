package de.mancino.armory.requests.vault;

import de.mancino.armory.datatypes.ArmoryBaseUri;
import de.mancino.armory.json.vault.AuctionFaction;
import de.mancino.armory.json.vault.money.Money;

public class MoneyVaultRequest extends ArmoryVaultJsonRequest<Money> {
    public MoneyVaultRequest(final ArmoryBaseUri armoryBaseUri, final AuctionFaction auctionFaction) {
        super(armoryBaseUri, "vault/character/auction/" + auctionFaction.key + "/money", Money.class);
    }
}
