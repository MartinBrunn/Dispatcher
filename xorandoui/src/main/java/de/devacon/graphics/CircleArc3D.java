package de.devacon.graphics;

import de.devacon.graphics.exception.AllPointsInLineException;
import de.devacon.graphics.exception.PointNotOnPlaneException;

/**
 * Created by @Martin@ on 16.07.2015 13:37.
 */
public class CircleArc3D extends Circle3D {

    public Angle angle = Angle.RIGHTANGLE;
    public Point3D start;
    public CircleArc3D() {
    }

    public CircleArc3D(Point3D center, float radius) {
        super(center, radius);
    }

    public CircleArc3D(Point3D p1, Point3D p2, Point3D p3) throws Exception, AllPointsInLineException {
        super(p1, p2, p3);
        angle = Angle.calcAngle(this,p1,p3);
        start = p1;
    }


    public CircleArc3D(Circle3D circle) {
        super(circle);
    }
    public CircleArc3D(CircleArc3D circle) {
        super(circle);
        angle = circle.angle;
    }

    public CircleArc3D(Angle angle) {
        this.angle = angle;
    }

    public CircleArc3D(Point3D center, Point3D point, Plane3D plane, Angle angle) throws PointNotOnPlaneException {
        super(center, point, plane);
        this.angle = angle;
    }

    public CircleArc3D(Point3D center, float radius, Angle angle) {
        super(center, radius);
        this.angle = angle;
    }

    public CircleArc3D(Point3D center, float radius, Plane3D plane, Angle angle) {
        super(center, radius, plane);
        this.angle = angle;
    }

    public CircleArc3D(Circle3D circle, Angle angle) {
        super(circle);
        this.angle = angle;
    }

    public Angle getAngle() {
        return angle;
    }

    public void setAngle(Angle angle) {
        this.angle = angle;
    }
}
