package de.devacon.xorandoui.menu;

import android.util.Log;
import android.view.InflateException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import de.devacon.uiutil.MenuEntry;

/**
 * Created by @Martin@ on 08.07.2015 16:27.
 */
public class Menu {
    static final String TAG = "# ListMenu";
    private static final int TAG_INTERUPT = -1;
    private static final String ATTR_STARTACTIVITY = "StartActivity";
    private static final String TAG_MENUITEM = "item";
    private static final String TAG_MENU = "Menu";
    private static final String TAG_XMLMENU = "XMLMenus";
    private static final int EV_START_TAG = XmlPullParser.START_TAG;
    private static final int EV_END_TAG = XmlPullParser.END_TAG;
    private static final int EV_START_DOCUMENT = XmlPullParser.START_DOCUMENT;
    private static final int EV_END_DOCUMENT = XmlPullParser.END_DOCUMENT ;

    ArrayList<MenuEntry> menuList = new ArrayList<>();
    public ArrayList<MenuEntry> getItemList() {
        return menuList;
    }
    static public Menu inflate( XmlPullParser parser ,String menuName,HashMap<String,MenuEntry> map) throws InflateException{

        if(parser == null) {
            return null;
        }
        //final AttributeSet attrs = Xml.asAttributeSet(parser);
        int depthRoot = 0;
        int type;


        try {
            while ((type = parser.next()) != XmlPullParser.START_TAG &&
                    type != XmlPullParser.END_DOCUMENT) {
                // Empty
            }
            if (type != XmlPullParser.START_TAG) {
                throw new InflateException(parser.getPositionDescription()
                        + ": No start tag found!");
            }
            depthRoot = parser.getDepth();

            final String name = parser.getName();

            if (true) {
                Log.i(TAG,"**************************");
                Log.i(TAG,"Creating menu: "
                        + name);
                Log.i(TAG, "**************************");
            }
            if(parser.getDepth() == depthRoot) {
                if(TAG_XMLMENU.equals(name)) {
                    Menu menu = new Menu();
                    type = inflateChild(parser, menuName, menu, map);
                    if(type == 0) {
                        return menu;
                    }
                    if(     parser.getDepth() != depthRoot ||
                            (type != XmlPullParser.END_TAG && type != XmlPullParser.END_DOCUMENT)) {
                        throw new InflateException(parser.getPositionDescription() +
                                ":missing end tag must be </XMLMenus>");
                    }
                }
                else {
                    throw new InflateException(parser.getPositionDescription() +
                            ":wrong root tag <" + name + "> must be XMLMenus depth=" +
                            Integer.toString(parser.getDepth()) );
                }
            }
            else {
                throw new InflateException(parser.getPositionDescription() +
                        ":wrong root tag <" + name + "> must be XMLMenus depth=" +
                        Integer.toString(parser.getDepth()) + " root=" + Integer.toString(depthRoot));
            }


        } catch (XmlPullParserException e) {
            InflateException ex = new InflateException( parser.getPositionDescription() +
                    ":other pull parser exception" + e.getMessage());
            ex.initCause(e);
            throw ex;
        } catch (IOException e) {
            InflateException ex = new InflateException(
                    parser.getPositionDescription()
                            + " io exception : " + e.getMessage());
            ex.initCause(e);
            throw ex;
        }

        return null;
    }

