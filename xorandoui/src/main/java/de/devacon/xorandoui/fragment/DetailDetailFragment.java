package de.devacon.xorandoui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.devacon.util.ContentAdapterString;
import de.devacon.xorandoui.R;
import de.devacon.xorandoui.activity.DetailDetailActivity;
import de.devacon.xorandoui.activity.DetailListActivity;


/**
 * A fragment representing a single Detail detail screen.
 * This fragment is either contained in a {@link DetailListActivity}
 * in two-pane mode (on tablets) or a {@link DetailDetailActivity}
 * on handsets.
 */
public class DetailDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private int itemPosition = 0 ;
    private ContentAdapterString content = null;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DetailDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            content = (ContentAdapterString) getActivity() ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.

            itemPosition = getArguments().getInt(ARG_ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (content != null) {
            ((TextView) rootView.findViewById(R.id.detail_detail)).setText(content.toStringAt(itemPosition));
        }

        return rootView;
    }
}
