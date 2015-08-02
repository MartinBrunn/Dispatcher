package de.devacon.systeminfo;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.devacon.content.StringContentProvider;
import de.devacon.content.StringContentSink;

/**
 * Helper class for providing content for user interfaces
 * copyright
 * <p/>
 *
 */
public class SensorsContent implements StringContentProvider {
    public SensorsContent(Context context) {
        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> list = manager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor s: list) {
            addItem(new SensorItem(s));
        }
    }
    static protected int sdk = android.os.Build.VERSION.SDK_INT;
    /**
     * An array of sensor info items.
     */
    public List<SensorItem> ITEMS = new ArrayList<SensorItem>();

    /**
     * A map of sensor items, by ID.
     */
    public Map<String, SensorItem> ITEM_MAP = new HashMap<String, SensorItem>();

    static {
        // Add 3 sample items.
/*        addItem(new SensorItem("1", "Item 1"));
        addItem(new SensorItem("2", "Item 2"));
        addItem(new SensorItem("3", "Item 3"));*/
    }

    private void addItem(SensorItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    @Override
    public String getContent() {
        String html = "<table>" +  SensorItem.getHeader() ;
        String s = "" ;
        for(SensorItem item: ITEMS) {
            s = s + "<tr>" + item.getHtml() + "</tr>";
        }

        return html + s + "</table>" ;
    }

    @Override
    public void attachContentSink(StringContentSink sink) {

    }

    /**
     * A item representing sensor information.
     */
    public static class SensorItem {
        public String id;
        public String manufacturer;
        public String typeString;
        public int version;
        public int type;
        public Class classSensor;
        public boolean wakeUp ;
        public int maxDelay;
        public int minDelay;
        public int reportingMode;
        public float resolution;
        public float maxRange;
        public float power;

        public SensorItem(Sensor sensor) {
            this.id = sensor.getName();
            this.manufacturer = sensor.getVendor();
            this.version =  sensor.getVersion();
            this.type = sensor.getType();
            this.classSensor = sensor.getClass();
            this.resolution = sensor.getResolution();
            this.maxRange = sensor.getMaximumRange();
            this.minDelay = sensor.getMinDelay();
            this.power = sensor.getPower();

            if(sdk >= 20) {
                goTarget20(sensor);
            }
            else
            {
                if(this.type == Sensor.TYPE_ACCELEROMETER) {
                    this.typeString = "Accelerometer";
                }
                else {
                    this.typeString = new Integer(this.type).toString();
                }
            }
            if(sdk >= 21){
                goTarget21(sensor);
            }
            else
            {
                this.reportingMode = -1;
                this.maxDelay = 0;
                this.wakeUp = false;
            }
        }
        @TargetApi(21)
        public void goTarget21(Sensor sensor) {
            /*
            this.reportingMode = sensor.getReportingMode();
            this.maxDelay = sensor.getMaxDelay();
            this.wakeUp = sensor.isWakeUpSensor();
    */
        }
        @TargetApi(20)
        public void goTarget20(Sensor sensor) {
        //          this.typeString = sensor.getStringType();

        }
        @Override
        public String toString() {
            return id;
        }

        public String getContent() {
            return
                    "\nType:" + typeString +
                    "\nName:" + id +
                    "\tVersion:" + new Integer(version).toString() +
                    "\nVendor:" + manufacturer +

                    "\nClass:" + classSensor.toString() +
                    "\n";
        }

        public String getHtml() {
            return "<tr><td>" + id + "</td><td>" + typeString + "</td><td>" +
                Integer.toString(version) + "</td><td>" +
                    manufacturer + "</td><td>" +
                    Float.toString(resolution) + "</td><td>" +
                    Float.toString(maxRange) + "</td><td>" +
                    Float.toString(minDelay) + "</td><td>" +
                    Float.toString(power) + "</td></tr>" ;

        }
        public static String getHeader() {
            return "<tr><th>Id</th><th>Type</th><th>Version</th><th>Manufacturer</th>" +
                    "<th>Res</th><th>max</th><th>min</th><th>power</th></tr>";
        }
    }
}
