package de.devacon.graphicutil;

import android.graphics.Path;

import de.devacon.graphics.Vector3D;

/**
 * Created by @Martin@ on 21.07.2015 12:33.
 */
public class Triangle {
    Vector3D[] points = new Vector3D[3];
    public void setPoints(Vector3D p1,Vector3D p2,Vector3D p3) {
        points[0] = new Vector3D(p1);
        points[1] = new Vector3D(p2);
        points[3] = new Vector3D(p3);
    }
    public void setPoints(float x1,float y1,float x2,float y2,float x3,float y3) {
        points[0] = new Vector3D(x1,y1,0);
        points[1] = new Vector3D(x2,y2,0);
        points[2] = new Vector3D(x3,y3,0);
    }
    public void setPoint(int index,float x,float y) {
        points[ index ] = new Vector3D(x,y,0);
    }
    public void setPoint(int index , Vector3D p) {
        points[ index ] = new Vector3D(p);
    }
    public void draw(Path path) {
        path.moveTo(points[0].x,points[0].y);
        path.lineTo(points[1].x, points[1].y);
        path.lineTo(points[2].x, points[2].y);
        path.lineTo(points[0].x, points[0].y);
    }
}
