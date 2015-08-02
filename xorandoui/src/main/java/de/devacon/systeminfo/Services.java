package de.devacon.systeminfo;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.PowerManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.devacon.content.StringContentProvider;
import de.devacon.content.StringContentSink;
import de.devacon.util.BundleLookup;
import de.devacon.util.Lookup;

/**
 * Created by Harald on 29.06.2015.
 */
public class Services extends Lookup<String> implements StringContentProvider{
    public static final String PACKAGES = "Packages";
    public static final String SYSTEM_SERVICES = "SystemServices";
    private static final String PACKAGE_NAME = "PackageName";
    private static final String SHAREDUSERID = "ShardeUserId";
    private static final String VERSIONNAME = "VersionName";
    private static final String REQUESTEDPERMISSIONS = "RequestedPermissions";
    private static final String SPLITNAMES = "SplitNames";
    private static final String BACKUPAGENTNAME = "";
    private static final String CLASSNAME = "";
    private static final String DATADIR = "";
    private static final String MANAGESPACEACTIVITYNAME = "";
    private static final String NATIVELIBRARYDIR = "";
    private static final String PERMISSION = "";
    private static final String PROCESSNAME = "";
    private static final String TARGETSDKVERSION = "";
    private static final String PACKAGENAME = "";
    private static final String TASKAFFINITY = "";
    private static final String SOURCEDIR = "";
    private static final String PUBLICSOURCEDIR = "";
    private static final String NAME = "Name";
    private static final String APPLICATIONINFO = "ApplicationInfo";
    private static final String METADATA = "MetaData";
    private static final String SERVICEINFO = "ServiceInfo";
    private static final String ACTIVITYINFO = "ActivityInfo";
    private static final String SERVICES = "SystemServices";
    private static final String PACKAGEINFO ="PackageInfo";
    private static int sdk = android.os.Build.VERSION.SDK_INT;

    private String html = "" ;

    private HashMap<String,PackageInfo> packageList;

    @Override
    public String getContent() {
        return html ;
    }

    @Override
    public void attachContentSink(StringContentSink sink) {

    }

    public class ApplicationInfo extends Lookup {

        ApplicationInfo(android.content.pm.ApplicationInfo other){
            table.put(BACKUPAGENTNAME, other.backupAgentName);
            table.put(CLASSNAME, other.className);
            table.put(DATADIR, other.dataDir);
            table.put(MANAGESPACEACTIVITYNAME, other.manageSpaceActivityName);
            table.put(NATIVELIBRARYDIR, other.nativeLibraryDir);
            table.put(PERMISSION, other.permission);
            table.put(PROCESSNAME, other.processName);
            table.put(PUBLICSOURCEDIR, other.publicSourceDir);
            table.put(SOURCEDIR, other.sourceDir);
            table.put(TASKAFFINITY, other.taskAffinity);
            table.put(PACKAGENAME, other.packageName);
            table.put(TARGETSDKVERSION, Integer.toString(other.targetSdkVersion));

        }

    }
    private class PackageInfo extends Lookup{


        private ArrayList<String> requestedPermissions;
        public PackageInfo(android.content.pm.PackageInfo other){
            Lookup lookup = new Lookup();
            if(other.services != null) {
                for (android.content.pm.ServiceInfo e : other.services) {
                    lookup.addDescendant(e.name, new ServiceInfo(e));
                }
            }
            map.put(SERVICEINFO,lookup);
            lookup = new Lookup();
            if(other.activities != null) {
                for (android.content.pm.ActivityInfo e : other.activities) {
                    lookup.addDescendant(e.name, new ActivityInfo(e));
                }
            }
            map.put(ACTIVITYINFO,lookup);
            table.put(PACKAGE_NAME,other.packageName);
            table.put(SHAREDUSERID,other.sharedUserId);
            table.put(VERSIONNAME, other.versionName);
            lookup = new Lookup();
            if(other.requestedPermissions != null) {
                for (String e : other.requestedPermissions) {
                    lookup.addValue(e, "requested");
                }
            }
            map.put(REQUESTEDPERMISSIONS,lookup);

        }
    }
    private class ServiceInfo extends Lookup{
        public ServiceInfo(android.content.pm.ServiceInfo other){
            table.put(NAME,other.name);
            table.put(PERMISSION,other.permission);
            map.put(APPLICATIONINFO,new ApplicationInfo(other.applicationInfo));
            if(other.metaData != null) {
                map.put(METADATA,new BundleLookup(other.metaData));
            }
        }

    }
    private class ActivityInfo extends Lookup{
        public ActivityInfo(android.content.pm.ActivityInfo other){
            table.put(NAME,other.name);
            table.put(PERMISSION,other.permission);
            map.put(APPLICATIONINFO,new ApplicationInfo(other.applicationInfo));
            if(other.metaData != null) {
                map.put(METADATA,new BundleLookup(other.metaData));
            }
        }
    }
    public Services(Context context) {

        html = "" ;
        createPackageList(context);
        createServiceList(context);
    }

