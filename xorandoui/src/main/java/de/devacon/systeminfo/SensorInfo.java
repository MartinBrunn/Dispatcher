package de.devacon.systeminfo;

import android.hardware.Sensor;

import de.devacon.content.StringContentProvider;
import de.devacon.content.StringContentSink;
import de.devacon.util.Util;

/**
 * Created by @Martin@ on 22.07.2015 07:54.
 */
public class SensorInfo implements StringContentProvider {
    boolean asHtml = false;
    String text = "";
    String html = "";
    StringContentSink sink = null;
    Sensor sensor = null;

    public SensorInfo() {

    }

    public SensorInfo(Sensor sensor) {
        this(sensor,false);
    }

    public SensorInfo(Sensor sensor, boolean asHtml) {
        this.sensor = sensor;
        this.asHtml = asHtml;
        init();
    }
    void init() {
        html = "<p><bold>" + sensor.getName() + "</bold> (" +
                getSensorTypeString(sensor.getType()) +
                ") manufacturer:" + sensor.getVendor() + " " +
                "version:" + sensor.getVersion() + " </p><p>" +
                "resolution:" + sensor.getResolution() + " " +
                "rangeMax:" + sensor.getMaximumRange() + " " +
                "power:" + sensor.getPower() + " " +
                "minDelay:" + sensor.getMinDelay() + " </p>"
                ;
        text = "name:" + sensor.getName() + " (" +
                getSensorTypeString(sensor.getType()) +
                ") manufacturer:" + sensor.getVendor() + " " +
                "version:" + sensor.getVersion() + " \r\n" +
                "resolution:" + sensor.getResolution() + " " +
                "rangeMax:" + sensor.getMaximumRange() + " " +
                "power:" + sensor.getPower() + " " +
                "minDelay:" + sensor.getMinDelay() + " "
        ;
        if(sink != null) {
            sink.onContentChange();
        }
    }
    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
        init();
    }

    public boolean isHtml() {
        return asHtml;
    }

    public void setHtml(boolean asHtml) {
        this.asHtml = asHtml;
    }

    protected String getHtml() {
        return html;
    }

    protected void setHtml(String html) {
        this.html = html;
    }

    protected String getText() {
        return text;
    }

    protected void setText(String text) {
        this.text = text;
    }

    @Override
    public String getContent() {
        return asHtml ? html : text ;
    }

    @Override
    public void attachContentSink(StringContentSink sink) {
        this.sink = sink;
    }

    public static String getSensorTypeString(int type) {
        // TODO! return a meaningful text
        return Integer.toString(type);
    }
}
