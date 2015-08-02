package de.devacon.uiutil;

import java.lang.reflect.Field;

import de.devacon.util.Filter;

/**
 * Created by @Martin@ on 08.07.2015 14:21.
 */
public class ResourceReflection{
    Class aClass;

    public ResourceReflection(Class classClass) {
        aClass = classClass;
    }
    public Integer[] getIDs(Filter filter) {
        int count = 0;
        Field[] fields = aClass.getFields();
        int[] out = new int[ fields.length];
        for(int i = 0 ; i < fields.length; ++i) {
            try {
                int field = fields[i].getInt(null);
                if (filter.letsPass(field)) {
                    out[count] = field;
                    count++;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        Integer [] result = new Integer[count];
        for(int i = 0; i < count;++i) {
            result[i] = out[i];
        }
        return result;
    }
}
