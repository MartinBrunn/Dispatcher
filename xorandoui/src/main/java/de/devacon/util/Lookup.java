package de.devacon.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Harald on 02.07.2015.
 */
public class Lookup<T> implements Descendable<T> , ItemLookup<T> {
    protected HashMap<T,T> table = null;
    protected HashMap<T,Lookup<T>> map = null;
    public Lookup(){
        table = new HashMap<>();
        map = new HashMap<>();
    }
    public void addDescendant(T key,Lookup<T> newChild){
        map.put(key,newChild);
    }
    public void addValue(T key,T value){
        table.put(key, value);
    }
    public List<T> getValueKeys(){
        ArrayList<T> list = new ArrayList<T>();

        list.addAll(table.keySet());
        return list;
    }
    public List<T> getDescendableKeys(){
        ArrayList<T> list = new ArrayList<T>();
        list.addAll(map.keySet());
        return list;
    }
    @Override
    public boolean hasItem(T key){
        return table.containsKey(key);
    }
    @Override
    public T getItem(T key) {
        return table.get(key);
    }
    @Override
    public boolean containsDescendables(){
        return map.size() > 0 ;
    }
    public boolean canDescendPath(ArrayList<T> path){
        if(path == null )
            return false;
        if(path.size() == 0)
            return containsDescendables();
        Descendable desc = descend(path.get(0));
        if(desc == null)
            return false;
        if(path.size() == 1) {
            return true;
        }
        ArrayList<T> rest = new ArrayList<>(path);

        rest.remove(0);
        return desc.canDescendPath(rest);
    }
    public Descendable descend(T item){
        Object obj = map.get(item);
        if(obj instanceof Descendable) {
            return (Descendable) obj;
        }
        return null;
    }
    public Descendable descendPath(ArrayList<T> path,T item){
        if(path == null || path.size() == 0) {
            if(item == null){
                return this;
            }
            return descend(item);
        }
        T nextItem = path.get(0);
        ArrayList<T> rest = new ArrayList<>(path);

        rest.remove(0);
        Descendable desc = descend(nextItem);
        if(desc == null)
            return null;
        return desc.descendPath(rest, item);
    }
}
