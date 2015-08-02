package de.devacon.graphics;

/**
 * Created by @Martin@ on 16.07.2015 17:48.
 */
public class Line3D extends Vector3D implements Object3D {
    public Vector3D end;


    public Line3D(Vector3D end) {
        this.end = end;
    }

    public Line3D(Point3D point3D, Point3D end) {
        super(point3D);
        this.end = new Vector3D(end);
    }

    public Line3D(Vector3D start, Vector3D end) {
        super(start);
        this.end = end;
    }

    public Line3D(float x, float y, float z,float xEnd, float yEnd, float zEnd) {
        super(x, y, z);
        this.end = new Vector3D(xEnd,yEnd,zEnd);
    }

    public Vector3D getEnd() {
        return end;
    }

    public void setEnd(Vector3D end) {
        this.end = end;
    }

    /**
     * @return origin of the object
     */
    @Override
    public Vector3D getOrigin() {
        return (Vector3D) this;
    }

    /**
     * sets the origin of the object
     *
     * @param origin
     */
    @Override
    public void setOrigin(Point3D origin) {
        set(origin.x,origin.y,origin.z);
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
        Vector3D diff = subtract(new Vector3D(newOrigin),this) ;
        end.add(diff);
        setOrigin(newOrigin);
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
