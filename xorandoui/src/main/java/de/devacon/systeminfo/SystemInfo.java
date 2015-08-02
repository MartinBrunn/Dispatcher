package de.devacon.systeminfo;

import android.content.Context;

/**
 * Created by Harald on 29.06.2015.
 */
public class SystemInfo {
    private static SystemInfo ourInstance = null;
    private Context context = null;
    public SensorsContent sensors = null ;
    public Services svc = null;
    public static SystemInfo getInstance() {
        return ourInstance;
    }

    private SystemInfo(Context context) {
        this.context = context;
        this.svc = new Services(context);
    }
    static public void onStart(Context context) {
        if(ourInstance == null)
            ourInstance = new SystemInfo(context);
     }
    static public void onFinish() {
        ourInstance = null;
    }
    public void setSensors(SensorsContent sensors) {
        this.sensors = sensors;
    }
    public SensorsContent getSensors() {
        return sensors;
    }

}
