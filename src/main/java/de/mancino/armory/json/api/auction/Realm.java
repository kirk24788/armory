package de.mancino.armory.json.api.auction;

import java.io.Serializable;

/**
    "realm": {
        "name": "Medivh",
        "slug": "medivh"
    },
 * @author mmancino
 */
public class Realm implements Serializable {
    private static final long serialVersionUID = 2L;
    
    public String name;
    public String slug;
}
