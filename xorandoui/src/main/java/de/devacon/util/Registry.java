package de.devacon.util;

/**
 * Created by @Martin@ on 19.07.2015 07:11.
 */


/**
 * Interface of an object registry grouping all the registered objects in categories
 * names for the objects must be unique at the scope of the category but the same name
 * may exist in several categories.
 * objects can be found by the name of the category and their name
 * objects may be replaced
 * registration must fail if another object exists under the same name in the same category
 */
public interface Registry {
    /**
     * Register a new category with this registry.
     * Categories group registered items
     * when a category already exists nothing is done.
     *
     * @param category
     */
    void registerCategory(String category);

    /**
     * delete a category from the registry
     * @param category
     * @return true if the category was empty and is deleted or false when category is still in use
     */
    boolean deregisterCategory(String category);

    /**
     * register object with given name in the provided category. name must be unique in the category
     *
     * @param category in which the object belongs
     * @param name unique name
     * @param object any object
     * @return  <b>true</b> when the object is successfully registered or
     *          <b>false</b> when another object was already registered under the same name in this category
     */
    boolean register(String category,String name,Object object);

    /**
     * unregister object from given category
     * @param category
     * @param object previously registered
     * @return <b>true</b> when object exists and is successfully removed from registry
     *         <b>false</b> when object doesn't exist.
     */
    boolean deregister(String category,Object object);

    /**
     *
     * @param category
     * @param name
     * @return <b>true</b> when object exists in this category
     *         <b>false</b> if not
     */
    boolean isRegistered(String category,String name);

    /**
     *
     * @param category
     * @param name
     * @return object which is found in this category. if not existing <b>null</b> is returned
     */
    Object get(String category,String name);

    /**
     * Replace an existing object
     * @param category name of the category
     * @param name of the object
     * @param object the new object
     * @return <b>true</b> when an object was registered under this name and is replaced
     *         <b>false</b> when no object exists under this name
     */
    boolean set(String category,String name,Object object);

    /**
     *
     * @return array of categories registered with this registry
     */
    String[] enumCategories() ;

    /**
     *
     * @param category
     * @return array of names registered with this category
     */
    String[] enumObjects(String category);
}
