package de.devacon.xorandoui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import de.devacon.paint.Tool;
import de.devacon.xorandoui.R;
import java.util.Arrays;
/**
 * Created by @Martin@ on 13.07.2015 17:16.
 */
public class ToolboxView extends View {
    public final static int ORIENTATION_VERTICAL = 1;
    public final static int ORIENTATION_HORIZONTAL = 2;
    public final static int TOOLBOX_DRAWING =1;
    public final static int TOOLBOX_NAVIGATION = 2;
    public final static int TOOLBOX_COLOR = 3;
    public final static int TOOLBOX_LINES = 4;
    public final static int TOOLBOX_SHAPES = 5;
    public final static int TOOLBOX_OTHER = 6;

    HashMap<String,Tool> mapTools = new HashMap<>();
    ArrayList<String> array = new ArrayList<>();
    private RectF rectActive = new RectF();
    private int orientation = ORIENTATION_VERTICAL;


    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public ToolboxView(Context context) {
        super(context);
        init(null,0);
    }
    private void init(AttributeSet attrs,int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ToolboxView, defStyle, 0);
        TypedValue value = new TypedValue();
        if(a.getValue(R.styleable.ToolboxView_orientation,value)) {
            if(value.type == TypedValue.TYPE_INT_DEC) {
                orientation = a.getInt(R.styleable.ToolboxView_orientation,ORIENTATION_VERTICAL);
            }

        }
        int toolbox = a.getInt(R.styleable.ToolboxView_toolbox,TOOLBOX_DRAWING);
        int d = getContext().getResources().getConfiguration().densityDpi;
        if(TOOLBOX_DRAWING == toolbox) {
            mapTools.put("Selector", new Tool(Tool.ToolType.Selector));
            mapTools.put("Point", new Tool(Tool.ToolType.Point));
            mapTools.put("Line", new Tool(Tool.ToolType.Line));
            mapTools.put("Shape", new Tool(Tool.ToolType.RegularShape));
            mapTools.put("Path", new Tool(Tool.ToolType.Path));
            mapTools.put("ColorPicker", new Tool(Tool.ToolType.ColorPicker));
            mapTools.put("Filler", new Tool(Tool.ToolType.Filler));

            //getContext().getResources().getConfiguration().densityDpi = 96;
            mapTools.get("Selector").setIcon(getContext().getResources().getDrawable(R.drawable.tools_arrow_white));
            mapTools.get("Point").setIcon(getContext().getResources().getDrawable(R.drawable.tools_points));
            mapTools.get("Line").setIcon(getContext().getResources().getDrawable(R.drawable.tools_lines));
            mapTools.get("Shape").setIcon(getContext().getResources().getDrawable(R.drawable.tools_shapes));
            mapTools.get("ColorPicker").setIcon(getContext().getResources().getDrawable(R.drawable.tools_picker));
            mapTools.get("Filler").setIcon(getContext().getResources().getDrawable(R.drawable.tools_filler));
            mapTools.get("Path").setIcon(getContext().getResources().getDrawable(R.drawable.tools_path));

            array.add("Selector");
            array.add("Point");
            array.add("Line");
            array.add("Shape");
            array.add("ColorPicker");
            array.add("Filler");
            array.add("Path");
        }
        else if(TOOLBOX_NAVIGATION == toolbox) {
            mapTools.put("zoom in",new Tool(Tool.ToolType.Selector));
            mapTools.put("zoom out",new Tool(Tool.ToolType.Path));
            mapTools.put("up",new Tool(Tool.ToolType.Path));
            mapTools.put("down",new Tool(Tool.ToolType.Path));
            mapTools.put("left",new Tool(Tool.ToolType.Path));
            mapTools.put("right",new Tool(Tool.ToolType.Path));
            mapTools.get("zoom in").setIcon(getContext().getResources().getDrawable(R.drawable.round_klein_plus));
            mapTools.get("zoom out").setIcon(getContext().getResources().getDrawable(R.drawable.round_minus));
            mapTools.get("up").setIcon(getContext().getResources().getDrawable   (R.drawable.arrow_up));
            mapTools.get("down").setIcon(getContext().getResources().getDrawable( R.drawable.arrow_down));
            mapTools.get("left").setIcon(getContext().getResources().getDrawable( R.drawable.arrow_left));
            mapTools.get("right").setIcon(getContext().getResources().getDrawable(R.drawable.arrow_right));
            array.add("zoom in");
            array.add("zoom out");
            array.add("up");
            array.add("down");
            array.add("left");
            array.add("right");

        }
        else if(TOOLBOX_COLOR == toolbox) {
            mapTools.put("Grays", new Tool(Tool.ToolType.Point));
            mapTools.put("Colors", new Tool(Tool.ToolType.Line));
            mapTools.put("Shape", new Tool(Tool.ToolType.RegularShape));
            mapTools.get("Grays").setIcon(getContext().getResources().getDrawable(R.drawable.tools_grays));
            mapTools.get("Colors").setIcon(getContext().getResources().getDrawable(R.drawable.tools_colors));
            mapTools.get("Shape").setIcon(getContext().getResources().getDrawable(R.drawable.tools_shapes));
            array.add("Grays");
            array.add("Colors");
            array.add("Shape");

        }
        else if(TOOLBOX_OTHER == toolbox) {
            mapTools.put("Solid", new Tool(Tool.ToolType.Point));
            mapTools.put("Bezier", new Tool(Tool.ToolType.Line));
            mapTools.put("Spline", new Tool(Tool.ToolType.RegularShape));
            mapTools.get("Solid").setIcon(getContext().getResources().getDrawable(R.drawable.tools_points));
            mapTools.get("Bezier").setIcon(getContext().getResources().getDrawable(R.drawable.tools_lines));
            mapTools.get("Spline").setIcon(getContext().getResources().getDrawable(R.drawable.tools_shapes));
            array.add("Solid");
            array.add("Bezier");
            array.add("Spline");
        }
        setOnTouchListener(new OnTouchListener() {
            /**
             * Called when a touch event is dispatched to a view. This allows listeners to
             * get a chance to respond before the target view.
             *
             * @param v     The view the touch event has been dispatched to.
             * @param event The MotionEvent object containing full information about
             *              the event.
             * @return True if the listener has consumed the event, false otherwise.
             */
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    float x = event.getX();
                    float y = event.getY();
                    for(String name: mapTools.keySet()) {
                        RectF rect = new RectF(mapTools.get(name).getIcon().getBounds());
                        if(rect.contains(x,y)) {
                            rectActive.set(rect);
                            invalidate();
                            Toast.makeText(getContext(),name,Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return false;
            }

        });
    }

    /**
     * Constructor that is called when inflating a view from XML. This is called
     * when a view is being constructed from an XML file, supplying attributes
     * that were specified in the XML file. This version uses a default style of
     * 0, so the only attribute values applied are those in the Context's Theme
     * and the given AttributeSet.
     * <p/>
     * <p/>
     * The method onFinishInflate() will be called after all children have been
     * added.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     * @see #View(Context, AttributeSet)
     */
    public ToolboxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs,0);
    }

    /**
     * Perform inflation from XML and apply a class-specific base style. This
     * constructor of View allows subclasses to use their own base style when
     * they are inflating. For example, a Button class's constructor would call
     * this version of the super class constructor and supply
     * <code>R.attr.buttonStyle</code> for <var>defStyle</var>; this allows
     * the theme's button style to modify all of the base view attributes (in
     * particular its background) as well as the Button class's attributes.
     *
     * @param context  The Context the view is running in, through which it can
     *                 access the current theme, resources, etc.
     * @param attrs    The attributes of the XML tag that is inflating the view.
     * @param defStyle The default style to apply to this view. If 0, no style
     *                 will be applied (beyond what is included in the theme). This may
     *                 either be an attribute resource, whose value will be retrieved
     *                 from the current theme, or an explicit style resource.
     * @see #View(Context, AttributeSet)
     */
    public ToolboxView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs,defStyle);
    }

    /**
     * Changes the activated state of this view. A view can be activated or not.
     * Note that activation is not the same as selection.  Selection is
     * a transient property, representing the view (hierarchy) the user is
     * currently interacting with.  Activation is a longer-term state that the
     * user can move views in and out of.  For example, in a list view with
     * single or multiple selection enabled, the views in the current selection
     * set are activated.  (Um, yeah, we are deeply sorry about the terminology
     * here.)  The activated state is propagated down to children of the view it
     * is set on.
     *
     * @param activated true if the view must be activated, false otherwise
     */
    @Override
    public void setActivated(boolean activated) {
        super.setActivated(activated);
    }

    /**
     * Implement this to do your drawing.
     *
     * @param canvas the canvas on which the background will be drawn
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(canvas == null) {
            return;
        }
        //Drawable arrow = mapTools.get("Selector").getIcon();
        Rect rect = new Rect(); //arrow.getBounds();
        if(rect.width() == 0|| rect.height() ==0) {
            rect.set(0,0,96,96);
        }
        if(ORIENTATION_VERTICAL == orientation && mapTools.size() > 0 ) {
            float iconHeight = canvas.getHeight()/mapTools.size();
            if(iconHeight < rect.height()) {
                rect.set(rect.left,rect.top,Math.round(iconHeight),Math.round(iconHeight));
            }
        }
        if(ORIENTATION_HORIZONTAL == orientation && mapTools.size() > 0 ) {
            float iconHeight = canvas.getWidth()/mapTools.size();
            if(iconHeight < rect.width()) {
                rect.set(rect.left,rect.top,Math.round(iconHeight),Math.round(iconHeight));
            }
        }
        int i = 0 ;
        int vdist = 20;

        for(String name : array) {
            Tool tool = mapTools.get(name);
            if(tool == null) {
                continue;
            }
            Drawable icon = tool.getIcon();
            if(icon == null) {
                continue;
            }
            if(ORIENTATION_VERTICAL == orientation) {
                icon.setBounds(1, (vdist + rect.height()) * i + 1, rect.width(), (vdist + rect.height()) * (i) + rect.height());
            }
            else {
                icon.setBounds( (vdist + rect.width()) * i + 1,1,(vdist + rect.width())*i + rect.width(), rect.height());
            }
            icon.draw(canvas) ;
            ++i;
        }
        Paint paintFrame = new Paint();
        paintFrame.setColor(Color.BLACK);
        paintFrame.setStrokeWidth(4);
        paintFrame.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rectActive,paintFrame);
    }

    /**
     * Returns the minimum height of the view.
     *
     * @return the minimum height the view will try to be.
     * @attr ref android.R.styleable#View_minHeight
     * @see #setMinimumHeight(int)
     */
    @Override
    public int getMinimumHeight() {
        Drawable arrow = mapTools.get("Selector").getIcon();
        Rect rect = arrow.getBounds();
        return (rect.height()+1) * mapTools.size() ; //super.getMinimumHeight();
    }

    /**
     * Returns the minimum width of the view.
     *
     * @return the minimum width the view will try to be.
     * @attr ref android.R.styleable#View_minWidth
     * @see #setMinimumWidth(int)
     */
    @Override
    public int getMinimumWidth() {
        Drawable arrow = mapTools.get("Selector").getIcon();
        Rect rect = arrow.getBounds();
        return rect.width() + 2 ; //super.getMinimumWidth();
    }
}
