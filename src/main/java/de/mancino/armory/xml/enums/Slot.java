package de.mancino.armory.xml.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * Item Slot. E.g. head, neck...
 * 
 * @author mmancino
 */
@XmlEnum
public enum Slot {
    @XmlEnumValue(value="0")
    HEAD(0, "Head"),
    @XmlEnumValue(value="1")
    NECK(1, "Neck"),
    @XmlEnumValue(value="2")
    SHOULDER(2, "Shoulder"),
    @XmlEnumValue(value="3")
    SHIRT(3, "Shirt"),
    @XmlEnumValue(value="4")
    CHEST(4, "Chest"),
    @XmlEnumValue(value="5")
    BELT(5, "Belt"),
    @XmlEnumValue(value="6")
    LEGS(6, "Legs"),
    @XmlEnumValue(value="7")
    FEET(7, "Feet"),
    @XmlEnumValue(value="8")
    WRIST(8, "Wrist"),
    @XmlEnumValue(value="9")
    GLOVES(9, "Gloves"),
    @XmlEnumValue(value="10")
    FINGER_1(10, "Finger 1"),
    @XmlEnumValue(value="11")
    FINGER_2(11, "Finger 2"),
    @XmlEnumValue(value="12")
    TRINKET_1(12, "Trinket 1"),
    @XmlEnumValue(value="13")
    TRINKET_2(13, "Trinket 2"),
    @XmlEnumValue(value="14")
    BACK(14, "Back"),
    @XmlEnumValue(value="15")
    MAIN_HAND(15, "Main Hand"),
    @XmlEnumValue(value="16")
    OFF_HAND(16, "Off Hand"),
    @XmlEnumValue(value="17")
    RANGED(17, "Ranged"),
    @XmlEnumValue(value="18")
    TABARD(18, "Tabard");

    /**
     * numeric item slot id
     */
    public final int numericValue;

    /**
     * item slot description / real name
     */
    public final String description;

    /**
     * Item Slot.
     * 
     * @param numericValue numeric item slot id
     * @param description item slot description / real name
     */
    Slot(final int numericValue, final String description) {
        this.numericValue = numericValue;
        this.description = description;
    }

    /**
     * Returns the {@link Slot} for a given item slot id.
     * If the id doesn't exist, a {@link RuntimeException} is thrown,
     * thus be cautious!
     * 
     * @param id item slot id
     * 
     * @return item slot, whcih matches the given id
     */
    public static Slot parse(final int id) {
        for(Slot possibleQuality : Slot.values()) {
            if(possibleQuality.numericValue == id) {
                return possibleQuality;
            }
        }
        throw new RuntimeException("Error parsing slot: " + id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return description;
    }
}
