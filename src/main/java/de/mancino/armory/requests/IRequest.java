package de.mancino.armory.requests;

import de.mancino.armory.exceptions.RequestException;

public interface IRequest {
    /**
     * Execute POST Request.
     *
     * @return HTTP Status Code
     *
     * @throws RequestException Error during POST Request.
     */
    public int post() throws RequestException;

    /**
     * Execute GET Request.
     *
     * @return HTTP Status Code
     *
     * @throws RequestException Error during GET Request.
     */
    public int get() throws RequestException;
}
