package de.devacon.xorando.chitchat;

import android.os.Environment;

import java.io.File;

/**
 * Created by @Martin@ on 02.08.2015 21:26.
 */
public class Const {
    static final File rootdir = new File(Environment.getExternalStorageDirectory() + "/.ChitChat",".Person");
    public static final String PREFERENCES = "Plauderei";
    public class Preference {
        public static final String PERSONS = "Persons";
        public static final String CURRENT_PERSON = "Person";
    }

}
