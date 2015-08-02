package de.devacon.paint;

import java.util.HashMap;

import de.devacon.graphics.GraphicsObject;
import de.devacon.graphics.Point3D;
import de.devacon.inputmethod.Touchable;

/**
 * Created by @Martin@ on 14.07.2015 20:26.
 */
public class PaintObject implements Touchable {
    public Point3D position;
    private GraphicsObject object ;
    private int alpha = 255;
    //private boolean active = false;
    enum State {
        INACTIVE,
        RESIZING,
        MOVING,
    }
    private State state = State.INACTIVE;
    HashMap<String,Integer> styles = new HashMap<>();
    HashMap<String,Float> dimensions = new HashMap<>();

    HashMap<String,Integer> colors = new HashMap<>();

    public PaintObject() {
    }

    //public boolean isActive() {return active;}

    //public void setActive(boolean active) { this.active = active; }

    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public GraphicsObject getObject() {
        return object;
    }

    public void setObject(GraphicsObject object) {
        this.object = object;
    }

    public void setColor(String which,Integer color) {
        colors.put(which,color);
    }
    public Integer getColor(String which) {
        return colors.get(which);
    }
    public void setStyle(String which,Integer style) {
        styles.put(which,style);
    }
    public int getStyle(String which) {
        if(styles.containsKey(which))
            return styles.get(which);
        return 0;
    }
    public void setDimension(String which,Float dimension) {
        dimensions.put(which,dimension);
    }
    public Float getDimension(String which) {
        if(dimensions.containsKey(which))
            return dimensions.get(which);
        return 1.0f;
    }

    @Override
    public boolean touches(float x, float y, float tolerance) {
        if(object != null)
            return object.touches(x, y, tolerance);
        return false;
    }

    public boolean treatDetail(float x, float y,float tolerance) {
        if(object != null)
            return object.treatDetail(x, y,tolerance,isResizing());
        return false;
    }
    public void advanceState() {
        if(isInActive()) {
            state = State.MOVING;
        }
        else if(isMoving()) {
            state = State.RESIZING;
        }
        else if(isResizing()) {
            state = State.MOVING;
        }
    }
    public void setInActive() {
        state = State.INACTIVE;
    }
    public boolean isInActive() {
        return state == State.INACTIVE;
    }
    public boolean isResizing() {
        return state == State.RESIZING;
    }
    public boolean isMoving() {

        return state == State.MOVING;
    }
}