    private void createServiceList(Context context) {
        String serviceNames[] = {
                Pretext.POWER_SERVICE,
                Pretext.WINDOW_SERVICE,
                Pretext.LAYOUT_INFLATER_SERVICE,
                Pretext.ACCOUNT_SERVICE,
                Pretext.ACTIVITY_SERVICE,
                Pretext.ALARM_SERVICE,
                Pretext.NOTIFICATION_SERVICE,
                Pretext.ACCESSIBILITY_SERVICE,
                Pretext.CAPTIONING_SERVICE,
                Pretext.KEYGUARD_SERVICE,
                Pretext.LOCATION_SERVICE,

                Pretext.SEARCH_SERVICE,
                Pretext.SENSOR_SERVICE,
                Pretext.STORAGE_SERVICE,
                Pretext.WALLPAPER_SERVICE,
                Pretext.VIBRATOR_SERVICE,

                Pretext.CONNECTIVITY_SERVICE,
                Pretext.WIFI_SERVICE,
                //Pretext.WIFI_PASSPOINT_SERVICE,
                Pretext.WIFI_P2P_SERVICE,
                //Pretext.WIFI_SCANNING_SERVICE,
                //Pretext.WIFI_RTT_SERVICE,
                Pretext.NSD_SERVICE,
                Pretext.AUDIO_SERVICE,
                Pretext.MEDIA_ROUTER_SERVICE,
                Pretext.TELEPHONY_SERVICE,
                Pretext.TELECOM_SERVICE,
                Pretext.CLIPBOARD_SERVICE,
                Pretext.INPUT_METHOD_SERVICE,
                Pretext.TEXT_SERVICES_MANAGER_SERVICE,
                Pretext.APPWIDGET_SERVICE,
                Pretext.DROPBOX_SERVICE,
                Pretext.DEVICE_POLICY_SERVICE,
                Pretext.UI_MODE_SERVICE,
                Pretext.DOWNLOAD_SERVICE,
                Pretext.NFC_SERVICE,
                Pretext.BLUETOOTH_SERVICE,
                Pretext.USB_SERVICE,
                Pretext.LAUNCHER_APPS_SERVICE,
                Pretext.INPUT_SERVICE,
                Pretext.DISPLAY_SERVICE,
                Pretext.USER_SERVICE,
                Pretext.CAMERA_SERVICE,
                Pretext.PRINT_SERVICE,
                Pretext.MEDIA_SESSION_SERVICE,
                Pretext.BATTERY_SERVICE,
                Pretext.JOB_SCHEDULER_SERVICE,
                Pretext.MEDIA_PROJECTION_SERVICE
        };
      /*  context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        context.getSystemService(Context.ACCOUNT_SERVICE);
        ActivityManager serv = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);*/

        Lookup lookup = new Lookup();
        String out = "";
        for (String s : serviceNames) {
            Object serv = context.getSystemService(s);
            if (serv != null) {
                Log.i("Services:", s + ":" + serv.toString() + "\n");
                out = out + "<tr><td>" + s + "</td><td>" + serv.toString() + "</td></tr>" ;
                try {
                    callService(s, serv, lookup);

                } catch (Exception e) {
                    Log.e("Services#", Log.getStackTraceString(e));
                }

            } else {
                out = out + "<tr><td>" + s + "</td><td>null</td></tr>";
                Log.i("Services:", s + " == null");
            }
        }
        map.put(SERVICES,lookup);
        html = html + "<table><tr><th>Services</th></tr>" + out + "</table>" ;
    }
    private void createPackageList(Context context) {
        List<android.content.pm.PackageInfo> pkgs = context.getPackageManager().getInstalledPackages(PackageManager.GET_SERVICES);
        Lookup lookup = new Lookup();
        for(android.content.pm.PackageInfo pkg : pkgs) {
            String id = pkg.packageName;
            lookup.addDescendant(id, new PackageInfo(pkg));
            StringBuilder builder = new StringBuilder("Activties:");
            builder.append(pkg.packageName);
            builder.append("\n");
            if(pkg.activities != null) {
                for (Object act : pkg.activities) {
                    builder.append(act.toString() + "\n");
                }
            }
            String packString = builder.toString();
            Log.i("ServicesId", id);
            Log.i("ServicesPack:", packString);
            builder = new StringBuilder("Services");
            builder.append(pkg.packageName);
            builder.append("\n");
            if(pkg.services != null) {
                for (Object info : pkg.services) {
                    builder.append(info.toString() + "\n");
                }
            }
            packString = builder.toString();
            Log.i("ServicesId",id);
            Log.i("ServicesPack:",packString);
        }
        map.put(PACKAGEINFO, lookup);
    }


