package de.devacon.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by @Martin@ on 04.07.2015 12:02.
 *
 */
public class Tree<PathItem,Leaf>  implements TreeLike<PathItem,Leaf> {
    PathItem name;
    Tree<PathItem,Leaf> parent = null;
    HashMap<PathItem, TreeLike<PathItem,Leaf>> branches;
    HashMap<PathItem,Leaf> leaves;

    Tree(Tree<PathItem,Leaf> parent,PathItem name) {
        this.name = name ;
        this.parent = parent;
    }



    @Override
    public TreeLike<PathItem,Leaf> getParent() {
        return parent;
    }

    @Override
    public boolean isRoot() {
        return parent == null;
    }

    @Override
    public TreeLike<PathItem,Leaf> getBranch(PathLike path) {
        return null;
    }

    @Override
    public boolean hasBranches() {
        return branches.size() > 0;
    }

    @Override
    public boolean hasLeafs() {
        return leaves.size() > 0;
    }

    @Override
    public Leaf getLeaf(PathLike relativePath) {
        return null;
    }

    @Override
    public  PathItem getName() {
        return null;
    }

    @Override
    public Collection<PathItem> getLeafs() {
        return leaves.keySet();
    }

    @Override
    public Collection<PathItem> getBranches() {
        return branches.keySet();
    }

    @Override
    public Collection<PathItem> getLeafs(Filter filter) {
        ArrayList<PathItem> result = new ArrayList<>();
        for(PathItem keyx:leaves.keySet())
        {
            Leaf leaf = leaves.get(keyx);
            Pair<PathItem,Leaf> pair = new Pair(keyx,leaf) ;
            if(filter.letsPass(pair)) {
                result.add(keyx);
            }

        }
        return result;
    }

    @Override
    public Collection<PathItem> getBranches(Filter filter) {
        ArrayList<PathItem> result = new ArrayList<>();
        for(PathItem keyx:leaves.keySet())
        {
            TreeLike<PathItem,Leaf> node = branches.get(keyx);
            Pair<PathItem,Leaf> pair = new Pair(keyx,node) ;
            if(filter.letsPass(pair)) {
                result.add(keyx);
            }

        }
        return result;
    }
}
