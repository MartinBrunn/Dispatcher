package de.devacon.xorandoui.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.preference.ListPreference;
import android.preference.Preference;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.devacon.content.StringContentProvider;
import de.devacon.graphics.NormalVector3D;
import de.devacon.graphics.Point3D;
import de.devacon.graphics.Vector3D;

import de.devacon.graphicutil.Needle;
import de.devacon.uiutil.GraphicUtil;
import de.devacon.util.Util;
import de.devacon.xorandoui.R;

/**
 * TODO: document your custom view class.
 */
public class ActiveView extends View implements SensorEventListener  {
    private static final int TYPE_MAGNETIC_FIELD = Sensor.TYPE_MAGNETIC_FIELD;
    private static final int TYPE_GYROSCOPE = Sensor.TYPE_GYROSCOPE;
    private static final int TYPE_ACCELEROMETER = Sensor.TYPE_ACCELEROMETER;
    private static final int TYPE_ROTATION_VECTOR = Sensor.TYPE_ROTATION_VECTOR;
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;
    private String type;
    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    private long timer = 0 ;
    private long compare = 0;
    private String [] list = null;
    int contentWidth;
    int contentHeight;
    private Point3D[] point = new Point3D[3];
    private float radius= 40;
    private float lineWidth = 4;
    private int[] color = new int[3];
    private boolean initialized = false;
    private float[] angle = new float[3];
    SensorManager sensorManager = null;
    private Sensor sensor = null;
    private Context context = null;
    HashMap<String,Sensor> map = new HashMap<>();


    public ActiveView(Context context) {
        super(context);

        init(null, 0);
    }

    public ActiveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ActiveView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }
    public void init(String type) {
        this.type = type;
        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> ar = null;
        int index = 0 ;
        int iType = TYPE_ACCELEROMETER;
        switch(type){
            case "MAGNETOMETER":
                iType = TYPE_MAGNETIC_FIELD;
                break;
            case "ACCEL3":
                index++;
            case "ACCEL2":
                index++;
            case "ACCELEROMETER":
                iType = TYPE_ACCELEROMETER;
                break ;
            case "ORIENTATION":
                iType = Sensor.TYPE_ORIENTATION;
                break;
            case "ALL":
                iType = Sensor.TYPE_ALL;
                break ;
        }
        ar = sensorManager.getSensorList(iType);
        //TYPE_MAGNETIC_FIELD);
        if(iType == Sensor.TYPE_ALL){
            for(Sensor sensor: ar){
                map.put(sensor.getName(),sensor);
            }
            list = new String[map.size()];
            map.keySet().toArray(list);
        }
        else if(ar.size() > index) {
            sensor = ar.get(index);
        }

        if(sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }
    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        context = getContext();
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ActiveView, defStyle, 0);


