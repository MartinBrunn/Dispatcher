package de.devacon.graphics;

/**
 * Created by @Martin@ on 15.07.2015 11:20.
 */
public class Vector3D extends Point3D {
    static public float scalarProduct(Vector3D p1,Vector3D p2) {
        return p1.x*p2.x + p1.y*p2.y + p1.z*p2.z;
    }
    static public Vector3D crossProduct(Vector3D p1,Vector3D p2) {
        return new Vector3D(p1.y*p2.z - p1.z*p2.y,p1.z*p2.x - p1.x*p2.z,p1.x*p2.y - p1.y*p2.x);
    }
    static public Vector3D subtract(Vector3D p2,Vector3D p1) {
        return new Vector3D(p2.x - p1.x,p2.y - p1.y,p2.z - p1.z);
    }
    static public Vector3D add(Vector3D p1,Vector3D p2) {
        return new Vector3D(p2.x + p1.x,p2.y + p1.y,p2.z + p1.z);
    }

    public static Vector3D getMidPoint(Vector3D v1, Vector3D v2){
        Vector3D v = subtract(v2,v1);
        v.divideBy(2);
        return add(v1, v);
    }

    public Vector3D() {
        super();
    }
    public Vector3D(Vector3D vector3D) {
        super(vector3D.x,vector3D.y,vector3D.z);
    }
    public Vector3D(Point3D point3D) {
        super(point3D);
    }
    public Vector3D(Vector3D start,Vector3D end) {
        super(subtract(end,start));
    }
    public Vector3D(float x, float y,float z) {
        super(x,y,z);
    }
    public void normalize() {
        divideBy(length());
    }
    public float scalarProduct(Vector3D p2) {
        return scalarProduct(this,p2);
    }
    /*public Vector3D crossProduct(Vector3D p2) {
        return crossProduct(this,p2);
    }*/
    public float[] getAngles() {
        float[] angles = new float[3];
        float len = length();
        float xa = (float)Math.asin(x/len);
        float ya = (float)Math.asin(y/len);
        float za = (float)Math.asin(z/len);

        angles[0] = xa ;//> Math.PI/2 ? -xa : xa ;
        angles[1] = ya ;//> Math.PI/2 ? -ya : ya ;
        angles[2] = za ;//> Math.PI/2 ? -za : za ;

        return angles;
    }
    public boolean isNull() {
        if(length() == 0) {
            return true;
        }
        return false;
    }

}
