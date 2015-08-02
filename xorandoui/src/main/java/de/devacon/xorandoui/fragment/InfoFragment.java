package de.devacon.xorandoui.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import de.devacon.content.StringContentProvider;
import de.devacon.content.StringContentSink;
import de.devacon.style.DefaultStyle;
import de.devacon.style.StyleProvider;
import de.devacon.util.Registry;
import de.devacon.xorandoui.R;
import de.devacon.xorandoui.system.BaseServices;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment implements StringContentSink {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_HTML = "html";

    private static final String ARG_IDENTIFIER = "identifier";

    // TODO: Rename and change types of parameters
    private WebView webView = null;
    private String html;
    private String mParam2;
    private StringContentProvider contentProvider = null;
    private StyleProvider styleProvider = new DefaultStyle();
    private String sinkIdentifier = null;
    private Registry registry = BaseServices.getRegistry();
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sinkIdentifier
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String sinkIdentifier) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_IDENTIFIER, sinkIdentifier);
        fragment.setArguments(args);
        return fragment;
    }

    public InfoFragment() {
        // Required empty public constructor
    }

    public void setContentProvider(StringContentProvider contentProvider) {
        this.contentProvider = contentProvider;
    }

    @Override
    public void onContentChange() {

        if(webView == null) {
            return;
        }
        String style = styleProvider.getStyleSheet() ;
        webView.setBackgroundColor(Color.GRAY);
        if(contentProvider != null) {
            html = contentProvider.getContent();
        }
        else {
            html = "<h1>Not available now</h1>";
        }
        webView.loadData("<html><style>\n"  + style + "\n</style><body>" + html + "</body></html>", "text/html", "utf-8" );
    }

    public void setStyleProvider(StyleProvider styleProvider) {
        this.styleProvider = styleProvider;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            html = getArguments().getString(ARG_HTML);
            sinkIdentifier = getArguments().getString(ARG_IDENTIFIER);
            if(registry.isRegistered(BaseServices.CONTENT,sinkIdentifier) ) {
                Object object = registry.get(BaseServices.CONTENT, sinkIdentifier);
                try {
                    setContentProvider((StringContentProvider)object);
                    ((StringContentProvider)object).attachContentSink(this);
                }
                catch (ClassCastException e) {
                    // lets the content provider how it is
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_empty, container, false);
        webView = (WebView)view.findViewById(R.id.webView);


        String style = styleProvider.getStyleSheet() ;
        webView.setBackgroundColor(Color.GRAY);
        if(contentProvider != null) {
            html = contentProvider.getContent();
        }
        else {
            html = "<h1>Not available now</h1>";
        }
        webView.loadData("<html><style>\n"  + style + "\n</style><body>" + html + "</body></html>", "text/html", "utf-8" );

        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        /* try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
