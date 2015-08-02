package de.devacon.apps.dispatcher;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;

import de.devacon.xorandoui.activity.BlankActivity;
import de.devacon.xorandoui.activity.DetailListActivity;
import de.devacon.xorandoui.activity.DrawerActivity;
import de.devacon.xorandoui.activity.SettingsActivity;
import de.devacon.xorandoui.activity.SpinnerActivity;
import de.devacon.xorandoui.activity.SwipedActivity;
import de.devacon.xorandoui.activity.TabbedActivity;



public class DispatcherActivity extends FragmentActivity {
    HashMap<Integer,Class> map = new HashMap<>();

    public DispatcherActivity() {
        super();
        map.put(Integer.valueOf(R.id.action_settings),SettingsActivity.class);
        map.put(Integer.valueOf(R.id.action_blank),BlankActivity.class);
        map.put(Integer.valueOf(R.id.action_master), DetailListActivity.class);
        map.put(Integer.valueOf(R.id.action_drawer),DrawerActivity.class);
        map.put(Integer.valueOf(R.id.action_spinner),SpinnerActivity.class);
        map.put(Integer.valueOf(R.id.action_tabbed),TabbedActivity.class);
        map.put(Integer.valueOf(R.id.action_swiped),SwipedActivity.class);
       // map.put(Integer.valueOf(R.id.action_login),LoginActivity.class);
        map.put(Integer.valueOf(R.id.action_other),SettingsActivity.class);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);
        ActionBar actionBar = getActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dispatch, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(map.containsKey(id)) {
            Class c = map.get(id);
            Intent i = new Intent(this,c );
            startActivity(i);
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
