package de.devacon.graphics;

import java.util.ArrayList;

/**
 * Created by @Martin@ on 16.07.2015 17:56.
 */
public class PolyLine3D extends Vector3D implements Object3D {


    enum Algorithm {
        STRAIGHT,
        BEZIER,
        CUBIC,
        QUADRATIC,
        SPLINE,
        ARC,
    }
    public ArrayList<Point3D> points = new ArrayList<>();
    Algorithm algorithm = Algorithm.STRAIGHT;
    public PolyLine3D() {
    }

    public PolyLine3D(Point3D point3D) {
        super(point3D);
    }

    public PolyLine3D(Vector3D vector3D) {
        super(vector3D);
    }

    public PolyLine3D(float x, float y, float z) {
        super(x, y, z);
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public void addPoint(Point3D point){
        points.add(point);
    }
    public void setPoint(int index, Point3D point) {
        points.set(index,point);
    }
    public void addPoints(Point3D point1,Point3D point2){
        points.add(point1);
        points.add(point2);
    }
    public void addPoints(Point3D point1,Point3D point2,Point3D point3){
        points.add(point1);
        points.add(point2);
        points.add(point3);
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
        for(Point3D point:points) {
            point.add(diff);
        }
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
