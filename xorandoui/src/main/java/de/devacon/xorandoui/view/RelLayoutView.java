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
import android.view.SurfaceHolder;
import android.view.View;

import de.devacon.graphics.ResizeFrame;
import de.devacon.xorandoui.R;

/**
 * TODO: document your custom view class.
 */
public class RelLayoutView extends  View implements GestureDetector.OnGestureListener{
    private boolean beginScroll = false;

    /**
     * Notified when a tap occurs with the down {@link MotionEvent}
     * that triggered it. This will be triggered immediately for
     * every down event. All other events should be preceded by this.
     *
     * @param event The down motion event.
     */
    @Override
    public boolean onDown(MotionEvent event) {
        if(disabled || beginScroll) {
            disabled = false;
            return true;
        }
        RectF text = new RectF(mRectText);
        text.inset(0, -mTextHeight);
        if (mTextActive && text.contains(event.getX(),event.getY())) {
            mIconActive = !mIconActive;
            if (!mIconActive) {
                playSoundEffect(SoundEffectConstants.CLICK);
            } else {
                playSoundEffect(SoundEffectConstants.NAVIGATION_UP);
            }
            invalidate();
            disabled = true ;
        } else if (mIconActive && mRectIcon.contains(event.getX(), event.getY())) {
            mTextActive = !mTextActive;
            if (!mTextActive) {
                playSoundEffect(SoundEffectConstants.CLICK);
            } else {
                playSoundEffect(SoundEffectConstants.NAVIGATION_DOWN);
            }
            invalidate();
            disabled = true;
        }
        else {
            int button = mResizeFrame.findButton(event.getX(),event.getY());
            if(button == 0) {
                toggleactivate();
                playSoundEffect(SoundEffectConstants.CLICK);
                disabled = true;
                emphasize = 0 ;
            }
            else {
                emphasize = button;
                postInvalidate();
                beginScroll = true;
            }
            //return super.onTouchEvent(event);
        }
        return true;
    }

