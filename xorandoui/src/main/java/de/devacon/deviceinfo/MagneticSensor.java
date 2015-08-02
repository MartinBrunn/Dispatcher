package de.devacon.deviceinfo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import de.devacon.content.StringContentProvider;
import de.devacon.content.StringContentSink;
import de.devacon.systeminfo.Pretext;

/**
 * Created by @Martin@ on 20.07.2015 00:14.
 */
public class MagneticSensor implements StringContentProvider,SensorEventListener {
    private Sensor sensor = null;
    private long timer = 0 ;
    SensorManager sensorManager = null;
    String sensorData    = "X" ;
    String sensorAccuracy = "X";
    private StringContentSink contentSink = null;
    private int counter = 0 ;
    private String html = "";
    private String text = "";
    float xMin = 0 ;
    float xMax = 0 ;
    float yMin = 0 ;
    float yMax = 0 ;
    float zMin = 0 ;
    float zMax = 0 ;
    private long compare = 0;

    public MagneticSensor(Context context) {
        sensorManager = (SensorManager )context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> ar = null;
        ar = sensorManager.getSensorList(2);
        if(ar.size() > 0) {
            sensor = ar.get(0);
        }
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        if(sensor != null) {
            text = "<table><h2><bold>" + sensor.getName() + "</bold></h2>";
        }

    }

    @Override
    public String getContent() {
        return html;
    }

    @Override
    public void attachContentSink(StringContentSink sink) {
        contentSink = sink;
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
        boolean display = false;
        String time = printTime(event.timestamp) ;

        if(event.values.length == 3){
            if(xMin == 0 || event.values[0] < xMin) {
                display = true;
                xMin = event.values[0];
            }
            if(yMin == 0 || event.values[1] < yMin) {
                display = true;
                yMin = event.values[1];
            }
            if(zMin == 0 || event.values[2] < zMin) {
                display = true;
                zMin = event.values[2];
            }
            if(xMax == 0 || event.values[0] > xMax) {
                display = true;
                xMax = event.values[0];
            }
            if(yMax == 0 || event.values[1] > yMax) {
                display = true;
                yMax = event.values[1];
            }
            if(zMax == 0 || event.values[2] > zMax) {
                display = true;
                zMax = event.values[2];
            }
        }
        /*for(float f : event.values) {
            sensorData = sensorData + " [" + Float.toString(f) + "] " ;
        }*/
        sensorData = String.format("%.02f",xMin) + "</td><td>" + String.format("%.02f",xMax) + "</td><td>" +
                String.format("%.02f",yMin) + "</td><td>" + String.format("%.02f",yMax) +
                "</td><td>" + String.format("%.02f",zMin) + "</td><td>" + String.format("%.02f",zMax ) ;
        if(display) {
            if (++counter > 10) {
                counter = 0;
                text = "<h2><bold>" + sensor.getName() + "</bold></h2><table>";

            }
            text = text + "<tr><td>" + time + "</td><td>" +
                    sensorData + "</td></tr>";
            html = text + "</table>";
            if (contentSink != null) {
                contentSink.onContentChange();
            }

        }
    }

    private String printTime(long timestamp) {
        if(timer == 0) {
            timer = timestamp;
            Date now = new Date();
            compare = now.getTime();
        }

        return DateFormat.getTimeInstance(DateFormat.MEDIUM).format(new Date((timestamp - timer)/1000000 + compare));
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
        String name = sensor.getName();
        sensorAccuracy = " accuracy: (" + Integer.toString(accuracy) + ")";

    }
}
