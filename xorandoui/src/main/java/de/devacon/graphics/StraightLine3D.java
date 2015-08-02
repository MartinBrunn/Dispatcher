package de.devacon.graphics;

import de.devacon.graphics.exception.NoIntersectionException;

/**
 * Created by @Martin@ on 15.07.2015 11:06.
 */
public class StraightLine3D extends Point3D {
    public static final StraightLine3D X_AXIS = new StraightLine3D(new Vector3D(1,0,0));
    public static final StraightLine3D Y_AXIS = new StraightLine3D(new Vector3D(0,1,0));
    public static final StraightLine3D Z_AXIS = new StraightLine3D(new Vector3D(0,0,1));
    NormalVector3D normalVector;
    public StraightLine3D(Vector3D direction) {
        super();
        normalVector =  new NormalVector3D(direction);
    }
    public StraightLine3D(Point3D p1,Vector3D direction){
        super(p1);
        normalVector = new NormalVector3D(direction);
    }
    public StraightLine3D(Point3D p1,Point3D p2){
        super(p1);
        Vector3D v2 = new Vector3D(p2);
        v2.subtract(new Vector3D(p1));
        normalVector = new NormalVector3D(v2);
    }
    public Point3D intersectionPoint(StraightLine3D line) throws NoIntersectionException {
        float sa = 0;
        try {
            if(line.normalVector.x == 0) {
                sa = (line.x - x)/normalVector.x ;
            }
            else {
                float sum1 = ((line.y - y + (x * line.normalVector.y/ line.normalVector.x) -
                        (line.x * line.normalVector.y / line.normalVector.x)) * normalVector.x) ;
                float sum2 = (normalVector.y - (normalVector.x * line.normalVector.y/ line.normalVector.x)) ;
                sa = sum1 / sum2 ;
                //sb = (x + (sum1 / sum2  ) + y) / line.normalVector.x;
            }
            float xx = x + sa*normalVector.x;
            float yy = y + sa*normalVector.y;
            float zz = z + sa*normalVector.z;
            // TODO Check for validity in z equation!

            return new Point3D(xx,yy,zz);
        }
        catch(Exception e) {
            throw new NoIntersectionException();
        }
    }

}
