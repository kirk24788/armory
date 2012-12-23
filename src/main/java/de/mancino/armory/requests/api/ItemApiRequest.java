package de.mancino.armory.requests.api;

import de.mancino.armory.datatypes.ArmoryBaseUri;
import de.mancino.armory.json.api.item.Item;

public class ItemApiRequest extends ArmoryApiJsonRequest<Item> {

    public ItemApiRequest(final ArmoryBaseUri armoryBaseUri, final int itemId) {
        super(armoryBaseUri, "item/" + itemId, Item.class);
    }
}
