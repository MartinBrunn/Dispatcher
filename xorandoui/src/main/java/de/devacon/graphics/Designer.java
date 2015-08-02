package de.devacon.graphics;

import android.graphics.Path;
import android.graphics.RectF;

import java.util.ArrayList;

import de.devacon.exception.NotImplementedException;

/**
 * Created by @Martin@ on 17.07.2015 13:00.
 */
public class Designer {
    private Object3D object ;
    ArrayList<Point3D> resize = new ArrayList<>();
    private int active = -1 ;

    Designer(Object3D object) {
        this.object = object;
        init(object) ;
    }
    private void init(Object3D object) {
        resize.clear();
        if(object instanceof Circle3D) {
            Circle3D circle = (Circle3D)object;
            //addPoint(circle);
            Vector3D v = Vector3D.add(circle, new Vector3D((float) 0.707 * circle.radius, (float) 0.707 * circle.radius, 0));
            addResizePoint(v);
        }
        else if(object instanceof Line3D) {
            Line3D line = (Line3D)object;
            addResizePoint(line);
            addResizePoint(line.end);
        }
        else if(object instanceof PolyLine3D) {
            PolyLine3D poly = (PolyLine3D)object;
            addResizePoint(poly);
            for(Point3D point:poly.points) {
                addResizePoint(point);
            }
        }
    }
    public Point3D getResizePoint(int index) {
        return resize.get(index);
    }
    public ArrayList<Point3D> getResizePoints() {
        return resize;
    }
    public void setResizePoint(int index,Point3D point) {
        if(index >= resize.size()) {
            throw new UnsupportedOperationException();
        }
        Point3D old = resize.get(index);
        resize.set(index,point);

        if(object instanceof Circle3D){
            if(index != 0) {
                throw new UnsupportedOperationException();
            }
            Circle3D circle = (Circle3D)object;
            Vector3D v = Vector3D.subtract(new Vector3D(point),circle);
            circle.setRadius(v.length());
        }
        else if(object instanceof PolyLine3D) {
            PolyLine3D poly = (PolyLine3D)object;
            poly.setPoint(index - 1,point);
        }
    }
    public void addResizePoint(Point3D point) {
        resize.add(point);
    }

    public void drawResizePoints(Path metaPoints,float metaX,float metaY){
        for(Point3D point:resize) {
            drawPoint(metaPoints,point,metaX,metaY);
        }
    }
    public void draw(Path path) throws NotImplementedException {
        if (object instanceof Circle3D) {
            Circle3D circle = (Circle3D)object;
            path.addCircle(circle.x, circle.y, circle.radius, Path.Direction.CW);
        } else if(object instanceof Line3D){
            Line3D line = (Line3D)object;
            path.moveTo(line.x, line.y);
            path.lineTo(line.end.x, line.end.y);
        } else if (object instanceof PolyLine3D) {
            drawPolyLine(path,object);
        }
        else
            throw new NotImplementedException();
    }
    void drawPoint( Path metaPoints,Point3D p,float metaX,float metaY){
        if(metaPoints == null) {
            return;
        }
        RectF rect = new RectF(p.x,p.y,p.x,p.y);
        rect.inset(-metaX,-metaY);
        metaPoints.addRect(rect, Path.Direction.CW);
    }
    void drawPolyLine(Path path, Object3D object) throws NotImplementedException {
        PolyLine3D poly = (PolyLine3D)object;
        path.moveTo(poly.x, poly.y);

        Point3D[] points = new Point3D[3];
        int index = 0 ;
        for(Point3D point : poly.points) {
            if (poly.algorithm == PolyLine3D.Algorithm.STRAIGHT) {
                path.lineTo(point.x, point.y);
            }
            else if(poly.algorithm == PolyLine3D.Algorithm.QUADRATIC) {
                points[index] = point ;
                index++;
                if(index == 2) {
                    index = 0 ;
                    path.quadTo(points[0].x,points[0].y,points[1].x,points[1].y);
                }
            }
            else if(poly.algorithm == PolyLine3D.Algorithm.CUBIC) {
                points[index] = point ;
                index++;
                if(index == 3) {
                    index = 0 ;
                    path.cubicTo(points[0].x,points[0].y,points[1].x,points[1].y,points[2].x,points[2].y);
                }
            }
            else
                throw new NotImplementedException();

        }

    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getActive() {
        return active;
    }

    public boolean findMovePoint(float x, float y,float tolerance) {
        RectF rect = new RectF(x,y,x,y);
        float ax = object.getOrigin().x ;
        float ay = object.getOrigin().y;
        RectF rect2 = new RectF(ax,ay,ax,ay);
        rect.inset(-tolerance,-tolerance);
        rect2.inset(-tolerance,-tolerance);
        if(rect.intersect(rect2)) {
            return true;
        }
        return false;
    }
    public int findResizePoint(float x, float y,float tolerance) {
        int index = 0 ;
        for(index = 0 ; index < resize.size();index++) {
            RectF rect = new RectF(x,y,x,y);
            RectF rect2 = new RectF(resize.get(index).x,resize.get(index).y,resize.get(index).x,resize.get(index).y);
            rect.inset(-tolerance,-tolerance);
            rect2.inset(-tolerance,-tolerance);
            if(rect.intersect(rect2)) {
                return index;
            }

        }
        return -1;
    }

    public void moveResizePoint(float x, float y) {
        if(active != -1) {
            setResizePoint(active, new Point3D(x,y));
            init(object);
        }
    }

    public void moveObject(float x, float y) {
        object.moveTo(new Point3D(x,y));
        init(object);
    }

    public void drawMovePoint(Path meta, float metaX, float metaY) {
        drawPoint(meta,object.getOrigin(),metaX,metaY);
    }
}
