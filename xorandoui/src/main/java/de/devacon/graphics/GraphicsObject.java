package de.devacon.graphics;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;

import java.util.ArrayList;
import java.util.HashMap;

import de.devacon.exception.NotImplementedException;
import de.devacon.inputmethod.Touchable;

/**
 * Created by @Martin@ on 14.07.2015 22:01.
 */
public class GraphicsObject implements Touchable {
    static final String CIRCLE = "circle";
    static final String OVAL = "oval";
    static final String RECTANGLE = "rectangle";
    static final String LINE = "line";
    static final String POLYLINE = "polyline";
    static final String POLYGON = "polygon";
    static final String ARC = "arc";

    public Region region = new Region();
    public HashMap<String,Float> dimensions = new HashMap<>();
    public HashMap<String,String> shapes = new HashMap<>();

    public ArrayList<Point3D> points = new ArrayList<>();
    public HashMap<String,Integer> pointNames = new HashMap<>();
    private ArrayList<String> array = new ArrayList<>();
    public HashMap<String,Object3D> objects = new HashMap<>();
    public HashMap<String,Designer> designers = new HashMap<>();
    private Designer activeDesigner = null;

    public Designer getActiveDesigner() {
        return activeDesigner;
    }

    void addNamedPoint(String name,Point3D point){
        pointNames.put(name,points.size());
        points.add(new Point3D());
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    void setDimension(String which,Float dimension) {
        dimensions.put(which, dimension);
    }
    Float getDimension(String which) {
        return dimensions.get(which);
    }
    void setShape(String which,String shape) {
        shapes.put(which,shape);
    }
    String getShape(String which) {
        return shapes.get(which);
    }
    public void add(String name,Object3D object) {
        array.add(name);
        objects.put(name, object);
        Designer designer = new Designer(object);
        designers.put(name, designer);

    }
    public void draw(Path path) throws NotImplementedException {

        for(String name: array) {
            Designer designer = designers.get(name);
            if(designer != null) {
                designer.draw(path);
            }
        }
        RectF bounds = new RectF();
        path.computeBounds(bounds,true);
        float x = bounds.left + bounds.width()/2;
        float y = bounds.top + bounds.height()/2;
        Matrix matrix = new Matrix();
        matrix.preScale(1.1f, 1.1f, x, y);
        Path dst = new Path();
        path.transform(matrix, dst);
        dst.computeBounds(bounds, true);
        Region clip = new Region(Math.round(bounds.left)-1,Math.round(bounds.top)-1,
                Math.round(bounds.right)+1,Math.round(bounds.bottom)+1);
        region.setPath(dst, clip);
    }
    public void drawResizePoints(Path metaPoints,float metaX,float metaY) {
        for(String name: array) {
            Designer designer = designers.get(name);
            designer.drawResizePoints(metaPoints, metaX, metaY);
        }
    }
    void prepare() {
        for(String name : array) {
            String shape = shapes.get(name);
            if(shape == "circle") {
                float x = dimensions.get(name + "-x");
                float y = dimensions.get(name + "-y");
                float r = dimensions.get(name + "-radius");
                objects.put(name,new Circle3D(new Point3D(x,y,0),r));
            }
        }

    }

    @Override
    public boolean touches(float x, float y,float tolerance) {
        RectF rectF = new RectF(x,y,x,y);
        rectF.inset(-tolerance,-tolerance);
        Rect rect = Graphics.rectFromRectF(rectF);
        Region dst = new Region();
        dst.op(rect, region,Region.Op.INTERSECT);
        if(dst.isEmpty())
            return false;
        return true;
    }

    public boolean treatDetail(float x, float y,float tolerance,boolean bResizing) {
        boolean ret = false;
        for(String name: array) {
            Designer designer = designers.get(name);
            if(designer == null) {
                continue;
            }
            if(bResizing) {
                int index = designer.findResizePoint(x, y, tolerance);
                designer.setActive(index);
                if (index != -1) {
                    ret = true;
                    this.activeDesigner = designer;
                }
            }
            else {
                boolean found = designer.findMovePoint(x,y,tolerance);
                if(found) {
                    ret = true;
                    this.activeDesigner = designer;
                }
            }

        }
        return ret;
    }

    public void drawMovePoint(Path meta, float metaX , float metaY) {
        for(String name: array) {
            Designer designer = designers.get(name);
            designer.drawMovePoint(meta, metaX, metaY);
        }
    }
}
