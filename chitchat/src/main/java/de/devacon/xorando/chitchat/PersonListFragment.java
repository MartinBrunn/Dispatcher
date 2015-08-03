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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
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
public class PersonListFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, PersonDialogFragment.OnPersonDataChangedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PERSONNAME = "personname";
    private static final String ARG_SAVED = "saved";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String name = "rot";
    private OnFragmentInteractionListener mListener;
    private OnPersonChangedListener listener = null;
    private OnPersonListChangedListener listListener = null;
    private ListView list = null;
    private PersonSymbolic activePerson = null;
    private ArrayList<PersonSymbolic> persons = null;
    private OnAdminStateChangedListener onAdminStateChangedListener = null;
    private ImageButton personImage = null;
    private int position = 0;
    private View view = null;

    @Override
    public void onPersonDataChangedListener(PersonSymbolic person) {
        activePerson = person;
        if(position <= persons.size()) {
            persons.set(position,activePerson);
            list.setAdapter(new PersonAdapter(getActivity().getApplicationContext(),
                    R.layout.person_list, persons));
            savePersons();
            listListener.onPersonListChanged(persons);
        }

    }

    private void savePersons() {
        getArguments().putSerializable(ARG_SAVED,persons);
    }
    interface OnPersonListChangedListener {
        void onPersonListChanged(ArrayList<PersonSymbolic> personList);
    }
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
            this.position = position;
            activePerson = persons.get(position);
            for(int i = 0 ; i < persons.size() ; ++i) {
                if (((ListView) parent).isItemChecked(i)) {
                    view.setBackgroundColor(Color.rgb(0x7f, 0x7f, 0xFF));
                } else
                    view.setBackgroundColor(parent.getDrawingCacheBackgroundColor());
            }
            listener.onPersonChanged(activePerson);
            personImage.setImageResource(activePerson.resIcon);

        }
    }

    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * tied to {@link Activity#onPause() Activity.onPause} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onPause() {
        view.setVisibility(View.INVISIBLE);
        super.onPause();
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {@link Activity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        view.setVisibility(View.VISIBLE);

        super.onResume();
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
        /**
         * {@inheritDoc}
         *
         * @param position
         * @param convertView
         * @param parent
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            if(convertView == null) {
                RelativeLayout view = (RelativeLayout)getActivity().getLayoutInflater().inflate(R.layout.person_list, parent, false);
                convertView = view;
            }
            ((ImageView) convertView.findViewById(R.id.imageView)).setImageResource(
                    persons.get(position).resIcon);
            ((TextView) convertView.findViewById(R.id.textView)).setText(
                    persons.get(position).toString());

            return convertView;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return true ; //super.areAllItemsEnabled();
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

    public static PersonListFragment newInstance(String person,ArrayList saved) {
        PersonListFragment fragment = new PersonListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PERSONNAME, person);
        args.putSerializable(ARG_SAVED, saved);

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
            String person = getArguments().getString(ARG_PERSONNAME);
            persons = (ArrayList)getArguments().getSerializable(ARG_SAVED);
            for(int i = 0 ; i < persons.size();++i) {
                if(persons.get(i).name.equals(person)) {
                    activePerson = persons.get(i);
                    position = i ;
                    break ;
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_person_list, container, false);
        try {
            ((ImageButton) view.findViewById(R.id.personSelected)).setOnClickListener(this);

            list = (ListView)view.findViewById(R.id.listview);
            name = activePerson.name;
            list.setOnItemClickListener(this);
            list.setAdapter(new PersonAdapter(getActivity().getApplicationContext(),
                    R.layout.person_list, persons));
            list.setVisibility(View.VISIBLE);
            personImage = (ImageButton)view.findViewById(R.id.personSelected);
            personImage.setVisibility(View.VISIBLE);
            personImage.setImageResource(activePerson.resIcon);

        }
        catch(Throwable all) {
            all.printStackTrace();
        }
        view.setVisibility(View.VISIBLE);
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
            case R.id.personSelected:
                PersonDialogFragment fragment = PersonDialogFragment.newInstance(activePerson);
                fragment.setOnPersonDataChangedListener(this);

                getFragmentManager().beginTransaction().
                        replace(android.R.id.content, fragment).addToBackStack("TAG").commit();
                if(onAdminStateChangedListener != null) {
                    onAdminStateChangedListener.onAdminStateChanged(AdminActivity.AdminState.DIALOG);
                }
                break;
        }
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
    public void setOnAdminStateChangedListener(OnAdminStateChangedListener listener) {
        this.onAdminStateChangedListener = listener;
    }
    public void setOnPersonListChangedListener(OnPersonListChangedListener listener) {
        this.listListener = listener;
    }
}
