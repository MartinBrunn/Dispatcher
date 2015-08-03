package de.devacon.xorando.chitchat;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
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
import android.widget.TextView;

import java.io.File;
import java.io.FileFilter;

import de.devacon.datastruct.PersonSymbolic;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayListFragment extends Fragment implements AdapterView.OnItemClickListener,
        View.OnClickListener , PersonListFragment.OnPersonChangedListener {

    @Override
    public void onPersonChanged(PersonSymbolic newPerson) {
        activePerson = newPerson;
        getArguments().putSerializable(ARG_PERSON, activePerson);
    }

    class MyFile extends File {

        /**
         * Constructs a new file using the specified directory and name.
         *
         * @param dir  the directory where the file is stored.
         * @param name the file's name.
         * @throws NullPointerException if {@code name} is {@code null}.
         */
        public MyFile(File dir, String name) {
            super(dir, name);
        }
        public MyFile(File file){
            super(file.getPath());
        }
        public String toString() {
            String ret = getName().toUpperCase();
            ret = ret.substring(0,ret.indexOf(".3GP"));
            /*try {
                Date date = new Date(Long.parseLong(ret));
                ret = DateFormat.getDateTimeInstance().format(date);

            }
            catch(Throwable any){
                //any.printStackTrace();
            }*/
            return ret;
        }

    }

    private static final String ARG_PERSON = "person";


    private PersonSymbolic activePerson = null;
    private ListView listView = null;
    private OnFragmentInteractionListener mListener;
    private MyFile[] array = null;
    private File dir = null;
    private Player player = null;

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
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
                ((TextView)view).setTextColor(Color.rgb(0x7f, 0x7f, 0xff));
                player.playFile(array[position]);
            }
            else {
                ((TextView)view).setTextColor(Color.BLACK);
            }
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.playSelected:
                if(array != null) {
                    for(int i = 0 ; i < array.length ; ++i) {
                        if(listView.isItemChecked(i)) {
                            player.enqueue(array[i]);
                        }
                    }
                    player.play();
                }
                break;
            case R.id.deleteSelected:
                if(array != null) for(int i = 0 ; i < array.length ; ++i) {
                    if(listView.isItemChecked(i)) {
                        array[i].delete();
                    }
                }
                fillArray();
                listView.setAdapter(new TextAdapter(getActivity().getApplicationContext(),
                        R.layout.play_list, array != null ? array : new MyFile[0]));
                listView.setEnabled(true);
                break;
        }
    }

    interface OnPlayListChangedListener {
        void onPlayListChanged();
    }
    class TextAdapter extends ArrayAdapter<MyFile> {

        /**
         * Constructor
         *
         * @param context  The current context.
         * @param resource The resource ID for a layout file containing a TextView to use when
         *                 instantiating views.
         * @param objects  The objects to represent in the ListView.
         */
        public TextAdapter(Context context, int resource, MyFile[] objects) {
            super(context, resource, objects);
        }
    }
    private OnPlayListChangedListener listener = null;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param activePerson person currently activated.
     * @return A new instance of fragment PlayListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayListFragment newInstance(PersonSymbolic activePerson) {
        PlayListFragment fragment = new PlayListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PERSON, activePerson);

        fragment.setArguments(args);
        return fragment;
    }

    public PlayListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            activePerson = (PersonSymbolic)getArguments().getSerializable(ARG_PERSON);

        }
        dir = new File(Const.rootdir,activePerson.name);
        dir.mkdirs();
        player = new Player();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play_list, container, false);
        listView = (ListView)view.findViewById(R.id.listview);
        ((ImageButton)view.findViewById(R.id.playSelected)).setOnClickListener(this);
        ((ImageButton)view.findViewById(R.id.deleteSelected)).setOnClickListener(this);
        listView.setOnItemClickListener(this);
        fillArray();
        listView.setAdapter(new TextAdapter(getActivity().getApplicationContext(),R.layout.play_list,
                    array != null ? array : new MyFile[0]));
        view.setVisibility(View.VISIBLE);
        return view;
    }

    private void fillArray() {

        dir.mkdirs();
        File[] files = dir.listFiles(new FileFilter() {
            /**
             * Indicating whether a specific file should be included in a pathname list.
             *
             * @param pathname the abstract file to check.
             * @return {@code true} if the file should be included, {@code false}
             * otherwise.
             */
            @Override
            public boolean accept(File pathname) {
                if(!pathname.isDirectory() && pathname.getName().toUpperCase().endsWith(".3GP"))
                    return true;
                return false;
            }
        });
        if(files != null) {
            array = new MyFile[ files.length ];
            int i = 0 ;
            for(File file : files){
                array[ i++ ] = new MyFile(file);
            }
        }
        else
            array = new MyFile[0];
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
        listener = null;
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
    public void setOnPlayListChangedListener(OnPlayListChangedListener listener) {
        this.listener = listener;
    }
}
