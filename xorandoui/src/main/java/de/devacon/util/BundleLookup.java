package de.devacon.util;

import android.os.Bundle;

/**
 * Created by Harald on 02.07.2015.
 */
public class BundleLookup extends Lookup {
    public BundleLookup(Bundle bundle){
        for(String e :bundle.keySet()){
            Object obj = bundle.get(e);
            if(obj instanceof String){
                table.put(e,(String)obj);
            }
            else {
                table.put(e, obj.toString());
            }
        }

    }
}