    private static int inflateChild(XmlPullParser parser, String menuName, Menu menu, HashMap<String, MenuEntry> map)
            throws IOException, XmlPullParserException,InflateException {
        if(!findStartTag(parser)){
            throw new InflateException(parser.getPositionDescription() + ":cannot find start tag of first element");
        }
        do {

            String nameTag = parser.getName();
            Log.i(TAG,"begin inflating " + nameTag);
            if(TAG_MENU.equals(nameTag)) {
                int count = parser.getAttributeCount();
                for(int i = 0 ; i < count ; i++) {
                    String value = parser.getAttributeValue(i);
                    String attr = parser.getAttributeName(i);
                    String space = parser.getAttributeNamespace(i);
                    Log.i(TAG,"Attrib:" + space + ":" + attr + ":" + value);
                }
                String name = parser.getAttributeValue(null,"name");
                if(!name.equals(menuName)) {
                    Log.i(TAG,"begin ignoring Menu:" + name);
                    if(!findEndTag(parser)) {
                        throw new InflateException(parser.getPositionDescription() + ": end tag <Menu> not found");
                    }

                    continue;
                }
                Log.i(TAG,"begin inflating " + nameTag);

                while(inflateItem(parser,menu,map)) {

                }
                findEndTag(parser);
            }
            else {
                findEndTag(parser);
            }
        } while ( findStartTag(parser));

        Log.i(TAG,"end of inflate");

        return 0;
    }

    private static boolean inflateItem(XmlPullParser parser, Menu menu, HashMap<String, MenuEntry> map)
            throws XmlPullParserException, InflateException, IOException {


        if(!findStartTag(parser)) {
            return false ;//throw new InflateException(parser.getPositionDescription() + ":cannot find start tag of first element");
        }
        Log.i(TAG,"begin inflating " + parser.getName());

        String name = parser.getAttributeValue(null,"name");
        Log.i(TAG,"name:" + name);

        MenuEntry entry = new MenuEntry(0,name);

        String icon = parser.getAttributeValue(null, "icon");
        MenuEntry entryIcon = map.get(icon);
        String action = parser.getAttributeValue(null, "action");
        String className = parser.getAttributeValue(null,"className");
        entry.setIcon(entryIcon != null ? entryIcon.getIcon():0);
        entry.setClassName(className);
        entry.setAction(action);
        entry.setText("");
        menu.addMenuEntry(entry);
        Log.i(TAG, "entry added " + name + ":" + icon + ":" + action + ":" + className);

        return findEndTag(parser);
    }
    private static boolean findEndTag(XmlPullParser parser) throws XmlPullParserException {
        int depth = parser.getDepth();
        int type = parser.getEventType();
        Log.i(TAG,"findEndTag:" + Integer.toString(depth));
        if(type == EV_START_TAG && parser.isEmptyElementTag())
            return true;
        try {
            while(depth <= parser.getDepth() && type != EV_END_TAG) {
                try {
                    type = parser.nextTag();
                }
                catch (XmlPullParserException e){
                    if(type == 199){
                        throw e;
                    }
                    continue;
                }
                switch(type) {
                    case EV_START_TAG:
                        Log.i(TAG,"<" + parser.getName() + ">");
                        break;
                    case EV_END_TAG:
                        Log.i(TAG, "</" + parser.getName() + ">");
                        //parser.next(); // don't change type
                        break;
                    case EV_END_DOCUMENT:
                        Log.e(TAG,"END DOCUMENT");
                        break;
                    default:
                        Log.i(TAG,"type =" + Integer.toString(type));
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            InflateException ex =  new InflateException(parser.getPositionDescription() + " pull when looking for next tag");
            ex.initCause(e);
            throw ex;
        } catch (IOException e) {
            e.printStackTrace();
            InflateException ex =  new InflateException(parser.getPositionDescription() +" io when looking for next tag");
            ex.initCause(e);
            throw ex;
        }
        if(type == EV_END_TAG) {
            Log.i(TAG, "ENde tag found" + parser.getName() + Integer.toString(parser.getDepth()));

            return true;
        }
        return false;
    }

    private static boolean findStartTag(XmlPullParser parser) throws XmlPullParserException, IOException {
        int type;
        type = parser.nextToken();
        while(type != EV_START_TAG) {
            type = parser.nextToken();
            if(type == EV_END_TAG ) {

                return false;
            }
            if(type == EV_END_DOCUMENT) {
                return false; //throw new InflateException(parser.getPositionDescription() + ": end document when looking for start tag");
            }
        }
        if(type == EV_START_TAG)
            return true;
        return false;
    }

    void addMenuEntry(MenuEntry entry) {
        menuList.add(entry);
    }
}
