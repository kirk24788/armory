package de.mancino.armory.optimizer;

import java.util.ArrayList;
import java.util.List;

import de.mancino.armory.optimizer.stats.CritRating;
import de.mancino.armory.optimizer.stats.HasteRating;
import de.mancino.armory.optimizer.stats.HitRating;
import de.mancino.armory.optimizer.stats.Intellect;
import de.mancino.armory.optimizer.stats.MasteryRating;
import de.mancino.armory.optimizer.stats.SpellPower;
import de.mancino.armory.optimizer.stats.Stat;

public class ItemFactory {
    private List<Stat> stats = new ArrayList<Stat>(); 
    public static ItemFactory newItem() {
        return new ItemFactory();
    }
    public OptimizableItem createAs(final String name) {
        return new OptimizableItem(name, stats);
    }
    
    public ItemFactory critRating(double value) {
        stats.add(new CritRating(value));
        return this;
    }
    
    public ItemFactory spellPower(double value) {
        stats.add(new SpellPower(value));
        return this;
    }
    
    public ItemFactory hasteRating(double value) {
        stats.add(new HasteRating(value));
        return this;
    }
    
    public ItemFactory hitRating(double value) {
        stats.add(new HitRating(value));
        return this;
    }
    
    public ItemFactory intellect(double value) {
        stats.add(new Intellect(value));
        return this;
    }
    
    public ItemFactory masteryRating(double value) {
        stats.add(new MasteryRating(value));
        return this;
    }
}
