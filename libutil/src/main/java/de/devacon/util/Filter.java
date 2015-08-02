package de.devacon.util;

/**
 * Created by @Martin@ on 04.07.2015.
 */
public interface Filter<Filterable> {
    public boolean letsPass(Filterable object);
}
