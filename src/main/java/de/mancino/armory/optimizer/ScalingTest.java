package de.mancino.armory.optimizer;

import de.mancino.armory.optimizer.stats.CritRating;
import de.mancino.armory.optimizer.stats.HitRating;
import de.mancino.armory.optimizer.stats.Intellect;
import de.mancino.armory.optimizer.stats.MasteryRating;


public class ScalingTest {
    final static ScalingFactors AFFLI = new ScalingFactors(3.125, 2.55, 1.9814, 1.1687, 1.4297, 0.8233);
    final static ScalingFactors DEMO = new ScalingFactors(2.9689,2.37,1.9,1.27,1.43,1.67);
    final static ScalingFactors DESTRO = new ScalingFactors(2.64,2.08,2.12,0.99,1.29,0.96);
    final static ScalingFactors SCALES[] = new ScalingFactors[] {AFFLI, DEMO, DESTRO};
    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        final OptimizableItem coalwalker = ItemFactory.newItem().intellect(282).critRating(208).hasteRating(172).createAs("Coalwalker");
        final OptimizableItem signet = ItemFactory.newItem().intellect(190).hitRating(121).critRating(131).createAs("Signet of High Arcanist");
        final OptimizableItem band = ItemFactory.newItem().intellect(190).critRating(121).hasteRating(131).createAs("Band of Secret Names");
        final OptimizableItem fragment = ItemFactory.newItem().intellect(201).critRating(121).masteryRating(142).createAs("Spirit Fragment Band");
        final OptimizableItem items[] = new OptimizableItem[]{signet, band, fragment};
        System.err.println(coalwalker);
        System.err.println(coalwalker.bestReforge(AFFLI));
        for(ScalingFactors sf : SCALES) {
            for(OptimizableItem item : items) {
                final OptimizableItem best = item.bestReforge(sf);
                System.err.println(best.getValue(sf) + ": " + best);
            }
            System.err.println("****");
        }
    }
    
}
