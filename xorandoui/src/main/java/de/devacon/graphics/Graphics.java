package de.devacon.graphics;

import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by @Martin@ on 17.07.2015 10:41.
 */
public class Graphics {
    public static Rect rectFromRectF(RectF rect) {
        return new Rect(Math.round(rect.left),Math.round(rect.top),
                Math.round(rect.right),Math.round(rect.bottom));
    }
}