/*        mExampleString = a.getString(
                R.styleable.ActiveView_exampleString);
        mExampleColor = a.getColor(
                R.styleable.ActiveView_exampleColor,
                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.ActiveView_exampleDimension,
                mExampleDimension);

        if (a.hasValue(R.styleable.ActiveView_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.ActiveView_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }
        */
        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }
    private String printTime(long timestamp) {
        if(timer == 0) {
            timer = timestamp;
            Date now = new Date();
            compare = now.getTime();
        }

        return DateFormat.getTimeInstance(DateFormat.MEDIUM).format(new Date((timestamp - timer)/1000000 + compare));
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        boolean display = false;
        String time = printTime(event.timestamp);

        angle = event.values;

        postInvalidate();
    }
    void log(String tag,float[]angle){
        Log.i(tag," : " + Util.toString(angle[0]  ) + ";" +
                Util.toString(angle[1]) + ";" +
                Util.toString(angle[2])) ;
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

    private void invalidateTextPaintAndMeasurements() {
        //mTextPaint.setTextSize(mExampleDimension);
        //mTextPaint.setColor(mExampleColor);
        //mTextWidth = mTextPaint.measureText(mExampleString);

        //Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        //mTextHeight = fontMetrics.bottom;
        color[0] = Color.RED;
        color[1] = Color.BLUE;
        color[2] = Color.MAGENTA;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        contentWidth  = getWidth() - paddingLeft - paddingRight;
        contentHeight = getHeight() - paddingTop - paddingBottom;
        lineWidth = 2;
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
        initialized = false;
        super.onSizeChanged(w, h, oldw, oldh);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(!initialized){
            initializeDraw(canvas);
        }
        if(sensor == null)
            return;
        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        Paint paint = new Paint();
        float magnitude         = 0;
        NormalVector3D unity    = null;
        float[] angles          = new float[3];
        if(angle.length >= 3) {
            Vector3D tesla = new Vector3D(angle[0], angle[1], angle[2]);
            magnitude         = tesla.length();
            unity    = new NormalVector3D(tesla);
            angles          = unity.getAngles().clone();

            log(type + "/angle:",angle);
            log(type + "/angles:", angles);
        }
        else {
            angles = new float[angle.length];
            for(int i = 0 ; i < angle.length; ++i) {
                angles[ i ] = angle[ i ];
            }
        }
        Needle needle = new Needle();
        String str = "";
        Util util = new Util();
        util.setFloatFormat(" %04.02f");
        int cnt = angles.length;
        for(int i = 0 ; i < cnt && i < 3; i++) {
            drawCircle(canvas, point[i], paint);

            if(sensor.getType() == Sensor.TYPE_ORIENTATION && angle.length > i) {
                needle.draw(canvas, point[i], radius, angle[i]);
                str = str + util.toString(angle[i]);
            }
            else if(angle.length > i){
                needle.draw(canvas, point[i], radius, angles[i]);
                str = str + util.toString(angles[i]);
            }
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
        }
        String sTesla = util.toString(magnitude);
        String text = "";
        if(sensor.getType() == TYPE_MAGNETIC_FIELD) {
            sTesla = sTesla + " µTesla";
            text = "Magnetfeld Detektor";
        }
        else if(sensor.getType() == TYPE_ACCELEROMETER) {
            sTesla = sTesla + " m/s²";
            text = "Beschleunigungssensor";
        }
        else if(sensor.getType() == Sensor.TYPE_ORIENTATION) {
            sTesla = sTesla + " °";
            text = "Ausrichtungssensor";
        }
        mTextPaint.setTextSize(20);
        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();

        mTextHeight = metrics.bottom - metrics.top;

        mTextPaint.setColor(Color.BLACK);
        sTesla = str + " " + sTesla ;
        String name = sensor.getName();
        mTextWidth = Math.max(mTextPaint.measureText(sTesla),mTextPaint.measureText(text));
        mTextWidth = Math.max(mTextWidth,mTextPaint.measureText(name));
        canvas.drawText(text,contentWidth-mTextWidth,contentHeight-mTextHeight-mTextHeight-mTextHeight-40,mTextPaint);
        canvas.drawText(name,contentWidth-mTextWidth,contentHeight-mTextHeight-mTextHeight-20,mTextPaint);
        canvas.drawText(sTesla,contentWidth-mTextWidth,contentHeight-mTextHeight,mTextPaint);
    }

    /**
     * Called when the visibility of the view or an ancestor of the view is changed.
     *
     * @param changedView The view whose visibility changed. Could be 'this' or
     *                    an ancestor view.
     * @param visibility  The new visibility of changedView: {@link #VISIBLE},
     *                    {@link #INVISIBLE} or {@link #GONE}.
     */
    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if(visibility == VISIBLE){
            AlertDialog dialog ;
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            final ActiveView _this = this;

            builder.setAdapter(new ListAdapter() {
                @Override
                public boolean areAllItemsEnabled() {
                    return true;
                }

                @Override
                public boolean isEnabled(int position) {
                    return true;
                }

                @Override
                public void registerDataSetObserver(DataSetObserver observer) {

                }

                @Override
                public void unregisterDataSetObserver(DataSetObserver observer) {

                }

                @Override
                public int getCount() {
                    return list.length;
                }

                @Override
                public Object getItem(int position) {
                    return list[position];
                }

                @Override
                public long getItemId(int position) {
                    return 0;
                }

                @Override
                public boolean hasStableIds() {
                    return false;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    return null;
                }

                @Override
                public int getItemViewType(int position) {
                    return 0;
                }

                @Override
                public int getViewTypeCount() {
                    return 0;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }
            },new DialogInterface.OnClickListener() {
                /**
                 * This method will be invoked when a button in the dialog is clicked.
                 *
                 * @param dialog The dialog that received the click.
                 * @param which  The button that was clicked (e.g.
                 *               {@link DialogInterface#BUTTON1}) or the position
                 */
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Sensor s = map.get(list[which]);
                    if (s != null) {
                        sensor = s;
                        sensorManager.registerListener(_this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
                        dialog.dismiss();
                    }

                }
            });

            dialog = builder.create();
            dialog.show();
        }
    }

    private void initializeDraw(Canvas canvas) {

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        contentWidth  = canvas.getWidth() - paddingLeft - paddingRight;
        contentHeight = canvas.getHeight() - paddingTop - paddingBottom;

        float height = 0;
        if(contentHeight < contentWidth)
            height = Math.min(contentWidth/3,contentHeight) ;
        else
            height = Math.min(contentHeight/3,contentWidth) ;
        float leading = height/10;
        float y = height / 2 ;
        float x = height / 2 ;
        for(int i = 0 ; i < 3 ; i++) {
            if(contentHeight < contentWidth)
                x = ((height)*i+height/2);
            else
                y = ((height)*i+height/2);
            point[i] = new Point3D(x,y,0);
            point[i].offset(paddingLeft ,paddingTop,0);
        }
        radius = height/2 - leading/2;
        initialized = true;
    }

    private void drawCircle(Canvas canvas, Point3D point, Paint paint) {
        paint.setStrokeWidth(lineWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.DKGRAY);
        canvas.drawCircle(point.x, point.y,radius,paint);

    }


}
