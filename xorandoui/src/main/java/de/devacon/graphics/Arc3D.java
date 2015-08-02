package de.devacon.graphics;

/**
 * Created by @Martin@ on 16.07.2015 13:31.
 */
public class Arc3D extends Vector3D implements Object3D {

    public Plane3D plane = Plane3D.DRAWING_PLANE;

    public Arc3D() {
    }

    public Arc3D(Point3D point3D) {
        super(point3D);
    }

    public Arc3D(Vector3D start, Vector3D end) {
        super(start, end);
    }

    public Arc3D(Vector3D vector3D) {
        super(vector3D);
    }

    public Arc3D(float x, float y, float z) {
        super(x, y, z);
    }

    public Arc3D(Plane3D plane) {
        this.plane = plane;
    }

    public Arc3D(Point3D point3D, Plane3D plane) {
        super(point3D);
        this.plane = plane;
    }

    public Arc3D(Vector3D start, Vector3D end, Plane3D plane) {
        super(start, end);
        this.plane = plane;
    }

    public Arc3D(Vector3D vector3D, Plane3D plane) {
        super(vector3D);
        this.plane = plane;
    }

    public Arc3D(float x, float y, float z, Plane3D plane) {
        super(x, y, z);
        this.plane = plane;
    }

    /**
     * @return origin of the object
     */
    @Override
    public Vector3D getOrigin() {
        return (Vector3D)this;
    }

    /**
     * sets the origin of the object
     *
     * @param origin
     */
    @Override
    public void setOrigin(Point3D origin) {
        set(origin);
    }

    /**
     * @return plane if it is a 2D object otherwise null
     */
    @Override
    public Plane3D getPlane() {
        return null;
    }

    /**
     * @param plane plane when a 2D object
     */
    @Override
    public void setPlane(Plane3D plane) {

    }

    /**
     * @param newOrigin where to move the complete object
     */
    @Override
    public void moveTo(Point3D newOrigin) {

    }

    /**
     * @param plane where the projection is displayed
     * @return resulting 2D object.
     */
    @Override
    public Object3D project(Plane3D plane) {
        return null;
    }
}
