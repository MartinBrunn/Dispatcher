package de.devacon.util;

/**
 * Created by @Martin@ on 04.07.2015.
 */
public interface PathLike<Item> {

    public Item getFirstItem();
    public boolean isEmpty();
    public boolean isAbsolute();
    public boolean isRelative();
    public boolean isRelativeToParent();
    public boolean removeFirst();
    public boolean removeLast();
    public void append(Item item);
}
