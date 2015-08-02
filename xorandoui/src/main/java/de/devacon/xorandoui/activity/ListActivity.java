package de.devacon.xorandoui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.devacon.uiutil.RelativeLayout;
import de.devacon.xorandoui.R;
import de.devacon.xorandoui.adapter.SensorListAdapter;
import de.devacon.xorandoui.view.SensorView;
import de.devacon.xorandoui.view.ViewFlipper;

public class ListActivity extends FragmentActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemSelectedListener{

    private Sensor sensor = null;
    private SensorView sensorView = null;
    private SensorManager manager = null;
    private View contentView = null;
    private ListView listView = null;
    private ViewFlipper flipper = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        flipper = new ViewFlipper(this);

        ListView rel = new ListView(this);


        manager = (SensorManager )getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        rel.setAdapter(new SensorListAdapter(getApplicationContext(), manager));
        rel.setOnItemClickListener(this);
        //list.setOnItemSelectedListener(this);
        //rel.setOnItemSelectedListener(this);
        //rel.setVisibility(View.VISIBLE);
        //rel.setEnabled(true);
        sensor = manager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorView = new SensorView(getApplicationContext(),manager ,sensor);
        Toast.makeText(getApplicationContext(),"sensor:" + sensor.getName() + " is selected",Toast.LENGTH_LONG).show();
        flipper.addView(sensorView);
        //flipper.addView(rel);
        flipper.first();
        //flipper.last();


        setContentView(flipper);
        contentView = rel;
        listView = rel;
        //list.setFocusable(true);

        //list.setFocusableInTouchMode(true);

    }

    /**
     * Take care of calling onBackPressed() for pre-Eclair platforms.
     *
     * @param keyCode
     * @param event
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if(!flipper.isFirst()) {
                flipper.first();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        try {
            getActionBar().setHomeButtonEnabled(true);
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.action_sensors) {
            return true;
        }
        else if(id == android.R.id.home) {
            if(!flipper.isFirst()) {
                flipper.first();
            }
            else
                return super.onOptionsItemSelected( item );
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * <p>Callback method to be invoked when an item in this view has been
     * selected. This callback is invoked only when the newly selected
     * position is different from the previously selected position or if
     * there was no selected item.</p>
     * <p/>
     * Impelmenters can call getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param parent   The AdapterView where the selection happened
     * @param view     The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id       The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Sensor sensor = (Sensor)parent.getAdapter().getItem(position);
        if(sensor != null) {
            this.sensor = sensor;
            sensorView = new SensorView(this,manager ,sensor);
            Toast.makeText(getApplicationContext(),"sensor:" + sensor.getName() + " is selected",Toast.LENGTH_LONG).show();
            flipper.addView(1,sensorView);
        }
    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
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
        Sensor sensor = (Sensor)parent.getAdapter().getItem(position);
        if(sensor != null) {
            this.sensor = sensor;
            sensorView = new SensorView(getApplicationContext(),manager ,sensor);
            Toast.makeText(getApplicationContext(),"sensor:" + sensor.getName() + " is selected",Toast.LENGTH_LONG).show();
            flipper.addView(1, sensorView);
            flipper.last();
        }
    }
}
