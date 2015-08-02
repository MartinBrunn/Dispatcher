package de.devacon.xorandoui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;

import de.devacon.content.GageSink;
import de.devacon.content.GageSource;

/**
 * Created by @Martin@ on 22.07.2015 14:07.
 */
public class GageView extends View implements GageSink {
    static final int SENSOR = 1;

    int gageType = SENSOR;
    int subType = 0 ;
    private Object valueType = null;
    private float angle = 0 ;

    public int getGageType() {
        return gageType;
    }

    public void setGageType(int gageType) {
        this.gageType = gageType;
    }

    public int getSubType() {
        return subType;
    }

    public void setSubType(int subType) {
        this.subType = subType;
    }

    GageSource gage = null;
    public GageView(Context context) {
        super(context);
    }

    @Override
    public void onChanged() {
        if(gage != null) {
            Object all = gage.getValue(valueType);
            if(all instanceof Float) {
                angle = ((Float)all).floatValue();
            }
        }
    }

    @Override
    public void setGageSource(GageSource gageSource) {
        gage = gageSource;
    }

    public void setValueType(Object s) {
        valueType = s;
    }

    /**
     * Implement this to do your drawing.
     *
     * @param canvas the canvas on which the background will be drawn
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = canvas.getWidth();
        float height = canvas.getHeight();
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(width/2,height/2,Math.min(height,width)/2,paint);
    }
}
