package de.devacon.xorando.chitchat;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.List;

import de.devacon.datastruct.PersonSymbolic;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonListFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String name = "rot";
    private OnFragmentInteractionListener mListener;
    private OnPersonChangedListener listener = null;
    private ListView list = null;
    interface OnPersonChangedListener {
        void onPersonChanged(PersonSymbolic newPerson);
    }
    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p/>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(parent instanceof ListView) {
            if(((ListView)parent).isItemChecked(position)) {
                view.setBackgroundColor(Color.rgb(0x7f,0x7f,0xFF));
                /*
                SparseBooleanArray ar = ((ListView)parent).getCheckedItemPositions();

                if(ar == null) {
                    return;
                }
                for(int i = 0 ; i < persons.size();i++) {

                    if(positions.get(i)) {
                        queue.add(array.get(i));
                    }
                }*/

            }
            else
                view.setBackgroundColor(parent.getDrawingCacheBackgroundColor());
        }
    }

    class PersonAdapter extends ArrayAdapter<PersonSymbolic> {

        /**
         * Constructor
         *
         * @param context  The current context.
         * @param resource The resource ID for a layout file containing a TextView to use when
         *                 instantiating views.
         * @param objects  The objects to represent in the ListView.
         */
        public PersonAdapter(Context context, int resource, List<PersonSymbolic> objects) {
            super(context, resource, objects);
        }
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonListFragment newInstance(String param1, String param2) {
        PersonListFragment fragment = new PersonListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PersonListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_person_list, container, false);
        try {
            ((ImageButton) view.findViewById(R.id.playSelected)).setOnClickListener(this);
            ((ImageButton) view.findViewById(R.id.deleteSelected)).setOnClickListener(this);
            list = (ListView)view.findViewById(R.id.listview);
            SharedPreferences pref = getActivity().getSharedPreferences("Plauderei-Persons",Activity.MODE_MULTI_PROCESS);
            if(pref != null)
                name = pref.getString("name","rot");
            list.setOnItemClickListener(this);
        }
        catch(Throwable all) {
            all.printStackTrace();
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteSelected:
                deleteSelected();
                break;
            case R.id.playSelected:
                playSelected();
                break;
        }
    }

    private void playSelected() {

    }

    private void deleteSelected() {

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
    public void setOnPersonChangedListener(OnPersonChangedListener listener) {
        this.listener = listener;
    }
}
