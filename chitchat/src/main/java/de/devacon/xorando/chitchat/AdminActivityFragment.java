package de.devacon.xorando.chitchat;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A placeholder fragment containing a simple view.
 */
public class AdminActivityFragment extends Fragment implements Player.OnRecordStateChangedListener, View.OnClickListener {
    View view = null;
    View.OnClickListener listener = null;
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
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }
}
