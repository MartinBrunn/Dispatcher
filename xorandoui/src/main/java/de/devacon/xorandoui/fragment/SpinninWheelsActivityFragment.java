package de.devacon.xorandoui.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import java.util.ArrayList;

import de.devacon.xorandoui.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class SpinninWheelsActivityFragment extends Fragment {

    NumberPicker numberPicker = null;
    NumberPicker numberPicker2 = null;
    NumberPicker numberPicker3 = null;
    private class Data {

    }
    private class Worker extends AsyncTask<NumberPicker,NumberPicker,NumberPicker> {
        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected NumberPicker doInBackground(NumberPicker... params) {
            int number = 0 ;
            //do something
            publishProgress(params[0]);
            return null;
        }
        @Override
        protected void onProgressUpdate(NumberPicker... params) {
            for(NumberPicker numberPicker : params) {
                scroll(numberPicker);
            }
        }
    }
    public SpinninWheelsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spinnin_wheels, container, false);
        numberPicker = (NumberPicker)view.findViewById(R.id.numberpicker);
        numberPicker2 = (NumberPicker)view.findViewById(R.id.numberpicker2);
        numberPicker3 = (NumberPicker)view.findViewById(R.id.numberpicker3);
        String[] array = {
                "1","2","3","4","5","6","7","8","9","A"
        };
        ArrayList<String> letters = new ArrayList<>();
        for(Character ch = 'A'; ch <= 'Z'; ch++) {

            letters.add(ch.toString());
        }
        ArrayList<String> lettersAnd = new ArrayList<>(letters);
        lettersAnd.add("Ä");
        lettersAnd.add("Ö");
        lettersAnd.add("Ü");
        String[] l1 = new String[letters.size()];
        letters.toArray(l1);
        String[] l2 = new String[lettersAnd.size()];
        lettersAnd.toArray(l2);
        String[] l3 = new String[letters.size()];
        letters.toArray(l3);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(9);
        numberPicker.setDisplayedValues(array);
        numberPicker.setValue(5);
        numberPicker.setEnabled(false);
        numberPicker2.setMinValue(1);
        numberPicker2.setMaxValue(l1.length);
        numberPicker2.setDisplayedValues(l1);
        numberPicker3.setMinValue(1);
        numberPicker3.setMaxValue(l2.length);
        numberPicker3.setDisplayedValues(l2);
        numberPicker2.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {

            }
        });
        numberPicker3.setOnLongPressUpdateInterval(4000);
        //Worker worker = new Worker();
        //worker.execute(numberPicker);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
        return view;
    }
    private void scroll(NumberPicker numberPicker) {
        for(int i = 0 ; i < 100 ; i++) {
            numberPicker.scrollBy(0, 1);
        }

    }
}
