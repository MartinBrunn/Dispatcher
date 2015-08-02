package de.devacon.graphics;

import de.devacon.graphics.exception.AllPointsInLineException;
import de.devacon.graphics.exception.NoIntersectionException;
import de.devacon.graphics.exception.PointNotOnPlaneException;

/**
 * Created by @Martin@ on 15.07.2015 22:28.
 */
public class Circle3D extends Vector3D implements Object3D {
    protected Plane3D plane = Plane3D.DRAWING_PLANE;

    public static Circle3D calcCircle(Point3D p1,Point3D p2,Point3D  p3) throws Exception, AllPointsInLineException {

        try {
            Vector3D v1 = new Vector3D(p1);
            Vector3D v2 = new Vector3D(p2);
            Vector3D v3 = new Vector3D(p3);
            Vector3D p = Vector3D.getMidPoint(v1, v2);
            Vector3D q = Vector3D.getMidPoint(v2, v3);
            Plane3D plane = new Plane3D(v1,v2,v3);
            StraightLine3D straightP = new StraightLine3D(p,crossProduct(subtract(p,v1),plane));
            StraightLine3D straightQ = new StraightLine3D(q,crossProduct(subtract(q,v2),plane));
            Point3D center = straightP.intersectionPoint(straightQ);
            return new Circle3D(center,p1,plane);
        } catch (PointNotOnPlaneException e) {
            Exception ex = new Exception();
            ex.initCause(e);
            throw ex;
        } catch (NoIntersectionException e) {
            throw new AllPointsInLineException();
        }
    }

    public float radius ;
    public Circle3D() {
        super();
        radius = 0;
    }
    public Circle3D(Circle3D circle) {
        super(circle);
        radius = circle.radius;
        setPlane(circle.getPlane());
    }
    public Circle3D(Point3D center,float radius,Plane3D plane){
        super(center);
        setPlane(plane);
        this.radius = radius;
    }
    public Circle3D(Point3D center,float radius){
        super(center);
        this.radius = radius;
    }
    public Circle3D(Point3D center,Point3D point,Plane3D plane) throws PointNotOnPlaneException {
        super(center);
        if(!plane.isOnPlane(point)) {
            throw new PointNotOnPlaneException();
        }
        Vector3D r = new Vector3D(point);
        r.subtract(center);
        radius = r.length();
        setPlane(plane);
    }
    public Circle3D(Point3D p1,Point3D p2,Point3D  p3) throws Exception, AllPointsInLineException {
        this(calcCircle(p1, p2, p3));

    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
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
        set(origin);
    }

    /**
     * @return plane if it is a 2D object otherwise null
     */
    @Override
    public Plane3D getPlane() {
        return plane ;
    }

    /**
     * @param plane plane when a 2D object
     */
    @Override
    public void setPlane(Plane3D plane) {
        this.plane = plane;
    }

    /**
     * @param newOrigin where to move the complete object
     */
    @Override
    public void moveTo(Point3D newOrigin) {
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
