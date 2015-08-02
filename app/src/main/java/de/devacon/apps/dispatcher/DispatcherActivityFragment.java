package de.devacon.apps.dispatcher;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import de.devacon.uiutil.MenuEntry;
import de.devacon.uiutil.MenuEntryMap;
import de.devacon.xorandoui.menu.Menu;


/**
 * A placeholder fragment containing a simple view.
 */
public class DispatcherActivityFragment extends Fragment {

    private static final String TAG = "# Dispatcher:";

    private class MenuEntryAdapter extends ArrayAdapter<MenuEntry> {
        LayoutInflater inflater = null;
        /**
         * Constructor
         *
         * @param context            The current context.
         * @param textViewResourceId The resource ID for a layout file containing a TextView to use when
         */
        public MenuEntryAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            inflater = LayoutInflater.from(context);
        }

        /**
         * Constructor
         *
         * @param context            The current context.
         * @param resource           The resource ID for a layout file containing a layout to use when
         *                           instantiating views.
         * @param textViewResourceId The id of the TextView within the layout resource to be populated
         */
        public MenuEntryAdapter(Context context, int resource, int textViewResourceId) {
            super(context, resource, textViewResourceId);
        }
        /**
         * {@inheritDoc}
         *
         * @param position
         * @param convertView
         * @param parent
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MenuEntry entry = getItem(position);
            if(convertView == null){
                convertView = inflater.inflate(R.layout.fragment_detail,parent,false);
            }
            if(entry == null) {
                Log.i(TAG, "empty entry at " + Integer.toString(position));
            }
            ImageView imageView = (ImageView) convertView.findViewById(R.id.question);
            imageView.setImageResource(entry.getIcon());

            int layout = getResources().getConfiguration().screenLayout;
            TextView text = (TextView) convertView.findViewById(R.id.textview);
            String entryText = entry.getText();
            text.setText(
                    entryText == null || entryText.isEmpty() ? entry.getName() : entry.getText());
            return convertView ; //super.getView(position, convertView, parent);
        }

    }
    private class IntAdapter extends ArrayAdapter<Integer> {
        LayoutInflater inflater=null;

        /**
         * {@inheritDoc}
         *
         * @param position
         * @param convertView
         * @param parent
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Integer integer = getItem(position);
            if(convertView == null){
                convertView = inflater.inflate(R.layout.fragment_detail,parent,false);
            }
            ImageView imageView = (ImageView) convertView.findViewById(R.id.question);
            imageView.setImageResource(getItem(position));
            String name = getResources().getResourceName(getItem(position));
            int layout = getResources().getConfiguration().screenLayout;
            ((TextView) convertView.findViewById(R.id.textview)).setText(name);
            return convertView ; //super.getView(position, convertView, parent);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getCount() {
            return super.getCount();
        }

        /**
         * {@inheritDoc}
         *
         * @param position
         */
        @Override
        public Integer getItem(int position) {
            return super.getItem(position);
        }

