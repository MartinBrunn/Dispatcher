package de.devacon.xorando.chitchat;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.ImageButton;

import de.devacon.datastruct.PersonSymbolic;


/**
 * A placeholder fragment containing a simple view.
 */
public class AdminActivityFragment extends Fragment implements Player.OnRecordStateChangedListener,
        View.OnClickListener , PersonListFragment.OnPersonChangedListener{
    View view = null;
    View.OnClickListener listener = null;
    private int resIcon = R.drawable.person;
    public AdminActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin, container, false);

        try {
            ((ImageButton) view.findViewById(R.id.play)).setOnClickListener(this);
            ((ImageButton) view.findViewById(R.id.stop)).setOnClickListener(this);

            ((ImageButton) view.findViewById(R.id.list)).setOnClickListener(this);
            ((ImageButton) view.findViewById(R.id.person)).setOnClickListener(this);
            ((ImageButton) view.findViewById(R.id.delete)).setOnClickListener(this);
            view.findViewById(R.id.stop).setVisibility(View.INVISIBLE);
            ImageButton button = (ImageButton) view.findViewById(R.id.person);
            if (button != null) {
                button.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
                button.setImageResource(resIcon);
                view.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
            }
        }
        catch(Throwable all) {
            all.printStackTrace();
        }
        return view;
    }

    @Override
    public void onRecordStateChangedListener(Player.RecordState state) {
        if(state == Player.RecordState.IDLE){
            ((ImageButton) view.findViewById(R.id.play)).setVisibility(View.VISIBLE);
        }
        if(state == Player.RecordState.RECORDING){
            ((ImageButton) view.findViewById(R.id.stop)).setVisibility(View.VISIBLE);
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
            case R.id.play:
                break;
            case R.id.stop:
                break;
            case R.id.list:
                break;
            case R.id.person:
                break;
            case R.id.delete:
                break;
        }
        if(listener != null)
            listener.onClick(v);
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

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onPersonChanged(PersonSymbolic newPerson) {
        resIcon = newPerson.resIcon;
        if(view == null) {
            return;
        }
        ImageButton button = (ImageButton)view.findViewById(R.id.person) ;
        if(button != null) {
            button.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
            button.setImageResource(newPerson.resIcon);
            int l = view.getLeft();
            int t = view.getTop();
            int r = view.getRight();
            int b = view.getBottom();
            button.setVisibility(View.VISIBLE);
            view.layout(l, t, r, b);
            view.setVisibility(View.VISIBLE);
        }
    }
}
