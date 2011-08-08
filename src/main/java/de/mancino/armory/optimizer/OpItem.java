package de.mancino.armory.optimizer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import de.mancino.armory.optimizer.stats.Intellect;
import de.mancino.armory.optimizer.stats.Stat;


public class OpItem {
    public final String name;
    public final List<Stat> stats = new ArrayList<Stat>();
    
    public OpItem(final String name, final Stat ... stats) {
        this.name = name;
        for(final Stat stat : stats) {
            this.stats.add(stat);
        }
    }    
    public OpItem(final String name, final List<Stat> stats) {
        this.name = name;
        for(final Stat stat : stats) {
            this.stats.add(stat);
        }
    }
    
    public long getValue(final ScalingFactors scalingFactors) {
        double value=0.0;
        for(final Stat scale : scalingFactors.getAllStats()) {
            try {
                final Stat stat = getStatForScale(scale.getClass());
                value+=stat.value*scale.value;
            } catch (Exception e) {
            }
        }
        return (long)value;
    }
    
    private Stat getStatForScale(Class<? extends Stat> scaleClass) throws Exception {
        for(final Stat stat : stats) {
            if(stat.getClass().equals(scaleClass)) {
                return stat;
            }
        }
        throw new Exception();
    }
    
    public OpItem bestReforge(final ScalingFactors scalingFactors) {
        final List<OpItem> items = new ArrayList<OpItem>();
        OpItem best = this;
        items.add(this);
        for(final Stat stat : stats) {
            for(final Stat scale : scalingFactors.getStatReforgeOrder()) {
                try {
                    items.add(reforge(stat.getClass(), scale.getClass()));
                } catch (Exception e) {
                    
                }
            }
        }
        for(OpItem candidate : items) {
            if(candidate.getValue(scalingFactors) > best.getValue(scalingFactors)) {
                best = candidate;
            }
        }
        return best;
    }
    
    public OpItem reforge(Class<? extends Stat> reforgeFrom, Class<? extends Stat> reforgeTo) throws Exception {
        final String reforge = reforgeFrom.getSimpleName() + "->" + reforgeTo.getSimpleName();
        if(!canReforgeTo(reforgeTo)) {
            throw new Exception("Cant Reforge " + reforge + ": Already on Item!");
        }
        List<Stat> newStats = new ArrayList<Stat>();
        for(final Stat stat : stats) {
            if(stat.getClass().equals(reforgeFrom)) {
                if(!stat.isReforgable) {
                    throw new Exception("Cant Reforge " + reforge + ": " + stat.getClass().getSimpleName() + " is not reforgable!");
                }
                newStats.add(getStat(stat.getClass(), stat.value * 0.6));
                newStats.add(getStat(reforgeTo, stat.value * 0.4));
            } else {
                newStats.add(stat);
            }
        }
        return new OpItem(name + " " + reforge, newStats);
    }
    
    private boolean canReforgeTo(Class<? extends Stat> reforgeTo) {
        for(final Stat stat : stats) {
            if(stat.getClass().equals(reforgeTo)) {
                return false;
            }
        }
        return true;
    }

    public Stat getStat(final Class<? extends Stat> reforgeTo, double value) {
        try {
            final float constValue = (int)value;
            return (Stat) reforgeTo.getConstructors()[0].newInstance(constValue);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
    
    @Override
    public String toString() {
        return name + " (" + StringUtils.join(stats, " ,") + ")";
    }
}
