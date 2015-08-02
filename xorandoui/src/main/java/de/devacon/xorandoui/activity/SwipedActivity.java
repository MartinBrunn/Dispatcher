package de.devacon.xorandoui.activity;

import android.app.ActivityManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;

import de.devacon.content.DummyHtmlContent;
import de.devacon.content.StringContentProvider;
import de.devacon.content.StringContentSink;
import de.devacon.deviceinfo.DisplayList;
import de.devacon.deviceinfo.MagneticSensor;
import de.devacon.deviceinfo.ScreenInfo;
import de.devacon.systeminfo.SensorsContent;
import de.devacon.systeminfo.Services;
import de.devacon.util.Registry;
import de.devacon.xorandoui.R;
import de.devacon.xorandoui.fragment.ActiveFragment;
import de.devacon.xorandoui.fragment.InfoFragment;
import de.devacon.xorandoui.fragment.ViewFragment;
import de.devacon.xorandoui.system.BaseServices;

public class SwipedActivity extends FragmentActivity {
    public static final String SWIPED_ACTIVITY = "SwipedActivity";
    Registry registry = BaseServices.getRegistry();
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    private String name = SWIPED_ACTIVITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swiped);
        String name = null;
        if(savedInstanceState != null) {
            Bundle args = savedInstanceState.getBundle("arguments");
            if (args != null) {
                name = args.getString("name");
            }
        }
        if (name != null) {
            this.name = name;
        }
        registry.register(BaseServices.ACTIVITY, name, this);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                Toast.makeText(getApplicationContext(),"Page " + Integer.toString(position), Toast.LENGTH_SHORT).show();
                super.onPageSelected(position);
                String str = null;
                int count = 0 ;
                try {
                    //count = mViewPager.getChildCount();
                    //mViewPager.getChildAt(position).setVisibility(View.VISIBLE);
                    str = "OK" ;
                }
                catch(NullPointerException e) {
                    e.printStackTrace();
                    str = "ERROR" ;
                }
            }
        });

    }

    /**
     * Destroy all fragments and loaders.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        registry.deregister(BaseServices.ACTIVITY,this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_swiped, menu);
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
     * Called when a fragment is attached to the activity.
     *
     * @param fragment
     */
    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private int count = 20 ;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            registry.registerCategory(BaseServices.CONTENT);
            if(position == 0 ) {
                String id = "Compass" ;

                return ViewFragment.newInstance(id,id);
            }
            else if(position == 1 ) {
                String id = "SecondPage" ;
                return ActiveFragment.newInstance(id,"MAGNETOMETER");
            }
            else if(position == 2 ) {
                String id = "ACCELEROMETER" ;
                return ActiveFragment.newInstance(id,"ACCELEROMETER");
            }
            else if(position == 3 ) {
                String id = "ORIENTATION" ;
                return ActiveFragment.newInstance(id,"ORIENTATION");
            }
            else if(position == 4) {
                String id = "ThirdPage" ;
                registry.register(BaseServices.CONTENT, id, new SensorsContent(getApplicationContext()));
                return InfoFragment.newInstance(id);
            }
            else if(position == 11 ) {
                String id = "ALL" ;
                return ActiveFragment.newInstance(id,id);
            }
            else if(position == 5 ) {
                String id = "ACCEL3" ;
                return ActiveFragment.newInstance(id,id);
            }
            else if(position == 6 ) {
                String id = "ScreenInfo" ;
                registry.register(BaseServices.CONTENT,id,new ScreenInfo(getWindowManager().getDefaultDisplay()));
                return InfoFragment.newInstance(id);
            }
            else if(position == 7) {
                String id = "SixthPage" ;
                registry.register(BaseServices.CONTENT, id, new DisplayList(getApplicationContext()));
                return InfoFragment.newInstance(id);
            }
            else if(position == 8) {
                String id = "ThirdPage" ;
                registry.register(BaseServices.CONTENT, id, new SensorsContent(getApplicationContext()));
                return InfoFragment.newInstance(id);
            }
            else if(position == 9) {
                String id = "FourthPage" ;
                registry.register(BaseServices.CONTENT, id, new Services(getApplicationContext()));
                return InfoFragment.newInstance(id);
            }
            else if(position == 10) {
                String id = "FifthPage" ;
                registry.register(BaseServices.CONTENT, id, new MagneticSensor(getApplicationContext()));
                return InfoFragment.newInstance(id);
            }
            else {
                String id = "Page " + Integer.toString(position);
                registry.register(BaseServices.CONTENT, id, new DummyHtmlContent(position));
                return InfoFragment.newInstance(id);
            }
            //Fragment fragment = PlaceholderFragment.newInstance(position - 2 );
            //return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return count;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section4).toUpperCase(l);
                case 4:
                    return "TExt...";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private int nr = 0;
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int colors[] = { 0 , 0x00FF00, 0xFF0000, 0x0000FF , 0x00FFFF,0xFF00FF,0xFFFF00 ,0 } ;
            View rootView = inflater.inflate(R.layout.fragment_swiped, container, false);
            if(rootView == null) {
                Toast.makeText(getActivity().getApplicationContext(),"Cannot inflate",Toast.LENGTH_LONG).show();
            }
            nr = getArguments().getInt(ARG_SECTION_NUMBER);
            TextView text = (TextView)rootView.findViewById(R.id.section_label);
            text.setTextColor(colors[nr%(colors.length-1)]);
            text.setText("Section Number " + Integer.toString(nr) + "is displayed");
            //text.setVisibility(View.VISIBLE);

            return rootView;
        }

        /**
         * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
         * has returned, but before any saved state has been restored in to the view.
         * This gives subclasses a chance to initialize themselves once
         * they know their view hierarchy has been completely created.  The fragment's
         * view hierarchy is not however attached to its parent at this point.
         *
         * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
         * @param savedInstanceState If non-null, this fragment is being re-constructed
         */
        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            int colors[] = { Color.BLACK , Color.GREEN, Color.RED, Color.BLUE, Color.MAGENTA,
            Color.YELLOW,Color.CYAN,Color.GRAY} ;
            super.onViewCreated(view, savedInstanceState);
            TextView text = (TextView)view.findViewById(R.id.section_label);
            text.setTextColor(colors[(colors.length - nr -1)%(colors.length-1)]);
            text.setText("Section Number " + Integer.toString(nr) + " is displayed");
            text = (TextView)view.findViewById(R.id.section_title);
            text.setTextColor(colors[nr%(colors.length-1)]);
            text.setText("Title Number " + Integer.toString(nr) + " is displayed");
            //text.setVisibility(View.VISIBLE);
        }
    }

}
