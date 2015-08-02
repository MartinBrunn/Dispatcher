package de.devacon.xorandoui.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class SensorListAdapter extends ArrayAdapter {
    //String[] list = null;
    List<Sensor> list = null;
    SensorManager manager = null;
    public SensorListAdapter(Context context,SensorManager manager) {
        super(context,0);
        this.manager = manager;
        list = manager.getSensorList(Sensor.TYPE_ALL);
        super.addAll(list);

    }

    @Override
    public boolean isEnabled(int position) {
        boolean b = super.isEnabled(position);
        return b;
    }

    @Override
    public boolean areAllItemsEnabled() {
        boolean b = super.areAllItemsEnabled();
        return b;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = new TextView(parent.getContext()) ;
        }
        if(convertView != null) {
            Sensor s = (Sensor)getItem(position);
            if(s != null) {
                ((TextView)convertView).setTextSize(20);
                ((TextView)convertView).setText(s.getName() + "(" + s.getVendor() + ")");

            }
        }
        convertView.setEnabled(true);
        return convertView;
    }

    /**
     * @return true if this adapter doesn't contain any data.  This is used to determine
     * whether the empty view should be displayed.  A typical implementation will return
     * getCount() == 0 but since getCount() includes the headers and footers, specialized
     * adapters might want a different behavior.
     */
    @Override
    public boolean isEmpty() {
        return list != null ? list.size() == 0 : true ;
    }
}
