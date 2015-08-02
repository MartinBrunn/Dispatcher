package de.devacon.graphics;

/**
 * Created by @Martin@ on 15.07.2015 11:20.
 */
public class NormalVector3D extends Vector3D {
    public NormalVector3D(Vector3D vector3D){
        super(vector3D);
        if(length() != 0) {
            normalize();
        }
    }
}
