package de.devacon.uiutil;

/**
 * Created by @Martin@ on 09.07.2015 02:03.
 */
public class MenuEntry {

    private int icon;
    private String name;
    private String text;
    private Class aClass;
    private String action;
    private String param;
    private String className;

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public Class getaClass() throws ClassNotFoundException {
        return Class.forName(className);
    }

    public String getAction() {
        return action;
    }

    public String getParam() {
        return param;
    }

    public MenuEntry(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public MenuEntry() {
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setaClass(Class aClass) {
        this.className = aClass.getName();
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
