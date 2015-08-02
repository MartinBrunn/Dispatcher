package de.devacon.util;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by @Martin@ on 05.07.2015 22:09.
 */
public interface ContentAdapter<ID,Item> {
    public Item get(ID id);
    public Item getAt(int position);
    public String toStringAt(int position);
    public String toString(ID id);
    public int getCount();
    public HashMap<ID,Item> getMap() ;
    public ArrayList<Item> getArray() ;
}