    /**
     * The user has performed a down {@link MotionEvent} and not performed
     * a move or up yet. This event is commonly used to provide visual
     * feedback to the user to let them know that their action has been
     * recognized i.e. highlight an element.
     *
     * @param event The down motion event
     */
    @Override
    public void onShowPress(MotionEvent event) {
        if(disabled || beginScroll) {
            return ;
        }
        int button = mResizeFrame.findButton(event.getX(),event.getY());
        if(button == 0) {
            toggleactivate();
            //playSoundEffect(SoundEffectConstants.CLICK);
            disabled = true;
            emphasize = 0 ;
        }
        else {
            emphasize = button;
            beginScroll = true;
            postInvalidate();
        }
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
        beginScroll = false;
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
        if(emphasize != 0 ) {
            switch(emphasize & ResizeFrame.HORIZONTAL_MASK) {
                case ResizeFrame.AT_LEFT:
                    setLeft(Math.round(getLeft() - distanceX));
                    //mRectFrame.offset(distanceX,0);
                    break;
                case ResizeFrame.AT_HCENTER:
                    //mRectFrame.offset(0,distanceY);
                    break;
                case ResizeFrame.AT_RIGHT:
                    setRight(Math.round(getRight() - distanceX));
                    //mRectFrame.set(mRectFrame.left,mRectFrame.top,mRectFrame.right + distanceX,mRectFrame.bottom);
                    break;
            }
            switch(emphasize & ResizeFrame.VERTICAL_MASK) {
                case ResizeFrame.AT_TOP:
                    setTop(Math.round(getTop() - distanceY));
                    //mRectFrame.offset(0,distanceY);
                    break;
                case ResizeFrame.AT_VCENTER:
                    //mRectFrame.offset(0,distanceY);
                    break;
                case ResizeFrame.AT_BOTTOM:
                    setBottom(Math.round(getBottom() - distanceY));
                    //mRectFrame.set(mRectFrame.left,mRectFrame.top,mRectFrame.right,mRectFrame.bottom + distanceY);
                    break;
            }
            postInvalidate();
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

    //extends SurfaceView implements SurfaceHolder.Callback{
    private String mTitleString; // TODO: use a default from R.string...
    private int mFrameColor = Color.RED; // TODO: use a default from R.color...
    private int mShadeColor = Color.rgb(0x80,0,0); // TODO: use a default from R.color...
    private float mFrameWidth = 0; // TODO: use a default from R.dimen...
    private Drawable mBackgroundIcon;
    private ResizeFrame mResizeFrame;
    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    private int mTextColor = Color.BLACK;
    private int paddingLeft;
    private int paddingTop;
    private int paddingRight;
    private int paddingBottom;
    private int contentWidth;
    private int contentHeight;
    private boolean mInitialized = false;
    private int mShadeWidth=2;
    private float mTextStroke;
    private float mTextSize;
    private boolean mTextActive = true;
    private boolean mIconActive = false;
    private RectF mRectText = null;
    private RectF mRectIcon = null;
    private boolean disabled = false;
    private boolean active = false;
    private RectF mRectFrame = null;

    private float mBlackStroke = 2;
    private int emphasize = 0 ;
    private GestureDetector gestureDetector = null;

    //private SurfaceHolder holder ;
    public RelLayoutView(Context context) {
        super(context);
        init(null, 0);
    }

    public RelLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public RelLayoutView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.RelLayoutView, defStyle, 0);


        mTitleString = a.getString(
                R.styleable.RelLayoutView_titleString);
        mFrameColor = a.getColor(
                R.styleable.RelLayoutView_frameColor,
                mFrameColor);
        mShadeColor = a.getColor(
                R.styleable.RelLayoutView_shadeColor,
                mShadeColor);
        mTextColor = a.getColor(
                R.styleable.RelLayoutView_textColor,
                mTextColor);

        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mFrameWidth = a.getDimension(
                R.styleable.RelLayoutView_frameWidth,
                mFrameWidth);
        mTextSize = a.getDimension(
                R.styleable.RelLayoutView_textSize,
                mTextSize);

        if (a.hasValue(R.styleable.RelLayoutView_backgroundIcon)) {
            mBackgroundIcon = a.getDrawable(
                    R.styleable.RelLayoutView_backgroundIcon);
            mBackgroundIcon.setCallback(this);
        }

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mRectText = new RectF();
        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements(null);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleactivate();
            }
        });
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    active = false;
                    invalidate();
                }
            }
        });
        gestureDetector = new GestureDetector(getContext(),this);
