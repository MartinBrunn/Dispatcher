package de.devacon.util;

public class Util {
    protected static String floatFormat = "%.02f";
    public static void setFloatFormat(String format){
        Util.floatFormat = format;
    }
    public static String toString(float f){
        return String.format(floatFormat,f);
    }
    public static String toString(String s,int len){
        return s.substring(0,len);
    }
}
