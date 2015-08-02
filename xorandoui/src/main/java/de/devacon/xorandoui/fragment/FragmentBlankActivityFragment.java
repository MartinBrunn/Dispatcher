package de.devacon.xorandoui.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import de.devacon.xorandoui.R;
/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentBlankActivityFragment extends Fragment {

    public FragmentBlankActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment_blank, container, false);
    }
}