/*        holder = getHolder();
        holder.addCallback(this);
        holder.setFormat(PixelFormat.TRANSPARENT);*/

    }

    private void toggleactivate() {
        active = !active;
        invalidate();
    }

    /**
     * Implement this method to handle touch screen motion events.
     *
     * @param event The motion event.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
        /*
        if(event.getAction() !=  MotionEvent.ACTION_DOWN) {
            if(event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                active = false;
                disabled = false;
                invalidate();
            }
            if(event.getAction() == MotionEvent.ACTION_UP)
                disabled = false;
            return false;
        }
        if(disabled) {
            disabled = false ;
            return false;
        }
        RectF text = new RectF(mRectText);
        text.inset(0, -mTextHeight);
        if (mTextActive && text.contains(event.getX(),event.getY())) {
            mIconActive = !mIconActive;
            if (!mIconActive) {
                playSoundEffect(SoundEffectConstants.CLICK);
            } else {
                playSoundEffect(SoundEffectConstants.NAVIGATION_UP);
            }
            invalidate();
            disabled = true ;
        } else if (mIconActive && mRectIcon.contains(event.getX(), event.getY())) {
            mTextActive = !mTextActive;
            if (!mTextActive) {
                playSoundEffect(SoundEffectConstants.CLICK);
            } else {
                playSoundEffect(SoundEffectConstants.NAVIGATION_DOWN);
            }
            invalidate();
            disabled = true;
        }
        else {
            int button = mResizeFrame.findButton(event.getX(),event.getY());
            if(button == 0) {
                toggleactivate();
                playSoundEffect(SoundEffectConstants.CLICK);
                disabled = true;
                emphasize = 0 ;
            }
            else {
                emphasize = button;
                invalidate();
            }
            //return super.onTouchEvent(event);
        }
        return true;*/
    }

    /**
     * This is called during layout when the size of this view has changed. If
     * you were just added to the view hierarchy, you're called with the old
     * values of 0.
     *
     * @param w    Current width of this view.
     * @param h    Current height of this view.
     * @param oldw Old width of this view.
     * @param oldh Old height of this view.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mInitialized = false;
    }

    private void invalidateTextPaintAndMeasurements(Canvas canvas) {
        mTextPaint.setTextSize(mTextSize);
        mTextStroke = mTextPaint.getStrokeWidth();
        mTextPaint.setColor(mFrameColor);
        mTextWidth = mTextPaint.measureText(mTitleString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom - fontMetrics.top ;
        mShadeWidth = Math.round(mFrameWidth / 3);


        float x = ( paddingLeft + (contentWidth - mTextWidth) / 2) ;
        float y = paddingTop + mTextHeight ; // ( paddingTop + (contentHeight + mTextHeight) / 2);
        Rect rect = new Rect();

        mTextPaint.getTextBounds(mTitleString, 0, mTitleString.length(), rect);
        mRectText.set(x, y, x + (rect.right - rect.left), y + (rect.bottom - rect.top));

        mRectIcon = new RectF(paddingLeft, paddingTop+ mTextHeight ,
                paddingLeft + contentWidth, paddingTop + contentHeight);
        if(canvas != null) {
            mRectFrame = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
            mRectFrame.inset(1, 1);
            mRectFrame.offset(1,1);
        }
        else
            mRectFrame = new RectF();
        if(mResizeFrame == null){
            mResizeFrame = new ResizeFrame(mRectFrame);
        }
        else {
            mResizeFrame.setSize(25);
            mResizeFrame.set(mRectFrame);
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        doDrawing(canvas);
    }
    protected void doDrawing(Canvas canvas) {
        //super.onDraw(canvas);
        //canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        if(!mInitialized) {
            initializeDimensions(canvas);
            getBackground().setAlpha(0);
            //super.setAlpha(0x7f);

        }
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Draw the text.

        drawFrame(canvas);

        drawText(canvas);
        // Draw the example drawable on top of the text.
        drawIcon(canvas);
        if(active) {
            drawBlackFrame(canvas);
        }
    }
    private void drawRect(Canvas canvas,Paint paint,ResizeFrame.RectObject rect){

        canvas.drawRect(rect,paint);
    }
    private void drawBlackFrame(final Canvas canvas) {
        mTextPaint.setStrokeWidth(mBlackStroke);
        mTextPaint.setAlpha(0xff);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(mResizeFrame, mTextPaint);
        final Paint paint = new Paint();
        paint.setStrokeWidth(mBlackStroke);
        paint.setAlpha(0xff);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        mResizeFrame.foreach(new ResizeFrame.Processor() {
            @Override
            public boolean process(ResizeFrame.RectObject rect) {
                if(emphasize == rect.orientation) {
                    paint.setColor(Color.GREEN);
                }
                else {
                    paint.setColor(Color.RED);
                }

                drawRect(canvas,paint,rect);
                return true;
            }
        });
    }

    private void drawIcon(Canvas canvas) {
        if (mIconActive && mBackgroundIcon != null) {
            Rect rect = new Rect();
            mRectIcon.round(rect);
            mBackgroundIcon.setBounds(rect);
            rect = null;
            mBackgroundIcon.draw(canvas);
        }

    }

    private void drawText(Canvas canvas) {
        if(mTextActive) {

            mTextPaint.setColor(mTextColor);
            mTextPaint.setStrokeWidth(mTextStroke);
            //mRectText.offset(10,0);
            if(mRectText.centerX()>=getRight())
                mRectText.set(1,mRectText.top,mRectText.width(),mRectText.height());
            canvas.drawText(mTitleString, mRectText.left, mRectText.top, mTextPaint);

        }

    }

    private void drawFrame(Canvas canvas) {
        float shadeStroke = (mFrameWidth * 1) / 3;
        float frameStroke = (mFrameWidth * 2) / 3 ;
        int frameReduce = Math.round(frameStroke) ;
        int shadeReduce = Math.round(shadeStroke);
        int shadeOffset = Math.round(shadeStroke/2);
        int frameOffset = Math.round(frameStroke/2);
        Rect rectShade = new Rect(shadeOffset,shadeOffset,getWidth() - shadeReduce,getHeight() - shadeReduce);
        Rect rectFrame = new Rect(frameOffset,frameOffset,getWidth() - Math.round(mShadeWidth) - frameOffset,getHeight()- frameReduce);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setStrokeWidth(shadeStroke);
        mTextPaint.setColor(mShadeColor);
        canvas.drawRect(rectShade, mTextPaint);
        mTextPaint.setStrokeWidth(frameStroke);
        mTextPaint.setColor(mFrameColor);
        canvas.drawRect(rectFrame, mTextPaint);

    }

    private void initializeDimensions(Canvas canvas) {
        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();

        contentWidth = getWidth() - paddingLeft - paddingRight;
        contentHeight = getHeight() - paddingTop - paddingBottom;
        mInitialized = true;
        invalidateTextPaintAndMeasurements(canvas);
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getTitleString() {
        return mTitleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setTitleString(String exampleString) {
        mTitleString = exampleString;
        mInitialized = false;
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getFrameColor() {
        return mFrameColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setFrameColor(int exampleColor) {
        mFrameColor = exampleColor;
        mInitialized = false;
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getFrameWidth() {
        return mFrameWidth;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param frameWidth The frameWidth attribute value to use.
     */
    public void setFrameWidth(float frameWidth) {
        mFrameWidth = frameWidth;
        mInitialized = false;
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The backgroundIcon attribute value.
     */
    public Drawable getBackgroundIcon() {
        return mBackgroundIcon;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param backgroudIcon The attribute value to use.
     */
    public void setBackgroundIcon(Drawable backgroudIcon) {
        mBackgroundIcon = backgroudIcon;
    }

    /*
     * This is called immediately after the surface is first created.
     * Implementations of this should start up whatever rendering code
     * they desire.  Note that only one thread can ever draw into
     * a {@link Surface}, so you should not draw into the Surface here
     * if your normal rendering will be in another thread.
     *
     * @param holder The SurfaceHolder whose surface is being created.
     */
    //@Override
    public void surfaceCreated(SurfaceHolder holder) {
        setBackgroundColor(Color.TRANSPARENT);
    }

    /**
     * This is called immediately after any structural changes (format or
     * size) have been made to the surface.  You should at this point update
     * the imagery in the surface.  This method is always called at least
     * once, after {@link #surfaceCreated}.
     *
     * @param holder The SurfaceHolder whose surface has changed.
     * @param format The new PixelFormat of the surface.
     * @param width  The new width of the surface.
     * @param height The new height of the surface.
     */
  //  @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Canvas canvas = holder.lockCanvas();
        if(canvas != null) {
            doDrawing(canvas);
        }
        holder.unlockCanvasAndPost(canvas);
    }

    /**
     * This is called immediately before a surface is being destroyed. After
     * returning from this call, you should no longer try to access this
     * surface.  If you have a rendering thread that directly accesses
     * the surface, you must ensure that thread is no longer touching the
     * Surface before returning from this function.
     *
     * @param holder The SurfaceHolder whose surface is being destroyed.
     */
//    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
