package de.devacon.graphics;

/**
 * Created by @Martin@ on 15.07.2015 17:04.
 */
public class Angle {
    public static final Angle ZERO = new Angle(0);
    Float angle;
    public static final Angle RIGHTANGLE = new Angle(Math.PI/2);
    public static final Angle DEGREE_0 = new Angle(0);
    public static final Angle DEGREE_90 = new Angle(Math.PI/2);
    public static final Angle DEGREE_60 = new Angle(Math.PI/3);
    public static final Angle DEGREE_45 = new Angle(Math.PI/4);
    public static final Angle DEGREE_30 = new Angle(Math.PI/6);
    public static final Angle DEGREE_180 = new Angle(Math.PI);
    public static final Angle DEGREE_360 = new Angle(Math.PI*2);

    static public Angle calcAngle(Vector3D v1,Vector3D v2) {
        float cosinus = (v1.scalarProduct(v2)/(v1.length()*v2.length()));
        Angle angle = new Angle(0);
        angle.arccos(cosinus);
        return angle;
    }
    static public Angle calcAngle(Point3D center, Point3D p1, Point3D p2) {

        Vector3D v1 = new Vector3D(p1);
        Vector3D v2 = new Vector3D(p2);
        v1.subtract(center);
        v2.subtract(center);
        return calcAngle(v1,v2);
    }

    /**
     * Constructs a new {@code Angle} from the specified string.
     *
     * @param string the string representation of a float value.
     * @throws NumberFormatException if {@code string} can not be parsed as a float value.
     *
     */
    public Angle(String string) throws NumberFormatException {
        angle = new Float(string);
//        angle = (angle*(float)Math.PI)/180;
    }

    /**
     * Constructs a new {@code Float} with the specified primitive double value.
     *
     * @param value the primitive double value to store in the new instance.
     */
    public Angle(double value) {
        angle = new Float(value);
    }

    /**
     * Constructs a new {@code Float} with the specified primitive float value.
     *
     * @param value the primitive float value to store in the new instance.
     */
    public Angle(float value) {
        angle = new Float(value);
    }
    public void fromDegree(Float degree){
        angle = (degree*(float)Math.PI)/180;
    }
    public void fromDegree(String inDegrees){
        Float degree = new Float(inDegrees);
        angle = (degree*(float)Math.PI)/180;
    }
    public float toDegree() {
        return (angle*180)/(float)Math.PI;
    }
    public void arccos(float cosinus) {
        angle = (float)Math.acos(cosinus);
    }
    public float cos() {
        return (float)Math.cos(angle);
    }
}
