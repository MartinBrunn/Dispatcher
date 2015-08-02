package de.devacon.util;

import java.util.ArrayList;

/**
 * Created by @Martin@ on 04.07.2015.
 */
public class Path extends BasePath<String> {
    public static final String ROOT = "/";
    public static final String CURRENT =".";
    public static final String PARENT = "..";
    public Path(String s){
        super(s);
    }
    public Path(String s,ArrayList<String> path){
        super(s,path);
    }
    @Override
    public boolean isAbsolute() {
        if(path.size() > 0) {
            String first = path.get(0) ;
            if(first.equals("/")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isRelative() {
        if (path.size() > 0) {
            String first = path.get(0) ;
            if (first.equals(".")) {
                return true;
            }
            if(first.equals("..") || first.equals("/")) {
                return false;
            }
            return true;
        }
        return false  ;
    }
    @Override
    public boolean isRelativeToParent() {
        if (path.size() > 0) {
            String first = path.get(0) ;
            if (first.equals("..")) {
                return true;
            }
        }
        return false;
    }
}
