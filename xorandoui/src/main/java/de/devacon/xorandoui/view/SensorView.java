package de.devacon.xorandoui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.devacon.content.GageSink;
import de.devacon.content.GageSource;
import de.devacon.content.StringContentProvider;
import de.devacon.content.StringContentSink;
import de.devacon.systeminfo.SensorInfo;

/**
 * Created by @Martin@ on 22.07.2015 07:08.
 */
public class SensorView extends LinearLayout implements StringContentSink,SensorEventListener,GageSource {
    protected TextView text = null;
    protected LinearLayout group = null;
    protected Sensor sensor = null;
    protected SensorManager manager = null;
    private StringContentProvider contentProvider = null ;
    protected float[] values = null;
    private long timestamp = 0 ;
    private long oldtime = 0;
    private float[] oldValues = null;
    private Sensor sensorBackup = null;
    private GageSink gageSink = null;
    private boolean registered = false;
    Context parent = null;
    /**
     * Called when the visibility of the view or an ancestor of the view is changed.
     *
     * @param changedView The view whose visibility changed. Could be 'this' or
     *                    an ancestor view.
     *
     * @param visibility  The new visibility of changedView: {@link #VISIBLE},
     *                    {@link #INVISIBLE} or {@link #GONE}.
     */
    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if(visibility == INVISIBLE){
            manager.unregisterListener(this);
            registered = false;
        }
        else if(visibility == VISIBLE && !registered) {

            manager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
            registered = true;
        }
    }

    public SensorView(Context parent, SensorManager manager, Sensor sensor) {
        super(parent);
        this.parent = parent;
        this.manager = manager;
        this.sensor = sensor;
        init();
    }
    private void init() {
        text = new TextView(parent);
        group = new LinearLayout(parent);
        group.setOrientation(VERTICAL);
        if(getOrientation() == VERTICAL ) {
            group.setGravity(Gravity.TOP);
            text.setGravity(Gravity.BOTTOM);
        }
        else {
            group.setGravity(Gravity.START);
            text.setGravity(Gravity.END);
        }
        setContentProvider(new SensorInfo(sensor, false));
        text.setText(contentProvider.getContent());
        GageView gageView = new GageView(parent);

        group.addView(gageView);
        group.addView(new GageView(parent));
        group.addView(new GageView(parent));
        group.layout(getLeft(), getTop(), getRight(), getBottom() - 100);
        addView(group);
        text.layout(getLeft(), getBottom() - 100, getRight(), getBottom() );
        addView(text);
        layout(getLeft(),getTop(),getRight(),getBottom());
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        registered = true;
    }

    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public SensorView(Context parent) {
        this(parent, null, null);
    }

    /**
     * Constructor that is called when inflating a view from XML. This is called
     * when a view is being constructed from an XML file, supplying attributes
     * that were specified in the XML file. This version uses a default style of
     * 0, so the only attribute values applied are those in the Context's Theme
     * and the given AttributeSet.
     * <p/>
     * <p/>
     * The method onFinishInflate() will be called after all children have been
     * added.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     * @see #View(Context, AttributeSet)
     */
    public SensorView(Context parent, AttributeSet attrs) {
        this(parent, attrs, 0,null, null);
    }

    /**
     * Perform inflation from XML and apply a class-specific base style. This
     * constructor of View allows subclasses to use their own base style when
     * they are inflating. For example, a Button class's constructor would call
     * this version of the super class constructor and supply
     * <code>R.attr.buttonStyle</code> for <var>defStyle</var>; this allows
     * the theme's button style to modify all of the base view attributes (in
     * particular its background) as well as the Button class's attributes.
     *
     * @param context  The Context the view is running in, through which it can
     *                 access the current theme, resources, etc.
     * @param attrs    The attributes of the XML tag that is inflating the view.
     * @param defStyle The default style to apply to this view. If 0, no style
     *                 will be applied (beyond what is included in the theme). This may
     *                 either be an attribute resource, whose value will be retrieved
     *                 from the current theme, or an explicit style resource.
     * @see #View(Context, AttributeSet, int)
     */
    public SensorView(Context parent, AttributeSet attrs, int defStyle) {
        this(parent, attrs, defStyle, null, null);

    }

    public SensorView(Context parent, AttributeSet attrs, int defStyle, SensorManager sensorManager, Sensor sensor) {
        super(parent,attrs,defStyle);
        this.manager = sensorManager;
        this.sensor = sensor;
        this.parent = parent;
        init();
    }

    /**
     * {@inheritDoc}
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed,l,t,r,b);
    }

    /**
     *
     * @return
     */
    public SensorManager getSensorManager() {
        return manager;
    }

    /**
     *
     * @param manager
     */
    public void setSensorManager(SensorManager manager) {
        this.manager = manager;
    }

    /**
     *
     * @return
     */
    public Sensor getSensor() {
        return sensor;
    }

    /**
     *
     * @param sensor
     */
    public void setSensor(Sensor sensor) {
        this.sensor = sensor;

    }

    @Override
    public void setContentProvider(StringContentProvider provider) {
        contentProvider = provider;
    }

    @Override
    public void onContentChange() {
        if(contentProvider != null) {
            text.setText(contentProvider.getContent());
        }
    }

    /**
     * Called when sensor values have changed.
     * <p>See {@link SensorManager SensorManager}
     * for details on possible sensor types.
     * <p>See also {@link SensorEvent SensorEvent}.
     * <p/>
     * <p><b>NOTE:</b> The application doesn't own the
     * {@link SensorEvent event}
     * object passed as a parameter and therefore cannot hold on o it.
     * The object may be part of an internal pool and may be reused by
     * the framework.
     *
     * @param event the {@link SensorEvent SensorEvent}.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(sensor != event.sensor) {
            sensorBackup = sensor;
            sensor = event.sensor;
        }
        if(values != null) {
            oldValues = values.clone();
            values = event.values.clone();
        }
        else {
            String[] array = new String[]{
                    "X","Y","Z","VALUE1","VALUE2","VALUE3"
            };
            values = event.values.clone();
            if(values.length != event.values.length) {
                group.removeAllViews();
                for(int i = 0 ; i < event.values.length;++i) {
                    GageView view = new GageView(getContext());
                    view.setGageType(GageView.SENSOR);
                    view.setSubType(sensor.getType());
                    view.setValueType(array.length > i ? array[i] : array[5]);
                    view.setGageSource(this);
                    view.setVisibility(VISIBLE);
                    group.addView(view);
                }
            }
        }
        if(timestamp != 0) {
            oldtime = timestamp;
        }

        timestamp = event.timestamp ;
        postInvalidate();
    }

    /**
     * Called when the accuracy of a sensor has changed.
     * <p>See {@link SensorManager SensorManager}
     * for details.
     *
     * @param sensor
     * @param accuracy The new accuracy of this sensor
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        float width = canvas.getWidth();
        float height = canvas.getHeight();
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(width / 2, height / 2, Math.min(height, width) / 2, paint);

    }

    @Override
    public Object getValue(Object which) {
        if(which instanceof String) {
            String what = (String)which;
            if(what.equals("X") && values.length > 0) {
                return new Float(values[0]);
            }
            else if(what.equals("Y") && values.length > 1) {
                return new Float(values[1]);
            }
            else if(what.equals("Z") && values.length > 2) {
                return new Float(values[2]);
            }
            else if(values.length > 3) {
                return new Float(values[3]);
            }
        }
        return null;
    }

    @Override
    public void attachSink(GageSink sink) {
        gageSink = sink ;
    }
}
