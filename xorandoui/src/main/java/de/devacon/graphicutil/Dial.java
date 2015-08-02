package de.devacon.graphicutil;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import de.devacon.graphics.Vector3D;

/**
 * Created by @Martin@ on 21.07.2015 11:39.
 */
public class Dial {

    static final int LINE = 1;

    static final int TRIANGLE = 3;
    private float textSize = 20;
    private int textColor = Color.BLACK ;

    enum TextPlacement {
        NONE ,
        INSIDE ,
        OUTSIDE,
    }
    enum TextDirection {
        HORIZONTAL,
        VERTICAL,
        RADIAL,
    }
    protected TextPlacement placement = TextPlacement.INSIDE;
    protected TextDirection direction = TextDirection.HORIZONTAL;

    protected Vector3D position ;
    protected float radius = 40 ;
    protected float lineWidth = 1;
    protected int lineColor = Color.DKGRAY;
    protected int pitchLineColor = Color.BLACK;
    protected int pitchLineCount = 12;
    protected float pitchLineWidthFactor = 0.008f  ;
    protected float pitchLineLengthFactor = 0.08f;

    protected boolean withPitchLines = true ;
    protected boolean withCircle = true ;

    protected int pitchLineStyle = TRIANGLE ;

    protected String[] text = new String[pitchLineCount];

    public Dial() {
    }

    public Dial(Vector3D position, float radius) {
        this.position = position;
        this.radius = radius;
    }

    public Dial(Vector3D position, float radius,int pitchLineStyle, int pitchLineCount,
                int lineColor, int pitchLineColor, float lineWidth,
                boolean withPitchLines,float pitchLineLengthFactor, float pitchLineWidthFactor,
                boolean withCircle) {
        this.lineColor = lineColor;
        this.lineWidth = lineWidth;
        this.pitchLineColor = pitchLineColor;
        this.pitchLineCount = pitchLineCount;
        this.pitchLineLengthFactor = pitchLineLengthFactor;
        this.pitchLineStyle = pitchLineStyle;
        this.pitchLineWidthFactor = pitchLineWidthFactor;
        this.position = position;
        this.radius = radius;
        this.withCircle = withCircle;
        this.withPitchLines = withPitchLines;
    }

    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }
    public void setPosition(float x,float y) {
        this.position = new Vector3D(x,y,0);
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public float getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    public int getPitchLineColor() {
        return pitchLineColor;
    }

    public void setPitchLineColor(int pitchLineColor) {
        this.pitchLineColor = pitchLineColor;
    }

    public int getPitchLineCount() {
        return pitchLineCount;
    }

    public void setPitchLineCount(int pitchLineCount) {
        this.pitchLineCount = pitchLineCount;
    }

    public float getPitchLineLengthFactor() {
        return pitchLineLengthFactor;
    }

    public void setPitchLineLengthFactor(float pitchLineLengthFactor) {
        this.pitchLineLengthFactor = pitchLineLengthFactor;
    }

    public int getPitchLineStyle() {
        return pitchLineStyle;
    }

    public void setPitchLineStyle(int pitchLineStyle) {
        this.pitchLineStyle = pitchLineStyle;
    }

    public float getPitchLineWidthFactor() {
        return pitchLineWidthFactor;
    }

    public void setPitchLineWidthFactor(float pitchLineWidthFactor) {
        this.pitchLineWidthFactor = pitchLineWidthFactor;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public boolean hasCircle() {
        return withCircle;
    }

    public void setWitchCircle(boolean withCircle) {
        this.withCircle = withCircle;
    }

    public boolean hasPitchLines() {
        return withPitchLines;
    }

    public void setWithPitchLines(boolean withPitchLines) {
        this.withPitchLines = withPitchLines;
    }

    protected void drawCircle(Path path){
        path.addCircle(position.x,position.y,radius, Path.Direction.CW);
    }
    protected void drawPitchLine(Path path,int index,float emphasize) {
        float angle = (float) (2*Math.PI)*((float)index/(float)pitchLineCount);
        float len = radius*pitchLineLengthFactor*emphasize;
        switch (pitchLineStyle){
            case TRIANGLE:
                PitchTriangle triangle = new PitchTriangle(position.x,position.y,radius,angle,
                            len,pitchLineWidthFactor);
                triangle.draw(path);
                break;
            case LINE:
                // TODO this is just a dummy. it must be replaced by something calculating the widthFactor
                PitchTriangle triangle2 = new PitchTriangle(position.x,position.y,radius,angle,
                        len,0);
                triangle2.draw(path);
                break;
        }
    }
    protected void drawPitchLine(Path path,int index) {
        drawPitchLine(path,index,1);
    }
    protected void drawPitchLines(Path path) {
        for(int i = 0 ; i < getPitchLineCount() ; ++i){
            drawPitchLine(path, i);
        }
    }
    protected final void drawText(Canvas canvas) {
        float textRadius = 0 ;
        Paint paint = new Paint();
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        for(int index = 0 ; index < pitchLineCount ; index++ ) {
            float textWidth = paint.measureText(text[index]);
            Paint.FontMetrics metrics = paint.getFontMetrics();
            float textHeight = metrics.bottom - metrics.top;
            textRadius = Math.max(textRadius,Math.max(textHeight,textWidth));
        }
        if(placement == TextPlacement.INSIDE) {
            textRadius = radius - textRadius;
        }
        else {
            textRadius = radius + textRadius;
        }
        drawText(canvas,textRadius);
    }

    protected void drawText(Canvas canvas, float textRadius) {
        for(int index = 0 ; index < pitchLineCount ; index++ ) {
            drawText(canvas, index, textRadius,1) ;
        }
    }

    protected void drawText(Canvas canvas, int index,float textRadius,float emphasize) {

        if(index >= text.length )
            return;
        Paint paint = new Paint();
        paint.setColor(textColor);
        paint.setTextSize(textSize*emphasize);
        float textWidth = paint.measureText(text[index]);
        Paint.FontMetrics metrics = paint.getFontMetrics();
        float textHeight = metrics.bottom - metrics.top;

        Vector3D vector = calcPosition(index,textRadius,textWidth,textHeight);
        if(vector == null)
            return ;
        canvas.drawText(text[index],vector.x,vector.y,paint);

    }

    private Vector3D calcPosition(int index, float textRadius,float textWidth, float textHeight) {
        int quad = getQuadrant(index);
        float angle = (float) (index * 2*Math.PI/(float)pitchLineCount);
        float x,y;
        if( (index%4) == 0 || true ) {
            x = (float)Math.cos(angle)*textRadius;
            y = (float)Math.sin(angle)*textRadius;
            switch(quad) {
                case 0:
                    x -= textWidth;
                    y += textHeight/2;
                    break ;
                case 1:
                    x -= textWidth/2;
                    y += textHeight/2;
                    break ;
                case 2:
                    //x -= textWidth/2;
                    y += textHeight/2;
                    break ;
                case 3:
                    x -= textWidth/2;
                    y += textHeight/2;
                    break ;
            }
            Vector3D vector = new Vector3D(x,y,0);
            vector.add(position);
            return vector;
        }
        return null;
    }

    private int getQuadrant(int index) {
        return index/(pitchLineCount/4);
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
//        paint.setStrokeWidth(lineWidth);
        paint.setStrokeWidth(4);
        paint.setColor(pitchLineColor);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Path path = new Path();
        drawPitchLines(path);
        canvas.drawPath(path, paint);

        paint.setColor(lineColor);
        paint.setStrokeWidth(lineWidth);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(position.x,position.y,radius,paint);
        drawText(canvas);
    }
}
