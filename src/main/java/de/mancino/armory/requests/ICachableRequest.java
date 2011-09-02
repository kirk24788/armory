package de.mancino.armory.requests;


public interface ICachableRequest<T> extends IRequest {

    /**
     * @return the cached object
     */
    public T getObject();

    /**
     * @return tamestamp of cached data.
     */
    public long getObjectTimestamp();
}
