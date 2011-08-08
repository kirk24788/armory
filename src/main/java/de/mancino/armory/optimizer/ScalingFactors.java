package de.mancino.armory.optimizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.mancino.armory.optimizer.stats.CritRating;
import de.mancino.armory.optimizer.stats.HasteRating;
import de.mancino.armory.optimizer.stats.HitRating;
import de.mancino.armory.optimizer.stats.Intellect;
import de.mancino.armory.optimizer.stats.MasteryRating;
import de.mancino.armory.optimizer.stats.SpellPower;
import de.mancino.armory.optimizer.stats.Stat;

public class ScalingFactors {
    public final Intellect intellect;
    public final SpellPower spellPower;
    public final HitRating hit;
    public final CritRating crit;
    public final HasteRating haste;
    public final MasteryRating mastery;
    
    public ScalingFactors(final double intellect, final double spellPower, final double hit, final double crit, final double haste, 
            final double mastery) {
        this.intellect = new Intellect(intellect);
        this.hit = new HitRating(hit);
        this.crit = new CritRating(crit);
        this.haste = new HasteRating(haste);
        this.mastery = new MasteryRating(mastery);
        this.spellPower = new SpellPower(spellPower);
    }

    public List<Stat> getStatReforgeOrder() {
        List<Stat> stats = new ArrayList<Stat>();
        stats.add(hit);
        stats.add(crit);
        stats.add(haste);
        stats.add(mastery);
        Collections.sort(stats);
        return stats;
    }
    public List<Stat> getAllStats() {
        List<Stat> stats = new ArrayList<Stat>();
        stats.add(intellect);
        stats.add(spellPower);
        stats.add(hit);
        stats.add(crit);
        stats.add(haste);
        stats.add(mastery);
        Collections.sort(stats);
        return stats;
    }
}
