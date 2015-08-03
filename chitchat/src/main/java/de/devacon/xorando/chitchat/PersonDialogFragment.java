package de.devacon.xorando.chitchat;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import de.devacon.datastruct.Person;
import de.devacon.datastruct.PersonSymbolic;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonDialogFragment extends Fragment implements TextView.OnEditorActionListener,
        RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PERSON = "person";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private PersonSymbolic activePerson = null;
    private View view = null;

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.ok:
                activePerson.firstName = ((EditText)view.findViewById(R.id.firstName)).getText().toString();
                activePerson.lastName = ((EditText)view.findViewById(R.id.lastName)).getText().toString();
                listener.onPersonDataChangedListener(activePerson);
                break;
        }
    }

    interface OnPersonDataChangedListener {
        void onPersonDataChangedListener(PersonSymbolic person);
    }
    private OnPersonDataChangedListener listener;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonDialogFragment.
     */

    public static PersonDialogFragment newInstance(PersonSymbolic person) {
        PersonDialogFragment fragment = new PersonDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PERSON, person);
        fragment.setArguments(args);
        return fragment;
    }

    public PersonDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            activePerson = getArguments().getParcelable(ARG_PERSON);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_person_dialog, container, false);
        ((EditText)view.findViewById(R.id.firstName)).setText(activePerson.firstName);
        ((EditText)view.findViewById(R.id.lastName)).setText(activePerson.lastName);
        ((RadioGroup)view.findViewById(R.id.gender)).check(
                activePerson.gender == Person.Gender.MALE ? R.id.male : R.id.female);
        if(activePerson.gender == Person.Gender.UNKNOWN) {
            ((RadioGroup)view.findViewById(R.id.gender)).clearCheck();
        }
        ((RadioGroup)view.findViewById(R.id.gender)).setOnCheckedChangeListener(this);
        ((EditText)view.findViewById(R.id.lastName)).setOnEditorActionListener(this);
        ((Button)view.findViewById(R.id.ok)).setOnClickListener(this);

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
     * Called when an action is being performed.
     *
     * @param v        The view that was clicked.
     * @param actionId Identifier of the action.  This will be either the
     *                 identifier you supplied, or {@link EditorInfo#IME_NULL
     *                 EditorInfo.IME_NULL} if being called due to the enter key
     *                 being pressed.
     * @param event    If triggered by an enter key, this is the event;
     *                 otherwise, this is null.
     * @return Return true if you have consumed the action, else false.
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch(v.getId()) {
            case R.id.firstName:
                activePerson.firstName = (String)v.getText();
                listener.onPersonDataChangedListener(activePerson);
                break;
            case R.id.lastName:
                activePerson.lastName = (String)v.getText();
                listener.onPersonDataChangedListener(activePerson);
                break;
        }
        return false;
    }

    /**
     * <p>Called when the checked radio button has changed. When the
     * selection is cleared, checkedId is -1.</p>
     *
     * @param group     the group in which the checked radio button has changed
     * @param checkedId the unique identifier of the newly checked radio button
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int id = group.getCheckedRadioButtonId();
        switch(id){
            case R.id.male: activePerson.gender = Person.Gender.MALE; break;
            case R.id.female: activePerson.gender = Person.Gender.FEMALE; break;
            default: activePerson.gender = Person.Gender.UNKNOWN; break;
        }
        if(listener != null) {
            listener.onPersonDataChangedListener(activePerson);
        }
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
    public void setOnPersonDataChangedListener(OnPersonDataChangedListener listener){
        this.listener = listener;
    }
}
