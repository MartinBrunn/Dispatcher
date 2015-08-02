package de.devacon.util;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by @Martin@ on 04.07.2015.
 */
public interface TreeLike<Item,Leaf>  {
    public TreeLike<Item,Leaf> getParent();
    public boolean isRoot();
    public TreeLike<Item,Leaf> getBranch(PathLike path);
    public boolean hasBranches();
    public boolean hasLeafs();
    Leaf getLeaf(PathLike relativePath);
    public Item getName();
    Collection<Item> getLeafs() ;
    Collection<Item> getBranches();
    Collection<Item> getLeafs(Filter filter) ;
    Collection<Item> getBranches(Filter filter);
    public class TreeUtil{
        public static TreeLike getRoot(TreeLike _this) {

            while(!_this.isRoot()) {
                _this = _this.getParent();
            }
            return _this;
        }
        public static <Item> ArrayList<Item> getPath(TreeLike _this) {
            if(_this.isRoot()) {
                ArrayList<Item> list = new ArrayList<>();
                list.add((Item) _this.getName());
                return list;
            }
            ArrayList<Item> list = getPath(_this.getParent());
            list.add((Item) _this.getName());
            return list;
        }
    }
}
