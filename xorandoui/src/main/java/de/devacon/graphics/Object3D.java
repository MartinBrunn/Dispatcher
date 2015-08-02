package de.devacon.graphics;

/**
 * Created by @Martin@ on 16.07.2015 07:32.
 */
public interface Object3D {
    /**
     *
     * @return origin of the object
     */
    Point3D getOrigin();

    /**
     *  sets the origin of the object
     * @param origin
     */
    void setOrigin(Point3D origin);

    /**
     *
     * @return plane if it is a 2D object otherwise null
     */
    Plane3D getPlane();

    /**
     *
     * @param plane plane when a 2D object
     */
    void setPlane(Plane3D plane);
    /**
     *
     * @param newOrigin where to move the complete object
     */
    void moveTo(Point3D newOrigin);

    /**
     *
     * @param plane where the projection is displayed
     * @return resulting 2D object.
     */
    Object3D project(Plane3D plane);

}