        public IntAdapter(Context context,Integer array[]) {
            super(context, R.layout.fragment_detail,array);
            inflater = LayoutInflater.from(context);
        }
    }
    private WebView webView;
    public DispatcherActivityFragment() {
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
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after {@link #onCreateView}
     * and before {@link #onViewStateRestored(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(webView == null) {
            return;
        }
        WebSettings settings = webView.getSettings();
        settings.setAllowContentAccess(true);
        settings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            /**
             * Tell the client to display a javascript alert dialog.  If the client
             * returns true, WebView will assume that the client will handle the
             * dialog.  If the client returns false, it will continue execution.
             *
             * @param view    The WebView that initiated the callback.
             * @param url     The url of the page requesting the dialog.
             * @param message Message to be displayed in the window.
             * @param result  A JsResult to confirm that the user hit enter.
             * @return boolean Whether the client will handle the alert dialog.
             */
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            /**
             * Tell the client to display a confirm dialog to the user. If the client
             * returns true, WebView will assume that the client will handle the
             * confirm dialog and call the appropriate JsResult method. If the
             * client returns false, a default value of false will be returned to
             * javascript. The default behavior is to return false.
             *
             * @param view    The WebView that initiated the callback.
             * @param url     The url of the page requesting the dialog.
             * @param message Message to be displayed in the window.
             * @param result  A JsResult used to send the user's response to
             *                javascript.
             * @return boolean Whether the client will handle the confirm dialog.
             */
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            /**
             * Tell the client to display a prompt dialog to the user. If the client
             * returns true, WebView will assume that the client will handle the
             * prompt dialog and call the appropriate JsPromptResult method. If the
             * client returns false, a default value of false will be returned to to
             * javascript. The default behavior is to return false.
             *
             * @param view         The WebView that initiated the callback.
             * @param url          The url of the page requesting the dialog.
             * @param message      Message to be displayed in the window.
             * @param defaultValue The default value displayed in the prompt dialog.
             * @param result       A JsPromptResult used to send the user's reponse to
             *                     javascript.
             * @return boolean Whether the client will handle the prompt dialog.
             */
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            /**
             * Tell the client to display a dialog to confirm navigation away from the
             * current page. This is the result of the onbeforeunload javascript event.
             * If the client returns true, WebView will assume that the client will
             * handle the confirm dialog and call the appropriate JsResult method. If
             * the client returns false, a default value of true will be returned to
             * javascript to accept navigation away from the current page. The default
             * behavior is to return false. Setting the JsResult to true will navigate
             * away from the current page, false will cancel the navigation.
             *
             * @param view    The WebView that initiated the callback.
             * @param url     The url of the page requesting the dialog.
             * @param message Message to be displayed in the window.
             * @param result  A JsResult used to send the user's response to
             *                javascript.
             * @return boolean Whether the client will handle the confirm dialog.
             */
            @Override
            public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
                return super.onJsBeforeUnload(view, url, message, result);
            }

            /**
             * Report a JavaScript console message to the host application. The ChromeClient
             * should override this to process the log message as they see fit.
             *
             * @param consoleMessage Object containing details of the console message.
             * @return true if the message is handled by the client.
             */
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return super.onConsoleMessage(consoleMessage);
            }

            /**
             * Tell the host application the current progress of loading a page.
             *
             * @param view        The WebView that initiated the callback.
             * @param newProgress Current page loading progress, represented by
             */
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
        //webView.loadData("<html><body><h1>Page Header</h1><p><a href=\"http://Martin-Brunn.de\">Calender test</a></p></body></html>","text/html","utf-8");
        webView.loadUrl("https://Martin-Brunn.de/");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_dispatch, container, false);
        ListView listView = (ListView)v.findViewById(R.id.listview);
        Context context = getActivity().getApplicationContext();
        Resources res = context.getResources();
        int id = res.getIdentifier("xml_menus", "xml", context.getPackageName());
        XmlPullParser parser = null;
        try {
            parser = res.getXml(id);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        MenuEntryMap map = null;
        Class[] classes = R.class.getClasses();

        for(Class field: classes) {
            if(field.getSimpleName().equals("drawable")) {
                map = new MenuEntryMap(field);
            }
        }
        Menu menu = null;
        if(map != null) {
            menu = map.populate(getResources(),R.xml.xml_menus,"default");
        }

        MenuEntryAdapter adapter = new MenuEntryAdapter(getActivity().getApplicationContext(), R.layout.fragment_detail);
        adapter.addAll(menu.getItemList());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                MenuEntry i = (MenuEntry) parent.getAdapter().getItem(position);
                if(i != null) {
                    executeAction(i);
                }
            }
        });
        //CircleImageView circleImageView = (CircleImageView )v.findViewById(R.id.divider);
        webView = null ; //(WebView)v.findViewById(R.id.webview);
        return v;
    }
    void executeAction(MenuEntry entry) {
        String string = entry.getName() + ":" + entry.getAction() + ":\n" + entry.getText().toString();
        Toast t;

        Toast.makeText(getActivity().getApplicationContext(),string,Toast.LENGTH_LONG).show();
        if(entry.getAction().equals("startActivity")) {
            Class c = null;
            try {
                c = entry.getaClass();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            Intent intent = new Intent(getActivity(),c);
            startActivity(intent);
        }

    }
}
