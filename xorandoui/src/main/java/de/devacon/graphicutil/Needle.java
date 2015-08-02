
package de.devacon.graphicutil;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import de.devacon.graphics.Point3D;

public class Needle {
    public class Colors {
        int background = Color.LTGRAY;
        int outline = Color.DKGRAY;
        int north = Color.BLUE;
        int south = Color.RED;
        int northShade = Color.CYAN;
        int southShade = Color.MAGENTA;
        int hub = Color.BLACK;

        public int getBackground() {
            return background;
        }

        public void setBackground(int background) {
            this.background = background;
        }

        public int getHub() {
            return hub;
        }

        public void setHub(int hub) {
            this.hub = hub;
        }

        public int getNorth() {
            return north;
        }

        public void setNorth(int north) {
            this.north = north;
        }

        public int getNorthShade() {
            return northShade;
        }

        public void setNorthShade(int northShade) {
            this.northShade = northShade;
        }

        public int getOutline() {
            return outline;
        }

        public void setOutline(int outline) {
            this.outline = outline;
        }

        public int getSouth() {
            return south;
        }

        public void setSouth(int south) {
            this.south = south;
        }

        public int getSouthShade() {
            return southShade;
        }

        public void setSouthShade(int southShade) {
            this.southShade = southShade;
        }
    }
    Colors colors = new Colors();
    boolean hasHub = true;
    boolean hasShade = true;
    float widthFactor = 0.12f;
    float peakFactor = 0.1f;
    float lenFactor = 0.9f;
    float hubSizeFactor = 0.05f;
    float lineWidth = 1;

    public boolean isHasHub() {
        return hasHub;
    }

    public void setHasHub(boolean hasHub) {
        this.hasHub = hasHub;
    }

    public boolean isHasShade() {
        return hasShade;
    }

    public void setHasShade(boolean hasShade) {
        this.hasShade = hasShade;
    }

    public float getHubSizeFactor() {
        return hubSizeFactor;
    }

    public void setHubSizeFactor(float hubSizeFactor) {
        this.hubSizeFactor = hubSizeFactor;
    }

    public float getLenFactor() {
        return lenFactor;
    }

    public void setLenFactor(float lenFactor) {
        this.lenFactor = lenFactor;
    }

    public float getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    public float getPeakFactor() {
        return peakFactor;
    }

    public void setPeakFactor(float peakFactor) {
        this.peakFactor = peakFactor;
    }

    public float getWidthFactor() {
        return widthFactor;
    }

    public void setWidthFactor(float widthFactor) {
        this.widthFactor = widthFactor;
    }

    public Colors getColors() {
        return colors;
    }

    public void setColors(Colors colors) {
        this.colors = colors;
    }

    public void draw(Canvas canvas, Point3D point, float radius,float angle) {
        Paint paint = new Paint();
        paint.setStrokeWidth(lineWidth);
        float r = radius *lenFactor;
        float d = radius*widthFactor;
        float xoffset = point.x ;
        float yoffset = point.y;
        double sina = Math.sin(angle)*r;
        double cosa = Math.cos(angle)*r;
        double sinc = sina*peakFactor;
        double cosc = cosa*peakFactor;
        double sinb = Math.sin(angle + Math.PI / 2)*d;
        double cosb = Math.cos(angle + Math.PI / 2)*d;


        float [] points = new float[ 12 ];
        float x = xoffset - (float)cosa;
        float y = yoffset - (float)sina;


        Path outline = new Path();
        outline.moveTo(x, y);
        outline.lineTo(xoffset - (float) cosb, yoffset - (float) sinb);
        outline.moveTo(xoffset + (float) cosa, yoffset + (float) sina);
        outline.lineTo(xoffset + (float) cosb, yoffset + (float) sinb);
        outline.lineTo(x, y);
        paint.setColor(colors.background);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(outline, paint);

        Path path = new Path();
        path.moveTo(x, y);
        path.lineTo(xoffset + (float) cosb, yoffset + (float) sinb);
        path.lineTo(xoffset - (float) cosc, yoffset - (float) sinc);
        path.lineTo(x, y);

        paint.setColor(colors.northShade);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);

        path = new Path();
        path.moveTo(x, y);
        path.lineTo(xoffset - (float) cosc, yoffset - (float) sinc);
        path.lineTo(xoffset - (float) cosb, yoffset - (float) sinb);
        path.lineTo(x, y);

        paint.setColor(colors.north);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);


        path = new Path();

        path.moveTo(xoffset + (float) cosa, yoffset + (float) sina);
        path.lineTo(xoffset + (float) cosb, yoffset + (float) sinb);
        //path.lineTo(xoffset, yoffset);
        path.lineTo(xoffset + (float)cosc, yoffset + (float)sinc );
        path.moveTo(xoffset + (float) cosa, yoffset + (float) sina);
        paint.setColor(colors.southShade);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);
        path = new Path();

        path.moveTo(xoffset + (float) cosa, yoffset + (float) sina);
        path.lineTo(xoffset - (float) cosb, yoffset - (float) sinb);
        path.lineTo(xoffset + (float) cosc, yoffset + (float) sinc);
        path.lineTo(xoffset + (float) cosa, yoffset + (float) sina);

        paint.setColor(colors.south);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);

        paint.setColor(colors.outline);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(outline, paint);
        paint.setColor(colors.hub);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(point.x, point.y, radius * hubSizeFactor, paint);
        paint.setColor(colors.outline);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(point.x, point.y, radius * hubSizeFactor, paint);

    }
}
