package de.devacon.xorandoui.activity;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import de.devacon.xorandoui.R;
import de.devacon.xorandoui.view.PaintView;


public class BlankActivity extends FragmentActivity {
    private Handler handler = new Handler();
    private Runnable timerTask = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        final PaintView view = (PaintView)findViewById(R.id.relview);
        timerTask = new Runnable() {
            /**
             * The task to run should be specified in the implementation of the {@code run()}
             * method.
             */
            @Override
            public void run() {
                view.invalidate();
                handler.postDelayed(timerTask,100);
            }
        };
        //handler.postDelayed(timerTask,500);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_blank, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
