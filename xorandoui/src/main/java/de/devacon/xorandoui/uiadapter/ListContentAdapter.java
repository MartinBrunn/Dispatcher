package de.devacon.xorandoui.uiadapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import de.devacon.util.ContentAdapterString;
import de.devacon.util.ItemAdapter;

/**
 * Created by @Martin@ on 05.07.2015 22:31.
 */
public class ListContentAdapter extends ArrayAdapter<ItemAdapter> {

    ContentAdapterString contentAdapter;
    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     */
    public ListContentAdapter(Context context, ContentAdapterString content, int resource) {
        super(context, resource,content.getArray());
        contentAdapter = content;

    }

    /**
     * Constructor
     *
     * @param context            The current context.
     * @param resource           The resource ID for a layout file containing a layout to use when
     *                           instantiating views.
     * @param textViewResourceId The id of the TextView within the layout resource to be populated
     */
    public ListContentAdapter(Context context, ContentAdapterString content,int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId,content.getArray());
        contentAdapter = content;
    }

}
