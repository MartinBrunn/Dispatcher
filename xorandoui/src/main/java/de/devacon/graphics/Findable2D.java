package de.devacon.graphics;

/**
 * Created by @Martin@ on 17.07.2015 07:52.
 */
public interface Findable2D {
    boolean touchesContour(float x,float y,float tolerance) ;
    boolean touches(float x,float y,float tolerance);
}
