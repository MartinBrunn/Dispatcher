package de.devacon.paint;

import java.util.ArrayList;

/**
 * Created by @Martin@ on 14.07.2015 20:26.
 */
public class PaintLayer {
    private int alpha = 255;
    private boolean visible = true;
    private String name = "Unnamed";
    public ArrayList<PaintObject> array = new ArrayList<>();

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<PaintObject> getArray() {
        return array;
    }

    public void setArray(ArrayList<PaintObject> array) {
        this.array = array;
    }

    public void addObject(PaintObject object) {
        array.add(object);
    }
}
