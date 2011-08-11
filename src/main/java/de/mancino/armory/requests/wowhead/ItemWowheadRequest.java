package de.mancino.armory.requests.wowhead;

public class ItemWowheadRequest extends WowheadRequest {
    public ItemWowheadRequest(final int itemId) {
        super("http://wowhead.com/item=" + itemId +"&xml");
    }
}
