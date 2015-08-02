package de.devacon.xorandoui.system;

import de.devacon.util.Registry;
import de.devacon.util.RegistryImpl;

/**
 * Created by @Martin@ on 19.07.2015 07:07.
 */
public class BaseServices {
    public static final String ACTIVITY = "Activity";
    public static final String CONTENT = "content";
    private static BaseServices ourInstance = new BaseServices();
    private RegistryImpl impl = new RegistryImpl();
    public static BaseServices getInstance() {
        return ourInstance;
    }
    public static Registry getImpl() {
        return getInstance().impl;
    }
    public static Registry getRegistry() {
        return getImpl();
    }
    /**
     * unregister object from given category
     *
     * @param category
     * @param object   previously registered
     * @return <b>true</b> when object exists and is successfully removed from registry
     * <b>false</b> when object doesn't exist.
     */
    public static boolean deregister(String category, Object object) {
        return getImpl().deregister(category, object);
    }

    /**
     * delete a category from the registry
     *
     * @param category
     * @return true if the category was empty and is deleted or false when category is still in use
     */
    public static boolean deregisterCategory(String category) {
        return getImpl().deregisterCategory(category);
    }

    /**
     * @return array of categories registered with this registry
     */
    public static String[] enumCategories() {
        return getImpl().enumCategories();
    }

    /**
     * @param category
     * @return array of names registered with this category.
     * <b>null</b> when the category doesn't exist
     */
    public static String[] enumObjects(String category) {
        return getImpl().enumObjects(category);
    }

    /**
     * @param category
     * @param name
     * @return object which is found in this category. if not existing <b>null</b> is returned
     */
    public static Object get(String category, String name) {
        return getImpl().get(category, name);
    }

    /**
     * @param category
     * @param name
     * @return <b>true</b> when object exists in this category
     * <b>false</b> if not
     */
    public static boolean isRegistered(String category, String name) {
        return getImpl().isRegistered(category, name);
    }

    /**
     * register object with given name in the provided category. name must be unique in the category
     *
     * @param category in which the object belongs
     * @param name     unique name
     * @param object   any object
     * @return <b>true</b> when the object is successfully registered or
     * <b>false</b> when another object was already registered under the same name in this category
     */
    public static boolean register(String category, String name, Object object) {
        return getImpl().register(category, name, object);
    }

    /**
     * Register a new category with this registry.
     * Categories group registered items
     * when a category already exists nothing is done.
     *
     * @param category
     */
    public static void registerCategory(String category) {
        getImpl().registerCategory(category);
    }

    /**
     * Replace an existing object
     *
     * @param category name of the category
     * @param name     of the object
     * @param object   the new object
     * @return <b>true</b> when an object was registered under this name and is replaced
     * <b>false</b> when no object exists under this name
     */
    public static boolean set(String category, String name, Object object) {
        return getImpl().set(category, name, object);
    }

    private BaseServices() {
    }
}
