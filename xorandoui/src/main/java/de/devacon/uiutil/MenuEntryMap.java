package de.devacon.uiutil;

import android.content.res.Resources;

import org.xmlpull.v1.XmlPullParser;

import java.util.HashMap;
import java.util.Map;

import de.devacon.util.Filter;
import de.devacon.xorandoui.menu.Menu;

/**
 * Created by @Martin@ on 09.07.2015 02:15.
 */
public class MenuEntryMap extends HashMap<String,MenuEntry> {
    Class aClass;
    /**
     * Constructs a new empty {@code HashMap} instance.
     */
    public MenuEntryMap(Class aClass) {
        this.aClass = aClass;
    }

    /**
     * Constructs a new {@code HashMap} instance with the specified capacity.
     *
     * @param capacity the initial capacity of this hash map.
     * @throws IllegalArgumentException when the capacity is less than zero.
     */
    public MenuEntryMap(Class aClass,int capacity) {
        super(capacity);
        this.aClass = aClass;
    }

    /**
     * Constructs a new {@code HashMap} instance containing the mappings from
     * the specified map.
     *
     * @param map the mappings to add.
     */
    public MenuEntryMap(Class aClass,Map<? extends String, ? extends MenuEntry> map) {
        super(map);
        this.aClass = aClass;
    }
    public Menu populate(Resources resources,int resIdMenus,String menuName) {
        ResourceReflection resourceReflection = new ResourceReflection(aClass);

        Integer[] array = resourceReflection.getIDs(new Filter(){
            @Override
            public boolean letsPass(Object object) {
                Integer integer = (Integer)object;

                return true;
            }
        });
        HashMap<String,MenuEntry> map = new HashMap<>();
        for(Integer resId : array) {
            String name = resources.getResourceEntryName(resId.intValue());
            MenuEntry entry = new MenuEntry(resId.intValue(),name);
            map.put(name,entry);
        }
        XmlPullParser parser = resources.getXml(resIdMenus);
        Menu menu = Menu.inflate(parser,menuName, map);
        parser = null;
        return menu ;
    }
}
