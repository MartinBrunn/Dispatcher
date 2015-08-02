package de.devacon.util;

import java.util.HashMap;

/**
 * Created by @Martin@ on 19.07.2015 07:26.
 */
public class RegistryImpl implements Registry {

    protected class Category {
        String name ;
        protected HashMap<String,Object> map = new HashMap<>();
        protected HashMap<Object,String> reverse = new HashMap<>();

        protected boolean remove(Object object) {
            if(map.containsValue(object)) {
                map.remove(reverse.get(object));
                reverse.remove(object);
                return true;
            }
            return false;
        }
        protected boolean insertUnique(String name,Object object) {
            if(isRegistered(name)) {
                if(get(name).equals(object)) {
                    return true;
                }
                else {
                    return false;
                }
            }
            map.put(name,object);
            reverse.put(object,name);
            return true;
        }

        private Object get(String name) {
            return map.get(name);
        }

        protected boolean isRegistered(String name) {
            return map.containsKey(name);
        }

        public boolean isEmpty() {
            return map.isEmpty();
        }

        public boolean set(String name, Object object) {
            if(isRegistered(name)) {
                reverse.remove(object);
                map.put(name, object);
                reverse.put(object,name);
                return true;
            }
            return false;
        }

        public String[] enumObjects() {
            String[] array = new String[map.size()];
            return map.keySet().toArray(array);
        }
    }
    private HashMap<String,Category> categories = new HashMap<>();
    /**
     * Register a new category with this registry.
     * Categories group registered items
     * when a category already exists nothing is done.
     *
     * @param category
     */
    @Override
    public void registerCategory(String category) {
        if(categories.containsKey(category)) {
            return;
        }
        categories.put(category,new Category());
    }

    /**
     * delete a category from the registry
     *
     * @param category
     * @return true if the category was empty and is deleted or false when category is still in use
     */
    @Override
    public boolean deregisterCategory(String category) {
        if(categories.containsKey(category)) {
            if(categories.get(category).isEmpty()) {
                categories.remove(category);
                return true;
            }
        }
        return false;
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
    @Override
    public boolean register(String category, String name, Object object) {
        Category cat = categories.get(category);
        if(cat != null) {
            return cat.insertUnique(name,object);
        }
        return false;
    }

    /**
     * unregister object from given category
     *
     * @param category
     * @param object   previously registered
     * @return <b>true</b> when object exists and is successfully removed from registry
     * <b>false</b> when object doesn't exist.
     */
    @Override
    public boolean deregister(String category, Object object) {

        Category cat = categories.get(category);
        if(cat != null) {
            return cat.remove(object);
        }
        return false;
    }

    /**
     * @param category
     * @param name
     * @return <b>true</b> when object exists in this category
     * <b>false</b> if not
     */
    @Override
    public boolean isRegistered(String category, String name) {
        Category cat = categories.get(category);
        if(cat != null) {
            return cat.isRegistered(name);
        }
        return false;
    }

    /**
     * @param category
     * @param name
     * @return object which is found in this category. if not existing <b>null</b> is returned
     */
    @Override
    public Object get(String category, String name) {
        Category cat = categories.get(category);
        if(cat != null) {
            return cat.get(name);
        }
        return null;
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
    @Override
    public boolean set(String category, String name, Object object) {
        Category cat = categories.get(category);
        if(cat != null) {
            return cat.set(name, object);
        }
        return false;
    }

    /**
     * @return array of categories registered with this registry
     */
    @Override
    public String[] enumCategories() {

        String[] array = new String[categories.size()];
        return categories.keySet().toArray(array);
    }

    /**
     * @param category
     * @return array of names registered with this category.
     *         <b>null</b> when the category doesn't exist
     */
    @Override
    public String[] enumObjects(String category) {
        Category cat = categories.get(category);
        if(cat != null) {
            return cat.enumObjects();
        }
        return null ;
    }
}
