package de.mancino.armory.optimizer.stats;

public abstract class Stat implements Comparable<Stat>{
    public final boolean isReforgable;
    public final double value;
    
    public Stat(final boolean isReforgable, final double value) {
        this.value = value;
        this.isReforgable = isReforgable;
    }

    /**
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Stat o) {
        if(o.value==value) {
            return 0;
        } else if(value < o.value) {
            return -1;
        } else {
            return 1;
        }
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + value;
    }
}
