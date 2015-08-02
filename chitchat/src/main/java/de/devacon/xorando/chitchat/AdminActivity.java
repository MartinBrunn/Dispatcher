package de.devacon.xorando.chitchat;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;

import de.devacon.datastruct.PersonSymbolic;


public class AdminActivity extends Activity implements View.OnClickListener, PersonListFragment.OnPersonChangedListener {

    @Override
    public void onPersonChanged(PersonSymbolic newPerson) {
        activePerson = newPerson;
    }

    enum AdminState {
        BASE,
        PERSONS,
        PLAYLIST,
        DIALOG
    }
    File rootdir = new File(Environment.getExternalStorageDirectory() + ".Plauderei",".Person");
    File dir = null;
    PersonSymbolic activePerson = null;
    Player player = new Player();
    private Fragment baseFragment = null;
    AdminState state = AdminState.BASE;
    @Override
    public boolean navigateUpTo(Intent upIntent) {
        setResult(1);
        return super.navigateUpTo(upIntent);
    }
    /**
     * Called when the activity has detected the user's press of the back
     * key.  The default implementation simply finishes the current activity,
     * but you can override this to do whatever you want.
     */
    @Override
    public void onBackPressed() {
        setResult(1);

        if(state == AdminState.BASE)
            super.onBackPressed();
        else {
            getFragmentManager().beginTransaction().show(baseFragment).commit();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        dir = new File(rootdir,activePerson.name);
        player.setTargetDir(dir);
        AdminActivityFragment fragment = (AdminActivityFragment)getFragmentManager().findFragmentById(R.id.fragment);
        player.setOnRecordStateChangedListener(fragment);
        baseFragment = fragment;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin, menu);
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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()){
            case R.id.play:
                player.onClick(v);
                break;
            case R.id.stop:
                player.onClick(v);
                break;
            case R.id.list:
                fragment = new PlayListFragment();
                state = AdminState.PLAYLIST;
                break;
            case R.id.delete:
                deleteAll();
                break;
            case R.id.person:
                fragment = new PersonListFragment();
                ((PersonListFragment)fragment).setOnPersonChangedListener(this);
                state = AdminState.PERSONS;
                break;
        }

        if(fragment != null) {
            Bundle arguments = new Bundle();
            arguments.putInt("Param1", 1);
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment, fragment)
                    .commit();
        }
    }

    private void deleteAll() {

    }

}
