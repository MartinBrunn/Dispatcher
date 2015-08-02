package de.devacon.graphics;

import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;

public class ResizeFrame extends RectF {
    public static final int VERTICAL_MASK = 0x0c;
    public static final int HORIZONTAL_MASK = 0x03;
    public static final int AT_TOP = 0x08;
    public static final int AT_BOTTOM = 0x04;
    public static final int AT_VCENTER = 0x0c;
    public static final int AT_LEFT = 0x01;
    public static final int AT_RIGHT = 0x02;
    public static final int AT_HCENTER = 0x03;

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    private float size = 40;
    private ArrayList<RectObject> array=new ArrayList(8);
    private void init() {
        array.clear();
        array.add(new RectObject(left,  top, AT_LEFT | AT_TOP));
        array.add(new RectObject(left,  top+height()/2,AT_LEFT|AT_VCENTER));
        array.add(new RectObject(left,  bottom,AT_LEFT|AT_BOTTOM));
        array.add(new RectObject(left + width()/2,top,AT_HCENTER|AT_TOP));
        array.add(new RectObject(left + width()/2,bottom,AT_HCENTER|AT_BOTTOM));
        array.add(new RectObject(right, top,AT_RIGHT|AT_TOP));
        array.add(new RectObject(right, top + height()/2,AT_RIGHT|AT_VCENTER));
        array.add(new RectObject(right, bottom,AT_RIGHT|AT_BOTTOM));
    }
    private ResizeFrame() {

    }
    public ResizeFrame(RectF rect) {
        super(rect);
        init();
    }
    void resize() {
        init();
    }
    @Override
    public void set(RectF rect) {
        super.set(rect);
        init();
    }
    public int findButton(float x, float y) {
        for(RectObject r: array) {
            RectF rect = new RectF(r);
            rect.inset(-20,-20);
            if(rect.contains(x,y)) {
                return r.orientation;
            }
        }
        return 0;
    }
    public void foreach(Processor processor) {
        for(RectObject rect:array) {
            processor.process(rect);
        }
    }
    public class RectObject extends RectF {
        public int orientation = 0;

        /**
         * Create a new empty RectF. All coordinates are initialized to 0.
         */
        public RectObject(int orientation) {
            this.orientation = orientation;
            init();
        }

        /**
         * Create a new rectangle with the specified coordinates. Note: no range
         * checking is performed, so the caller must ensure that left <= right and
         * top <= bottom.
         *
         * @param left   The X coordinate of the left side of the rectangle
         * @param top    The Y coordinate of the top of the rectangle
         * @param right  The X coordinate of the right side of the rectangle
         * @param bottom The Y coordinate of the bottom of the rectangle
         */
        public RectObject(float left, float top, float right, float bottom, int orientation) {
            super(left, top, right, bottom);
            this.orientation = orientation;
            init();
        }

        public RectObject(float left, float top, int orientation) {
            super(left, top, left, top);
            this.orientation = orientation;
            init();
        }

        public RectObject(Rect r, int orientation) {
            super(r);
            this.orientation = orientation;
            init();
        }

        /**
         * Create a new rectangle, initialized with the values in the specified
         * rectangle (which is left unmodified).
         *
         * @param r The rectangle whose coordinates are copied into the new
         *          rectangle.
         */
        public RectObject(RectF r, int orientation) {
            super(r);
            this.orientation = orientation;
            init();
        }

        void init() {
            float x = left;
            float y = top;
            float half = size/2;
            int type = orientation;
            if ((type & VERTICAL_MASK) == AT_TOP) {
                top = y;
            }
            if ((type & VERTICAL_MASK) == AT_BOTTOM) {
                top = y - size;
            }
            if ((type & VERTICAL_MASK) == AT_VCENTER) {
                top = y - half;
            }
            bottom = top + size;
            if ((type & HORIZONTAL_MASK) == AT_LEFT) {
                left = x;
            }
            if ((type & HORIZONTAL_MASK) == AT_RIGHT) {
                left = x - size;
            }
            if ((type & HORIZONTAL_MASK) == AT_HCENTER) {
                left = x - half;
            }
            right = left + size;

        }
    }

    public interface Processor {
        boolean process(RectObject rect);
    }
}
