package de.devacon.xorandoui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by @Martin@ on 22.07.2015 16:13.
 */
public class ViewFlipper extends RelativeLayout {
    private ArrayList<View> array = new ArrayList<>();
    int position = 0;
    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public ViewFlipper(Context context) {
        super(context);
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
     * @see #View(Context, AttributeSet, int)
     */
    public ViewFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
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
    public ViewFlipper(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public void addView(View view) {
        super.addView(view);
        array.add(view);
    }
    public void addView(int index,View view) {
        if(index >= array.size()) {
            super.addView(view);
            array.add(view);
        }
        else {
            super.addView(view,index);
            array.set(index, view);
        }
    }
    public boolean isFirst() {
        return position == 0;
    }
    public boolean isLast() {
        return position == array.size() - 1;
    }
    public void first() {
        if(array.size() > 0){
            array.get(position).setVisibility(GONE);
            position = 0 ;
            array.get(position).setVisibility(VISIBLE);
        }
    }
    public void last() {
        if(array.size() > 0){
            array.get(position).setVisibility(GONE);
            position = array.size() - 1 ;
            array.get(position).setVisibility(VISIBLE);
        }
    }
    public void forward() {

        if(position + 1 < array.size()) {
            array.get(position).setVisibility(GONE);
            position++;
            array.get(position).setVisibility(VISIBLE);
        }
    }
    public void back() {

        if(position > 0) {
            array.get(position).setVisibility(GONE);
            position--;
            array.get(position).setVisibility(VISIBLE);
        }
    }

}
