package de.devacon.xorando.chitchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import de.devacon.datastruct.Person;
import de.devacon.datastruct.PersonSymbolic;

public class AdminActivity extends Activity implements View.OnClickListener, PersonListFragment.OnPersonChangedListener,
        PlayListFragment.OnPlayListChangedListener, OnAdminStateChangedListener, PersonListFragment.OnPersonListChangedListener {

    @Override
    public void onPersonChanged(PersonSymbolic newPerson) {
        activePerson = newPerson;
        SharedPreferences prefs = getSharedPreferences(Const.PREFERENCES,MODE_MULTI_PROCESS);
        prefs.edit().putString(Const.Preference.CURRENT_PERSON,activePerson.name).commit();

        dir = new File(Const.rootdir,activePerson.name);
        if(player != null) {
            player.setTargetDir(dir);
        }

        Fragment fragment = getFragmentManager().findFragmentById(R.id.playlist);
        if(fragment instanceof PersonListFragment.OnPersonChangedListener) {
            ((PersonListFragment.OnPersonChangedListener)fragment).onPersonChanged(newPerson);
        }
        fragment = getFragmentManager().findFragmentById(R.id.fragmentadmin);
        if(fragment instanceof PersonListFragment.OnPersonChangedListener) {
            ((PersonListFragment.OnPersonChangedListener)fragment).onPersonChanged(newPerson);
        }
    }

    @Override
    public void onPlayListChanged() {

    }

    @Override
    public void onAdminStateChanged(AdminState state) {
        this.state = state;
    }

    @Override
    public void onPersonListChanged(ArrayList<PersonSymbolic> personList) {
        persons = personList;
        String saved = writePersons();
        SharedPreferences prefs = getSharedPreferences(Const.PREFERENCES,MODE_MULTI_PROCESS);
        prefs.edit().putString(Const.Preference.PERSONS,saved).commit();

    }

    public enum AdminState {
        BASE,
        PERSONS,
        PLAYLIST,
        DIALOG
    }
    File dir = null;
    PersonSymbolic activePerson = null;
    ArrayList<PersonSymbolic> persons = new ArrayList<>();
    Player player = new Player();
    private Fragment baseFragment = null;
    AdminState state = AdminState.BASE;
    HashMap<String,Integer> map = new HashMap<>();
    void initMap() {
        map.put("rot",R.drawable.personxrot);
        map.put("gruen",R.drawable.personxgruen);
        map.put("blau",R.drawable.personxblau);
        map.put("orange",R.drawable.personxorange);
        map.put("hellgruen",R.drawable.personxhellgruen);
        map.put("hellblau",R.drawable.personxhellblau);
        map.put("braun",R.drawable.personxbraun);
        map.put("gelb",R.drawable.personxgelb);
        map.put("grau", R.drawable.personxgrau);
    }

    public AdminActivity() {
        initMap();
    }

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

        if(state == AdminState.DIALOG){
            //getFragmentManager().popBackStack();
            state = AdminState.PERSONS;
            super.onBackPressed();
        }
        else if(state == AdminState.BASE)
            super.onBackPressed();
        else {
            state = AdminState.BASE;

            super.onBackPressed();

            Fragment fragment = getFragmentManager().findFragmentById(R.id.fragmentadmin);
            if(fragment == null) {
                fragment = baseFragment;
            }
            if(fragment instanceof PersonListFragment.OnPersonChangedListener) {
                ((PersonListFragment.OnPersonChangedListener)fragment).onPersonChanged(activePerson);
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getSharedPreferences(Const.PREFERENCES,MODE_MULTI_PROCESS);
        String saved = pref.getString(Const.Preference.PERSONS, "");
        String person = pref.getString(Const.Preference.CURRENT_PERSON,"rot");
        if(!saved.isEmpty()) {
            readPersons(saved);
        }
        if(persons.isEmpty()){
            persons.add( new PersonSymbolic("rot", Person.Gender.MALE,"","Rot",R.drawable.personxrot,null));
            persons.add( new PersonSymbolic("gruen",Person.Gender.MALE,"","Grün",R.drawable.personxgruen,null));
            persons.add( new PersonSymbolic("blau",Person.Gender.MALE,"","Blau",R.drawable.personxblau,null));
            persons.add( new PersonSymbolic("braun",Person.Gender.MALE,"","Braun",R.drawable.personxbraun,null));

            persons.add( new PersonSymbolic("gelb",Person.Gender.FEMALE,"","Gelb",R.drawable.personxgelb,null));
            persons.add( new PersonSymbolic("orange",Person.Gender.FEMALE,"","Rot",R.drawable.personxorange,null));
            persons.add( new PersonSymbolic("grau",Person.Gender.MALE,"","Grau",R.drawable.personxgrau,null));
            persons.add( new PersonSymbolic("hellblau",Person.Gender.FEMALE,"","Blau", R.drawable.personxhellblau,null));
            persons.add( new PersonSymbolic("hellgruen",Person.Gender.FEMALE,"","Grün",R.drawable.personxhellgruen,null));
            saved = writePersons();
            pref.edit().putString(Const.Preference.PERSONS,saved).commit();
        }
        PersonSymbolic newPerson = null;
        for(int i = 0 ; i < persons.size();++i) {
            persons.get(i).resIcon = map.get(persons.get(i).name);
            if(persons.get(i).name.equals(person)) {
                newPerson = persons.get(i);

            }

        }
        onPersonChanged(newPerson);

        setContentView(R.layout.activity_admin);


        AdminActivityFragment fragment = (AdminActivityFragment)getFragmentManager().findFragmentById(R.id.fragment);
        player.setOnRecordStateChangedListener(fragment);
        baseFragment = fragment;
    }

    /**
     * Called when a Fragment is being attached to this activity, immediately
     * after the call to its {@link Fragment#onAttach Fragment.onAttach()}
     * method and before {@link Fragment#onCreate Fragment.onCreate()}.
     *
     * @param fragment
     */
    @Override
    public void onAttachFragment(Fragment fragment) {
        if(fragment instanceof AdminActivityFragment) {
            ((AdminActivityFragment)fragment).setOnClickListener(this);
        }
        if(activePerson != null && fragment instanceof PersonListFragment.OnPersonChangedListener) {
            ((PersonListFragment.OnPersonChangedListener)fragment).onPersonChanged(activePerson);
        }
        super.onAttachFragment(fragment);
    }

    /**
     * Called whenever a key, touch, or trackball event is dispatched to the
     * activity.  Implement this method if you wish to know that the user has
     * interacted with the device in some way while your activity is running.
     * This callback and {@link #onUserLeaveHint} are intended to help
     * activities manage status bar notifications intelligently; specifically,
     * for helping activities determine the proper time to cancel a notfication.
     * <p/>
     * <p>All calls to your activity's {@link #onUserLeaveHint} callback will
     * be accompanied by calls to {@link #onUserInteraction}.  This
     * ensures that your activity will be told of relevant user activity such
     * as pulling down the notification pane and touching an item there.
     * <p/>
     * <p>Note that this callback will be invoked for the touch down action
     * that begins a touch gesture, but may not be invoked for the touch-moved
     * and touch-up actions that follow.
     *
     * @see #onUserLeaveHint()
     */
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
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
    String writePersons() {
        ObjectOutput objectOutput = null;
        Base64OutputStream stream = null;
        ByteArrayOutputStream output = null;
        try {
            output = new ByteArrayOutputStream();

            objectOutput = new ObjectOutputStream(output);
            objectOutput.writeObject(persons);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        String ret = Base64.encodeToString(output.toByteArray(),Base64.DEFAULT);

        return ret;
    }
    void readPersons(String saved) {

        int size = 0;
        ObjectInput objectInput = null;
        try {

            byte[] buffer = Base64.decode(saved,Base64.DEFAULT);

            ByteArrayInputStream stream = new ByteArrayInputStream(buffer);
            objectInput = new ObjectInputStream(stream) ;
            persons = (ArrayList)objectInput.readObject();


        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(objectInput != null) {
                try {
                    objectInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        Fragment fragment = null;
        int id = 0;
        switch (v.getId()){
            case R.id.play:
                ((ImageButton)v).setVisibility(View.INVISIBLE);
                ((ImageButton)findViewById(R.id.stop)).setVisibility(View.VISIBLE);
                player.onClick(v);
                break;
            case R.id.stop:
                ((ImageButton)v).setVisibility(View.INVISIBLE);
                ((ImageButton)findViewById(R.id.play)).setVisibility(View.VISIBLE);
                player.onClick(v);
                break;
            case R.id.list:
                PlayListFragment fragmentPL = PlayListFragment.newInstance(activePerson);
                id= R.id.playlist;
                fragmentPL.setOnPlayListChangedListener(this);
                state = AdminState.PLAYLIST;
                fragment = fragmentPL;
                break;
            case R.id.delete:
                deleteAll();
                break;
            case R.id.person:
                PersonListFragment pFragment = PersonListFragment.newInstance(activePerson.name,persons);
                id = R.layout.fragment_person_list;
                pFragment.setOnPersonChangedListener(this);
                pFragment.setOnPersonListChangedListener(this);
                pFragment.setOnAdminStateChangedListener(this);
                fragment = pFragment;
                state = AdminState.PERSONS;
                break;
        }

        if(fragment != null) {
            //Bundle arguments = new Bundle();

            //fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content,fragment)
                    .hide(baseFragment)
                    .addToBackStack(null) //.hide(baseFragment)
                    .commit();
        }
    }

    private void deleteAll() {
        AlertDialog ad = new AlertDialog.Builder(this).create();
        ad.setCancelable(true); // This blocks the 'BACK' button
        ad.setMessage("Sollen allen Aufnahmen für die aktuelle Person gelöscht werden?");
        ad.setButton("Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        ad.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dir.delete()) {
                    dir.mkdirs();
                } else {
                    File[] files = dir.listFiles();
                    int i = 0;
                    for (File file : files) {
                        file.delete();
                        i++;
                    }
                    if (!dir.delete()) {
                        Toast.makeText(getApplicationContext(), "Fehler beim Löschen", Toast.LENGTH_LONG);
                    } else {
                        Toast.makeText(getApplicationContext(), Integer.toString(i) + " Dateien erfolgreich gelöscht", Toast.LENGTH_LONG);
                    }
                }

                dialog.dismiss();
            }
        });
        ad.show();


    }

}
