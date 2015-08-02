package de.devacon.util;

/**
 * Created by Harald on 02.07.2015.
 */
public interface ItemLookup<T> {
    T getItem(T key) ;
    public boolean hasItem(T key);
}
