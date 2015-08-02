package de.devacon.xorandoui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;

import java.util.ArrayList;

import de.devacon.exception.NotImplementedException;
import de.devacon.graphics.Circle3D;
import de.devacon.graphics.Designer;
import de.devacon.graphics.Graphics;
import de.devacon.graphics.GraphicsObject;
import de.devacon.graphics.Object3D;
import de.devacon.graphics.Point3D;
import de.devacon.graphics.PolyLine3D;
import de.devacon.paint.PaintLayer;
import de.devacon.paint.PaintObject;
import de.devacon.paint.Painter;
import de.devacon.xorandoui.R;

/**
 * TODO: document your custom view class.
 */
public class PaintView extends View implements GestureDetector.OnGestureListener {
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;
    private boolean dragActive = false;
    private boolean objectActive = false;
    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    private Painter painter = new Painter();
    private ArrayList<PaintLayer> layers = new ArrayList<>();
    private int activeLayer = 0 ;
    private GestureDetector gestureDetector = null;
    private Rect rectTouch = new Rect();
    private Designer designer = null;
    private PaintObject pObject = null;

    public PaintView(Context context) {
        super(context);
        init(null, 0);
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PaintView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.PaintView, defStyle, 0);
        PaintLayer layer = new PaintLayer();

        try {
            PaintObject object = new PaintObject();


            GraphicsObject gObject = new GraphicsObject();

            Object3D obj3D = new Circle3D(new Point3D(200, 200, 0), 200);
            gObject.add("circle1",obj3D);
            Object3D obj3D_2 = new Circle3D(new Point3D(400, 400, 0), 100);
            gObject.add("circle2", obj3D_2);

            object.setObject(gObject);
            object.setColor("foreground", Color.BLUE);
            object.setDimension("linewidth",2.f);

            layer.addObject(object);

        }
        catch(Exception e) {
            e.printStackTrace();
        }
        PaintObject object = new PaintObject();
        GraphicsObject rectangle = new GraphicsObject();

        PolyLine3D poly = new PolyLine3D(new Point3D(400, 600));
        poly.addPoint(new Point3D(460, 600));
        poly.addPoint(new Point3D(460, 660));
        poly.addPoint(new Point3D(400, 660));
        poly.addPoint(new Point3D(400, 600));
        rectangle.add("rectangle1", poly);
        object.setObject(rectangle);
        object.setColor("foreground", (Color.GREEN >> 1) & Color.GREEN);
        object.setDimension("linewidth", 3.f);
        layer.addObject(object);

        layers.add(layer);

        gestureDetector = new GestureDetector(getContext(),this);

        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        if(mExampleString == null){
            mExampleString = "Text";
        }
        /*if (a.hasValue(R.styleable.PaintView_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.PaintView_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }*/

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
        //setOnDragListener(this);
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mExampleDimension);
        mTextPaint.setColor(mExampleColor);
        mTextWidth = mTextPaint.measureText("Text");

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    /**
     * Implement this method to handle touch screen motion events.
     *
     * @param event The motion event.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            if(dragActive) {
                if(pObject.isResizing()) {
                    designer.moveResizePoint(event.getX(), event.getY());
                }
                else if(pObject.isMoving()) {
                    designer.moveObject(event.getX(), event.getY());
                }
                postInvalidate();
                return true;
            }
        }
        else if(event.getAction() == MotionEvent.ACTION_UP) {
            if(dragActive) {
                dragActive = false;
                postInvalidate();

                return true;
            }
        }
        return gestureDetector.onTouchEvent(event);
        //return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        painter.setCanvas( canvas );
        try {
            for(int i = 0 ; i < layers.size() ; i++) {
                if(layers.get(i).isVisible()) {
                    painter.draw(layers.get(i),i == activeLayer);
                }
            }
        } catch (NotImplementedException e) {
            e.printStackTrace();
        }
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.YELLOW);
        canvas.drawRect(rectTouch,paint);
        if(true)
            return;
        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the text.
        canvas.drawText(mExampleString,
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight) / 2,
                mTextPaint);

        // Draw the example drawable on top of the text.
        if (mExampleDrawable != null) {
            mExampleDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            mExampleDrawable.draw(canvas);
        }
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }

    /**
     * Notified when a tap occurs with the down {@link MotionEvent}
     * that triggered it. This will be triggered immediately for
     * every down event. All other events should be preceded by this.
     *
     * @param e The down motion event.
     */
    @Override
    public boolean onDown(MotionEvent e) {
        boolean found = false;
        float x = e.getX();
        float y = e.getY();
        float tolerance = 5 ;
        if(dragActive) {
            return true;
        }
        for( PaintObject p : layers.get(activeLayer).array) {
            if(p.touches(e.getX(),e.getY(),5)){
                if(p.isResizing() || p.isMoving()) {
                    if(p.treatDetail(e.getX(),e.getY(),20)) {
                        dragActive = true;
                        designer = p.getObject().getActiveDesigner();
                        pObject = p ;
                    }
                    else
                        p.advanceState();
                }
                else {
                    p.advanceState();
                    found = true;
                }
            }
            else {
                if(!p.isInActive()) {
                    found = true;
                }
                p.setInActive();
            }
        }
        RectF rectF = new RectF(x,y,x,y);
        rectF.inset(-tolerance,-tolerance);
        rectTouch = Graphics.rectFromRectF(rectF);

        if(found) {
            postInvalidate();
            playSoundEffect(SoundEffectConstants.CLICK);

            return true;
        }
        else {
            postInvalidate();
        }
        return false;
    }

    /**
     * The user has performed a down {@link MotionEvent} and not performed
     * a move or up yet. This event is commonly used to provide visual
     * feedback to the user to let them know that their action has been
     * recognized i.e. highlight an element.
     *
     * @param e The down motion event
     */
    @Override
    public void onShowPress(MotionEvent e) {

    }

    /**
     * Notified when a tap occurs with the up {@link MotionEvent}
     * that triggered it.
     *
     * @param e The up motion event that completed the first tap
     * @return true if the event is consumed, else false
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    /**
     * Notified when a scroll occurs with the initial on down {@link MotionEvent} and the
     * current move {@link MotionEvent}. The distance in x and y is also supplied for
     * convenience.
     *
     * @param e1        The first down motion event that started the scrolling.
     * @param e2        The move motion event that triggered the current onScroll.
     * @param distanceX The distance along the X axis that has been scrolled since the last
     *                  call to onScroll. This is NOT the distance between {@code e1}
     *                  and {@code e2}.
     * @param distanceY The distance along the Y axis that has been scrolled since the last
     *                  call to onScroll. This is NOT the distance between {@code e1}
     *                  and {@code e2}.
     * @return true if the event is consumed, else false
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if(dragActive) {

            return true;
        }
        return false;
    }

    /**
     * Notified when a long press occurs with the initial on down {@link MotionEvent}
     * that trigged it.
     *
     * @param e The initial on down motion event that started the longpress.
     */
    @Override
    public void onLongPress(MotionEvent e) {

    }

    /**
     * Notified of a fling event when it occurs with the initial on down {@link MotionEvent}
     * and the matching up {@link MotionEvent}. The calculated velocity is supplied along
     * the x and y axis in pixels per second.
     *
     * @param e1        The first down motion event that started the fling.
     * @param e2        The move motion event that triggered the current onFling.
     * @param velocityX The velocity of this fling measured in pixels per second
     *                  along the x axis.
     * @param velocityY The velocity of this fling measured in pixels per second
     *                  along the y axis.
     * @return true if the event is consumed, else false
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

}
