package de.devacon.deviceinfo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.text.BoringLayout;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import de.devacon.content.StringContentProvider;
import de.devacon.content.StringContentSink;

/**
 * Created by @Martin@ on 18.07.2015 18:46.
 */
public class ScreenInfo implements StringContentProvider{
    private DisplayMetrics outMetrics = new DisplayMetrics();
    private DisplayMetrics realMetrics = new DisplayMetrics();

    private String html = "";
    private Display display;
    private boolean dummy = false;

    public ScreenInfo(Display display) {
        this.display = display;
        collectData();
    }
    public String getData() {
        return html;
    }
    @TargetApi(17)
    private void getRealMetrics() {
        display.getRealMetrics(realMetrics);
    }
    private void getMetrics() {
        dummy = true;
        display.getMetrics(outMetrics);
    }

    public String getMetricsHtml(DisplayMetrics outMetrics) {
        Integer width = getScreenWidth(outMetrics);
        Integer height = getScreenHeight(outMetrics);
        Integer dpi = getDensity(outMetrics) ;
        Float xdpi = outMetrics.xdpi;
        Float ydpi = outMetrics.ydpi;
        Float fdpi = outMetrics.density;
        Float scaledDensity = outMetrics.scaledDensity;

        String metrics = "<big><p>Screen Size<p> Width: <bold>" + width + "</bold><p> Height: <bold>" +
                height + "</bold><p> Density: <bold>" + dpi + "</bold> dpi<p> xdpi: <bold>" +
                xdpi + "</bold><p>ydpi:<bold>" + ydpi + "</bold><p>" +
                "Density: <bold>" + fdpi + "</bold><p>Scaled Density: <bold>" + scaledDensity + "</bold> </big>";
        return metrics;
    }
    @TargetApi(17)
    void getRealSize17(Point pointReal) {
        display.getRealSize(pointReal);
    }
    void getRealSize(Point pointReal) {
        if(Build.VERSION.SDK_INT >= 17) {
            getRealSize17(pointReal);
        }
    }
    String getName() {
        if(Build.VERSION.SDK_INT >= 17)
            return getName17() ;
        return "Unknown";
    }
    @TargetApi(17)
    private String getName17() {
        return display.getName();
    }

    public void collectData() {
        getMetrics();

        if(Build.VERSION.SDK_INT >= 17) {
            getRealMetrics();
        }
        else
            realMetrics = outMetrics;
        Point pointReal = new Point();
        Point pointSmall = new Point();
        Point pointLarge = new Point();
        getRealSize(pointReal);
        String name = "<h3>Display Name: <bold>" + getName() + " (" +
                Integer.toString(display.getDisplayId()) + ")</bold><h3>";
        Rect rect = new Rect() ;
        display.getRectSize(rect);
        String size = "<h4>Display Size:<bold>" + printRect(rect) + "</bold> Real:<bold>" +
                        printSize(pointReal) + "</bold></h4>";
        Float refreshRate = display.getRefreshRate();
        getCurrentSizeRange(pointSmall, pointLarge);
        int flags = getFlags();
        int rotation = display.getRotation();
        Point pointSize = new Point();
        display.getSize(pointSize);
        int  pixelFormat = display.getPixelFormat();
        int orientation = display.getOrientation();
        String sizes = size + " <h4>Size: <bold>" + printSize(pointSize) + "</bold>  Range: <bold>" +
                        printSize(pointSmall) + "</bold> to <bold>" + printSize(pointLarge) + "</bold></h4>";

        String rest = "<h3>Rotation: <bold>" + printRotation(rotation) + "  " +
                printOrientation(orientation) + "</bold> " +
                    " Pixel Format: <bold>" + printPixelFormat(pixelFormat) + "</bold></h3>" +

                            "<h3>Flags: <bold>" + printFlags(flags) + "</bold></h3>" ;
        String title = "<h1>Display Info" + (dummy ? ":" : ":") + name + "</h1>" ;

        html = title + sizes + rest + "<table><tr><td><h2>Metrics</h2>" + getMetricsHtml(outMetrics) +
                "</td><td><h2>Real Metrics</h2>" +
                getMetricsHtml(realMetrics) + "</td></tr></table>"
                ;

    }
    @TargetApi(17)
    private int getFlags17() {
        int flags = display.getFlags();
        return 0;
    }

    private int getFlags() {
        if(Build.VERSION.SDK_INT >= 17)
            return display.getFlags();
        return 0;
    }

    @TargetApi(17)
    private void getCurrentSizeRange17(Point pointSmall, Point pointLarge) {
        display.getCurrentSizeRange(pointSmall, pointLarge);
    }
    private void getCurrentSizeRange(Point pointSmall, Point pointLarge) {
        if(Build.VERSION.SDK_INT >= 17)
            getCurrentSizeRange17(pointSmall, pointLarge);
    }

    private String printFlags(int flags) {
        final int FLAG_PRIVATE = 1 << 2;
        final int FLAG_PRESENTATION = 1 << 3;

        String s = "" ;
        if( (flags & Display.FLAG_SECURE ) == Display.FLAG_SECURE)
            s = s + "SECURE" ;
        if( (flags & Display.FLAG_SUPPORTS_PROTECTED_BUFFERS) == Display.FLAG_SUPPORTS_PROTECTED_BUFFERS) {
            if(!s.isEmpty())
                s = s + " | ";
            s = s + "SUPPORTS_PROTECTED_BUFFERS";
        }
        if( (flags & FLAG_PRESENTATION) == FLAG_PRESENTATION) {
            if(!s.isEmpty())
                s = s + " | ";
            s = s + "PRESENTATION";
        }
        if( (flags & FLAG_PRIVATE) == FLAG_PRIVATE) {
            if(!s.isEmpty())
                s = s + " | ";
            s = s + "PRIVATE";
        }

        return s ;
    }

    private String printOrientation(int orientation) {
        return "(" + Integer.toString(orientation) + ")" ;
    }

    private String printPixelFormat(int pixelFormat) {
        if(pixelFormat == PixelFormat.RGBA_8888)
            return "RGBA_8888 (" + Integer.toString(pixelFormat) + ") " ;
        return Integer.toString(pixelFormat);
    }

    private String printRotation(int rotation) {
        switch (rotation){
            case Surface.ROTATION_0: return "0&#176;" ;
            case Surface.ROTATION_90: return "90&#176;" ;
            case Surface.ROTATION_180: return "180&#176;" ;
            case Surface.ROTATION_270: return "270&#176;" ;
        }
        return "";
    }

    private String printSize(Point size) {
        return "(w:" + Integer.toString(size.x) + " h:" + Integer.toString(size.y) + ")" ;
    }

    private String printRect(Rect rect) {
        return "(l:" + Integer.toString(rect.left) +
                " t:" + Integer.toString(rect.top) +
                " r:" + Integer.toString(rect.right) +
                " b:" + Integer.toString(rect.bottom) + ")";
    }

    private int getDensity(DisplayMetrics outMetrics) {
        return outMetrics.densityDpi;
    }

    private int getScreenHeight(DisplayMetrics outMetrics) {
        return outMetrics.heightPixels;
    }

    private int getScreenWidth(DisplayMetrics outMetrics) {
        return outMetrics.widthPixels;
    }

    @Override
    public String getContent() {
        collectData();
        return html;
    }

    @Override
    public void attachContentSink(StringContentSink sink) {

    }
}
