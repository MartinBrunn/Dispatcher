package de.devacon.util;

import java.util.ArrayList;

/**
 * Created by @Martin@ on 04.07.2015.
 */
public abstract class BasePath<S> implements PathLike<S> {

    ArrayList<S> path = null;
    public BasePath(S name) {
        path = new ArrayList<>();
        path.add(name);
    }
    /**
     * removes the first item in path.  If path
     * contains <tt>no items</tt>, returns
     * <tt>false</tt>.
     * @param origin origin of the path. Will be first item.
     * @param path list of items representing the path
     *
     */
    public BasePath(S origin,ArrayList<S> path) {
        this.path = path;
        this.path.add(0,origin);
    }
    public BasePath(BasePath path) {
        this.path = path.path;
    }
    public ArrayList<S> toArrayList() {
        return new ArrayList<>(path);
    }

    /**
     * Returns the first item in path.  If path
     * contains <tt>no items</tt>, returns
     * <tt>null</tt>.
     *
     * @return the first item in path
     */
    @Override
    public S getFirstItem() {
        if(path.isEmpty())
            return null;
        return path.get(0);
    }

    /**
     * Returns <tt>true</tt> if path contains no elements.
     *
     * @return <tt>true</tt> if path contains no elements
     */
    @Override
    public boolean isEmpty() {
        return path.isEmpty();
    }
    /**
     * removes the first item in path.  If path
     * contains <tt>no items</tt>, returns
     * <tt>false</tt>.
     *
     * @return true when item is removed
     */
    @Override
    public boolean removeFirst() {
        if(path.isEmpty())
            return false;
        path.remove(0);
        return true;
    }

    /**
     * removes the last item in path.  If path
     * contains <tt>no items</tt>, returns
     * <tt>false</tt>.
     *
     * @return true when item is removed
     */
    @Override
    public boolean removeLast() {
        if(path.size() < 1)
            return false;
        path.remove(path.size()-1);
        return true;
    }

    /**
     * appends item to the end of path.
     *
      */
    @Override
    public void append(S s) {
        path.add(s);
    }
}