    public interface WrappableClass{
        public void call(Object obj);

 /*       List<String> getTokenList(List<String> path,String token);
        List<String> getValueList(List<String> path,String token);
        String getValue(List<String> path,String token);*/

    }
    public abstract class WrappedClass extends Lookup implements WrappableClass{

        public void call(Object obj){

        }

        /*public List<String> getTokenList(List<String> path,String token){
            return null;
        }
        public List<String> getValueList(List<String> path,String token){
            return null;
        }
        public String getValue(List<String> path,String token){
            return toString();
        }*/
    }
    protected void callService(String name,Object service,Lookup lookup) {
        WrappedClass wrappedClass = null;
        try {
            wrappedClass = createServiceWrapper(name);

            lookup.addDescendant(name, wrappedClass);
            if(wrappedClass == null){
                return;
            }
            try {
                wrappedClass.call(service);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected WrappedClass createServiceWrapper(String name){
        if(name.equals(Pretext.POWER_SERVICE)){
            return new PowerService();
        }
        if(name.equals(Pretext.WINDOW_SERVICE)){
            return new WindowService();
        }
        if(name.equals(Pretext.LAYOUT_INFLATER_SERVICE)){
            return new LayoutService();
        }
        if(name.equals(Pretext.ACCOUNT_SERVICE)){
            return new AccontService();
        }
        if(name.equals(Pretext.ACTIVITY_SERVICE)){
            return new ActivityService();
        }
        if(name.equals(Pretext.ALARM_SERVICE)){
            return new AlarmService();
        }
        if(name.equals(Pretext.NOTIFICATION_SERVICE)) {
            return new NotificationService();
        }
        if(name.equals(Pretext.ACCESSIBILITY_SERVICE)) {
            return new AccessibilityWrapper();
        }
        if(name.equals(Pretext.KEYGUARD_SERVICE)) {
            return new KeyguardWrapper();
        }
        if(name.equals(Pretext.LOCATION_SERVICE)) {
            return new LocationWrapper();
        }
        if(name.equals(Pretext.SEARCH_SERVICE)) {
            return new SearchWrapper();
        }
        if(name.equals(Pretext.SENSOR_SERVICE)) {
            return new SensorWrapper();
        }
        if(name.equals(Pretext.STORAGE_SERVICE)) {
            return new StorageWrapper();
        }
        if(name.equals(Pretext.WALLPAPER_SERVICE)) {
            return new WallpaperWrapper();
        }
        if(name.equals(Pretext.VIBRATOR_SERVICE)) {
            return new VibratorWrapper();
        }
        if(name.equals(Pretext.CONNECTIVITY_SERVICE)) {
            return new ConnectivityWrapper();
        }
        if(name.equals(Pretext.WIFI_SERVICE)) {
            return new WifiWrapper();
        }
        if(name.equals(Pretext.WIFI_P2P_SERVICE)) {
            return new WifiP2PWrapper();
        }
        if(name.equals(Pretext.NSD_SERVICE)) {
            return new NServiceDiscoveryWrapper();
        }
        if(name.equals(Pretext.AUDIO_SERVICE)) {
            return new AudioWrapper();
        }
        if(name.equals(Pretext.MEDIA_ROUTER_SERVICE)) {
            return new MediaRouterWrapper();
        }
        if(name.equals(Pretext.TELEPHONY_SERVICE)) {
            return new TelephonyWrapper();
        }
        if(name.equals(Pretext.TELECOM_SERVICE)) {
            return new TelecomWrapper();
        }
        if(name.equals(Pretext.CLIPBOARD_SERVICE)) {
            return new ClipBoardWrapper();
        }
        if(name.equals(Pretext.INPUT_METHOD_SERVICE)) {
            return new InputMethodWrapper();
        }
        if(name.equals(Pretext.TEXT_SERVICES_MANAGER_SERVICE)) {
            return new TextServicesWrapper();
        }
        if(name.equals(Pretext.APPWIDGET_SERVICE)) {
            return new AppWidgetWrapper();
        }
        if(name.equals(Pretext.DROPBOX_SERVICE)) {
            return new DropboxWrapper();
        }
        if(name.equals(Pretext.DEVICE_POLICY_SERVICE)) {
            return new DevicePolicyWrapper();
        }
        if(name.equals(Pretext.UI_MODE_SERVICE)) {
            return new UIModeWrapper();
        }
        if(name.equals(Pretext.DOWNLOAD_SERVICE)) {
            return new DownloadWrapper();
        }
        if(name.equals(Pretext.NFC_SERVICE)) {
            return new NfcWrapper();
        }
        if(name.equals(Pretext.BLUETOOTH_SERVICE)) {
            return new BluetoothWrapper();
        }
        if(name.equals(Pretext.USB_SERVICE)) {
            return new USBWrapper();
        }
        if(name.equals(Pretext.LAUNCHER_APPS_SERVICE)) {
            return new LauncherWrapper();
        }
        if(name.equals(Pretext.INPUT_SERVICE)) {
            return new InputWrapper();
        }
        if(name.equals(Pretext.DISPLAY_SERVICE)) {
            return new DisplayWrapper();
        }
        if(name.equals(Pretext.USER_SERVICE)) {
            return new UserWrapper();
        }
        if(name.equals(Pretext.CAMERA_SERVICE)) {
            return new CameraWrapper();
        }
        if(name.equals(Pretext.PRINT_SERVICE)) {
            return new PrintWrapper();
        }
        if(name.equals(Pretext.MEDIA_SESSION_SERVICE)) {
            return new MediaSessionWrapper();
        }
        if(name.equals(Pretext.BATTERY_SERVICE)) {
            return new BatteryWrapper();
        }
        if(name.equals(Pretext.JOB_SCHEDULER_SERVICE)) {
            return new SchedulerWrapper();
        }
        if(name.equals(Pretext.MEDIA_PROJECTION_SERVICE)) {
            return new ProjectionWrapper();
        }
        if(name.equals(Pretext.CAPTIONING_SERVICE)) {
            return new CaptioningWrapper();
        }
        return null ;
    }

    private class PowerService extends WrappedClass{
        PowerManager manager;
        PowerService() {
        }
        @TargetApi(21)
        private void power21() {
            //addValue("isPowerSafeMode", manager.isPowerSaveMode() ? "true" : "false");
            //addValue("WakeLockLevel0 supported",manager.isWakeLockLevelSupported(0) ? "true" : "false");
        }
        @TargetApi(20)
        private void power20() {
            //addValue("isInteractive",manager.isInteractive()?"true":"false");
        }

        public void call(Object obj){
            manager = (PowerManager)obj;
            addValue("Reboot","Reboot");
            if(sdk >= 21) {
                power21();
            }
            if(sdk >= 20) {
                power20();
            }

            //manager.reboot("Unknown");
        }

        @Override
        public String toString() {
            return "PowerService{" +
                    "manager=" + manager.toString() +
                    '}';
        }
    }
    private class AccessibilityWrapper extends WrappedClass {
        public AccessibilityWrapper() {
        }

        @Override
        public void call(Object obj) {

        }
    }

    private class WindowService extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class CaptioningWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class ProjectionWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class SchedulerWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class LayoutService extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class AccontService extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class ActivityService extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class AlarmService extends WrappedClass {
           @Override
             public void call(Object obj) {

           }

    }


    private class NotificationService extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class KeyguardWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class LocationWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class SearchWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class SensorWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class StorageWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class WallpaperWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class VibratorWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class ConnectivityWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class WifiWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class WifiP2PWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class NServiceDiscoveryWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class AudioWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class MediaRouterWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class TelephonyWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class TelecomWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class ClipBoardWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class InputMethodWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class TextServicesWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class AppWidgetWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class DropboxWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class DevicePolicyWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class UIModeWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class DownloadWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class NfcWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class BluetoothWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class USBWrapper extends WrappedClass {
        public static final String DEVICES = "devices";
        public static final String ACCESSORY = "accessory";
        public static final String USB = "usb";
        public static final String ACCESSORIES = "Accessories";


        UsbManager manager ;
        Map<String,UsbDevice> devices = null ;
        Lookup serial = new Lookup();
        Lookup usbAccessories = new Lookup();
        UsbAccessory accessories[] = null;


        @Override
        public void call(Object obj) {
            manager = (UsbManager)obj ;

            devices = manager.getDeviceList();
            accessories = manager.getAccessoryList();
            if(devices != null) {
                for (String s : devices.keySet()) {
                    UsbDeviceConnection connection = manager.openDevice(devices.get(s));
                    String serialNr = connection.getSerial();
                    connection.close();
                    serial.addValue(s, serialNr);
                }
            }
           if(devices == null || devices.isEmpty())
                serial.addValue("Empty","empty");
            addDescendant(DEVICES, serial);
            if(accessories != null) {
                for (UsbAccessory accessory : accessories) {
                    usbAccessories.addValue(accessory.getDescription(), accessory.toString());
                }
                addDescendant(ACCESSORIES,usbAccessories);
            }
        }
        boolean isRootPath(List<String> path){
            return (path != null && path.size() == 1 && path.get(0).equals(USB));
        }
    }

    private class LauncherWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class InputWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class DisplayWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class UserWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class CameraWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class PrintWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class MediaSessionWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }

    private class BatteryWrapper extends WrappedClass {
        @Override
        public void call(Object obj) {

        }
    }
}
