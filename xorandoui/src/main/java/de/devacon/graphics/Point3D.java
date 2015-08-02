package de.devacon.graphics;

import android.graphics.Point;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.FloatMath;

import de.devacon.math.MathUtil;

/**
 * Created by @Martin@ on 14.07.2015 20:39.
 */
public class Point3D implements Parcelable{
    public static final int PLANE_X = 1;
    public static final int PLANE_Y = 2;
    public static final int PLANE_Z = 3;
    public float x;
    public float y;
    public float z;

    public Point3D() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Point3D(float x, float y,float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D(float x, float y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }
    public Point3D(Point3D p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }

    public Point3D(Point p) {
        this.x = p.x;
        this.y = p.y;
        this.z = 0;
    }
    public Point3D(Point p,int z) {
        this.x = p.x;
        this.y = p.y;
        this.z = z;
    }
    public Point3D(PointF p) {
        this.x = p.x;
        this.y = p.y;
        this.z = 0;
    }
    public Point3D(PointF p,float z) {
        this.x = p.x;
        this.y = p.y;
        this.z = z;
    }
    public void divideBy(float divisor) {
        x/=divisor;
        y/=divisor;
        z/=divisor;
    }
    public void multiplyBy(float factor) {
        x*=factor;
        y*=factor;
        z*=factor;
    }
    public void subtract(Point3D p1) {
        x -= p1.x;
        y -= p1.y;
        z -= p1.z;
    }
    public void add(Point3D p1) {
        x += p1.x;
        y += p1.y;
        z += p1.z;
    }
    public boolean equals(Point3D p2){
        if(MathUtil.isSmall(p2.x - x) &&
                MathUtil.isSmall(p2.y - y) &&
                MathUtil.isSmall(p2.z - z)){
            return true;
        }
        return false;
    }
    public boolean isNull() {
        return equals(new Point3D());
    }

    /**
     * Set the point's x and y coordinates
     */
    public final void set(float x, float y,float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Set the point's x and y coordinates to the coordinates of p
     */
    public final void set(Point3D p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }

    public final void negate() {
        x = -x;
        y = -y;
        z = -z;
    }

    public final void offset(float dx, float dy,float dz) {
        x += dx;
        y += dy;
        z += dz;
    }

    /**
     * Returns true if the point's coordinates equal (x,y)
     */
    public final boolean equals(float x, float y,float z) {
        return this.x == x && this.y == y && this.z == z ;
    }

    /**
     * Return the euclidian distance from (0,0,0) to the point
     */
    public final float length() {
        return length(x, y, z);
    }

    /**
     * Returns the euclidian distance from (0,0) to (x,y)
     */
    public static float length(float x, float y,float z) {
        return FloatMath.sqrt(x * x + y * y + z*z);
    }

    /**
     * Parcelable interface methods
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Write this point to the specified parcel. To restore a point from
     * a parcel, use readFromParcel()
     * @param out The parcel to write the point's coordinates into
     */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeFloat(x);
        out.writeFloat(y);
        out.writeFloat(z);
    }

    public static final Parcelable.Creator<Point3D> CREATOR = new Parcelable.Creator<Point3D>() {
        /**
         * Return a new point from the data in the specified parcel.
         */
        public Point3D createFromParcel(Parcel in) {
            Point3D r = new Point3D();
            r.readFromParcel(in);
            return r;
        }

        /**
         * Return an array of rectangles of the specified size.
         */
        public Point3D[] newArray(int size) {
            return new Point3D[size];
        }
    };

    /**
     * Set the point's coordinates from the data stored in the specified
     * parcel. To write a point to a parcel, call writeToParcel().
     *
     * @param in The parcel to read the point's coordinates from
     */
    public void readFromParcel(Parcel in) {
        x = in.readFloat();
        y = in.readFloat();
        z = in.readFloat();
    }

    /**
     *
     * @param plane one of
     *        PLANE_X , PLANE_Y , PLANE_Z
     * @return
     * @throws IllegalArgumentException
     */
    public PointF project(int plane) throws IllegalArgumentException {
        switch(plane) {
            case PLANE_X:
                return new PointF(y,z);
            case PLANE_Y:
                return new PointF(x,z);
            case PLANE_Z:
                return new PointF(x,y);
            default:
                throw new IllegalArgumentException();
        }
    }


}
