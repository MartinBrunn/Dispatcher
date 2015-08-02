package de.devacon.paint;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import de.devacon.exception.NotImplementedException;
import de.devacon.graphics.Graphics;
import de.devacon.graphics.GraphicsObject;

/**
 * Created by @Martin@ on 16.07.2015 16:17.
 */
public class Painter {
    private Canvas canvas = null;
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
    public void draw(PaintLayer layer, boolean drawMeta) throws NotImplementedException {
        if(canvas == null) {
            return;
        }
        for(PaintObject object : layer.getArray()) {
            draw(object,drawMeta) ;
        }
    }
    public void draw(PaintObject object,boolean isActiveLayer) throws NotImplementedException {
        if(canvas == null) {
            return ;
        }
        boolean drawResizePoints = isActiveLayer && object.isResizing() ;
        boolean drawMovePoint = isActiveLayer && object.isMoving();
        Paint paint = new Paint();
        paint.setColor(object.getColor("foreground"));
        int iStyle = object.getStyle("foreground");
        Paint.Style style = (iStyle & 1) == 1? Paint.Style.FILL : (iStyle & 2) == 2 ? Paint.Style.STROKE :
                (iStyle & 3 ) == 3 ? Paint.Style.FILL_AND_STROKE : Paint.Style.STROKE;

        paint.setStyle(style);

        float linewidth = object.getDimension("linewidth");
        paint.setStrokeWidth( linewidth );
        GraphicsObject graph = object.getObject();
        Path path = new Path();
        Path meta = new Path();
        graph.draw(path);
        canvas.drawPath(path, paint);
        if(drawResizePoints) {
            graph.drawResizePoints(meta, 5, 5);
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(meta, paint);

            paint.setStrokeWidth(1);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(meta, paint);
        }
        else if(drawMovePoint) {
            graph.drawMovePoint(meta,20,20) ;
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(meta, paint);

            paint.setStrokeWidth(1);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(meta, paint);
        }
        else {
            Path p = graph.getRegion().getBoundaryPath();
            paint.setStrokeWidth(3);
            paint.setColor(Color.MAGENTA);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawPath(p,paint);
            RectF rect = new RectF();
            p.computeBounds(rect, true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            canvas.drawRect(Graphics.rectFromRectF(rect), paint);
        }
    }

}
