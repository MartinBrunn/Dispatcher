package de.devacon.deviceinfo;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import de.devacon.content.StringContentProvider;
import de.devacon.content.StringContentSink;

/**
 * Created by @Martin@ on 18.07.2015 19:51.
 */
public class DisplayList implements StringContentProvider {
    Context context = null;
    public DisplayList(Context context) {
        this.context = context;
    }

    @Override
    public String getContent() {
        DisplayManager disp = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        String html = "<BIG style=\"color:red\" >display info not available</BIG>";
        if(disp != null) {
            String displays = Build.VERSION.SDK_INT >= 17 ? getDisplays(disp) : "<bold>No displaylist available</bold>";
            html = "<h1>List of Available Displays:</h1>" + displays ;
        }
        return html;
    }

    @Override
    public void attachContentSink(StringContentSink sink) {

    }

    @TargetApi(17)
    private String getDisplays(DisplayManager displayManager) {
        String s = "" ;
        Display[] displays = displayManager.getDisplays();
        for(Display display:displays) {
            s = s + "<h3><bold>" + display.getName() + "</bold>(" + Integer.toString(display.getDisplayId()) + ")</h3>";
        }
        return s;
    }
}
