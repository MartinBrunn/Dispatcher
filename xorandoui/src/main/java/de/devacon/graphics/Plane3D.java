package de.devacon.graphics;

import de.devacon.exception.NotImplementedException;
import de.devacon.math.MathUtil;

/**
 * Created by @Martin@ on 14.07.2015 22:55.
 */
public class Plane3D extends NormalVector3D{
    static public Plane3D DRAWING_PLANE = new Plane3D(0,0,1,0);
    private float distance;
    public Plane3D(Plane3D plane3D) {
        super(plane3D);
        distance = plane3D.distance;
    }
    public Plane3D(float x, float y, float z,float distance) {
        super(new Vector3D(x,y,z));
        this.distance = distance;
    }
    public Plane3D(Vector3D p1,Vector3D p2,Vector3D p3) {
        this(calcPlane(p1,p2,p3));
    }
    public Plane3D(Point3D p1,Point3D p2,Point3D p3) {
        this(calcPlane(p1,p2,p3));
    }
    public Plane3D(Vector3D n0,float distance) {
        super(n0);
        this.distance = distance;
        x = n0.x;
        y = n0.y;
        z = n0.z;
    }
    public boolean isOnPlane(Point3D point){
        if( MathUtil.isSmall( (point.x * x + point.y * y + point.z * z) - distance)){
            return true;
        }
        return false;
    }
    public Point3D intersectionPoint(StraightLine3D line) throws NotImplementedException{
        throw new NotImplementedException();
    }

    public static Plane3D calcPlane(Point3D p1, Point3D p2, Point3D p3) {
        Vector3D v2 = new Vector3D(p2);
        v2.subtract(p1);
        Vector3D v3 = new Vector3D(p3);
        v3.subtract(p1);
        Vector3D cross = crossProduct(v2,v3);
        float distance = new Vector3D(p1).scalarProduct(cross);
        return new Plane3D(new NormalVector3D(cross),distance);
    }
}
