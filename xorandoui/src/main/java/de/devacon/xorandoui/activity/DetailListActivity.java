package de.devacon.xorandoui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

import de.devacon.util.ContentAdapterString;
import de.devacon.util.ItemAdapter;
import de.devacon.xorandoui.fragment.DetailDetailFragment;
import de.devacon.xorandoui.fragment.DetailListFragment;

import de.devacon.xorandoui.R;

/**
 * An activity representing a list of Details. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link DetailDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link DetailListFragment} and the item details
 * (if present) is a {@link DetailDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link DetailListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class DetailListActivity extends Activity
        implements DetailListFragment.Callbacks , ContentAdapterString{
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */

    class Detail implements ItemAdapter {
        String display ;
        String content;
        Detail(String display,String content) {
            this.display = display;
            this.content = content;
        }
        @Override
        public String toString() {
            return content;
        }
        public String getID() {
            return display ;
        }
    }

    ArrayList<ItemAdapter> arrayItems = null;
    private boolean mTwoPane = false;

    public DetailListActivity() {
        this.arrayItems = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayItems.add(new Detail("Item 1", "SOme COntent of 1"));
        arrayItems.add(new Detail("Item 2","Emty Text - without sense"));
        arrayItems.add(new Detail("Item 3","aFDJKADF SADFJFKJDJDK FFA"));
        arrayItems.add(new Detail("Item 4","Array Items"));
        arrayItems.add(new Detail("Item 5","Lorem ipsum etc pp"));
        arrayItems.add(new Detail("Item 6","wertzu iopü"));
        arrayItems.add(new Detail("Item 7","asdfg ghjk yxcv "));
        arrayItems.add(new Detail("Item 8","Sinnloser Inhalt"));
        arrayItems.add(new Detail("Item 9","In veilen SPrACHEN"));
        arrayItems.add(new Detail("Item 10","Leere Seite"));
        setContentView(R.layout.activity_detail_list);
        if (findViewById(R.id.detail_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((DetailListFragment) getFragmentManager()
                    .findFragmentById(R.id.detail_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link DetailListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(int position) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(DetailDetailFragment.ARG_ITEM_ID, position);
            DetailDetailFragment fragment = new DetailDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.detail_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, DetailDetailActivity.class);
            detailIntent.putExtra(DetailDetailFragment.ARG_ITEM_ID, position);
            startActivity(detailIntent);
        }
    }

    @Override
    public ItemAdapter get(String s) {
        return null;
    }

    @Override
    public ItemAdapter getAt(int position) {
        return arrayItems.get(position);
    }

    @Override
    public String toStringAt(int position) {
        if(position < 0 || position >= arrayItems.size())
            return "";
        return arrayItems.get(position).toString();
    }

    @Override
    public String toString(String s) {
        return null;
    }

    @Override
    public int getCount() {
        return arrayItems.size();
    }

    @Override
    public HashMap<String, ItemAdapter> getMap() {
        return null;
    }

    @Override
    public ArrayList<ItemAdapter> getArray() {
        return arrayItems;
    }
}
