package de.devacon.xorando.chitchat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import de.devacon.datastruct.PersonSymbolic;
import de.devacon.xorando.chitchat.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class ChitChatActivity extends Activity implements View.OnClickListener, View.OnTouchListener {
    private int counter = 5;
    private int nextFile = 0;
    private Runnable resetCounter = null;
    private Handler handler = null;
    private Player player = new Player();
    private File dir = null;
    private File[] files = null;
    private PersonSymbolic activePerson = null;
    private ImageView person = null;
    private ArrayList<PersonSymbolic> persons = null;
    private View controlsView = null;
    HashMap<String,Integer> map = new HashMap<>();
    int mHideFlags = 0 ;
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

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = false;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    //private SystemUiHider mSystemUiHider;
    public ChitChatActivity() {
        initMap();
    }

    /**
     * Called after {@link #onRestoreInstanceState}, {@link #onRestart}, or
     * {@link #onPause}, for your activity to start interacting with the user.
     * This is a good place to begin animations, open exclusive-access devices
     * (such as the camera), etc.
     * <p/>
     * <p>Keep in mind that onResume is not the best indicator that your activity
     * is visible to the user; a system window such as the keyguard may be in
     * front.  Use {@link #onWindowFocusChanged} to know for certain that your
     * activity is visible to the user (for example, to resume a game).
     * <p/>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @see #onRestoreInstanceState
     * @see #onRestart
     * @see #onPostResume
     * @see #onPause
     */
    @Override
    protected void onResume() {
        prepareData();
        person.setImageResource(activePerson.resIcon);

        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chit_chat);
        prepareData() ;
        controlsView = findViewById(R.id.fullscreen_content_controls);

        person = (ImageView) findViewById(R.id.personImage);
        final ImageButton hand = (ImageButton) findViewById(R.id.hand);
        person.setImageResource(activePerson.resIcon);


        controlsView.setOnTouchListener(this);

        hand.setOnClickListener(this);
    }

    private void prepareData() {
        SharedPreferences prefs = getSharedPreferences(Const.PREFERENCES, MODE_MULTI_PROCESS);
        String name = prefs.getString(Const.Preference.CURRENT_PERSON, "rot");
        String saved = prefs.getString(Const.Preference.PERSONS, "");
        readPersons(saved);
        if (persons != null) for (PersonSymbolic person : persons) {
            if (person != null && person.name.equals(name)) {
                activePerson = person;
                activePerson.resIcon = map.get(name);
                break;
            }
        }
        dir = new File(Const.rootdir, activePerson.name);
        files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (!pathname.isDirectory() && pathname.getName().toUpperCase().endsWith(".3GP"))
                    return true;
                return false;
            }
        });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hand:
                if(files.length == 0) {
                    Toast.makeText(getApplicationContext(),"Keine Aufnahme vorhanden",Toast.LENGTH_LONG).show();
                    return ;
                }
                if(nextFile >= files.length) {
                    nextFile = 0 ;
                }
                player.playFile(files[nextFile]);
                nextFile++;
                break;
        }
    }
    void readPersons(String saved) {

        int size = 0;
        ObjectInput objectInput = null;
        try {

            byte[] buffer = Base64.decode(saved, Base64.DEFAULT);

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
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v.getId() == R.id.hand){
            onClick(v);
            return true;
        }
        return true;
    }

    /**
     * Called when a context menu for the {@code view} is about to be shown.
     * Unlike {@link #onCreateOptionsMenu(Menu)}, this will be called every
     * time the context menu is about to be shown and should be populated for
     * the view (or item inside the view for {@link AdapterView} subclasses,
     * this can be found in the {@code menuInfo})).
     * <p/>
     * Use {@link #onContextItemSelected(MenuItem)} to know when an
     * item has been selected.
     * <p/>
     * It is not safe to hold onto the context menu after this method returns.
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     * <p/>
     * <p>This is only called once, the first time the options menu is
     * displayed.  To update the menu every time it is displayed, see
     * {@link #onPrepareOptionsMenu}.
     * <p/>
     * <p>The default implementation populates the menu with standard system
     * menu items.  These are placed in the {@link Menu#CATEGORY_SYSTEM} group so that
     * they will be correctly ordered with application-defined menu items.
     * Deriving classes should always call through to the base implementation.
     * <p/>
     * <p>You can safely hold on to <var>menu</var> (and any items created
     * from it), making modifications to it as desired, until the next
     * time onCreateOptionsMenu() is called.
     * <p/>
     * <p>When you add items to the menu, you can implement the Activity's
     * {@link #onOptionsItemSelected} method to handle them there.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chitchat,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Default implementation of
     * {@link Window.Callback#onMenuItemSelected}
     * for activities.  This calls through to the new
     * {@link #onOptionsItemSelected} method for the
     * {@link Window#FEATURE_OPTIONS_PANEL}
     * panel, so that subclasses of
     * Activity don't need to deal with feature codes.
     *
     * @param featureId
     * @param item
     */
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if(item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(this,AdminActivity.class);
            startActivity(intent);
        }
        return super.onMenuItemSelected(featureId, item);
    }
}
