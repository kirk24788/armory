/**
 * 
 */
package de.mancino.armory.json.api.character.items;

/**
 *"stats":[{"stat":32,"amount":351,"reforgedAmount":-233}
 *,{"stat":5,"amount":881},{"stat":36,"amount":610},{"stat":7,"amount":1441},
 *{"stat":49,"amount":233,"reforged":true}]
 * @author mmancino
 */
public class Stat {
    public int stat; //XXX: Enum
    public int amount;
    public int reforgedAmount;
    public boolean reforged;
}
