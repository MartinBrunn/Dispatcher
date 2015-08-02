package de.devacon.util;

import java.util.ArrayList;

/**
 * Created by Harald on 02.07.2015.
 */
public interface Descendable<T> {
    public boolean containsDescendables();
    boolean canDescendPath(ArrayList<T> path);
    public Descendable descend(T item);
    public Descendable descendPath(ArrayList<T> path,T item);
}
