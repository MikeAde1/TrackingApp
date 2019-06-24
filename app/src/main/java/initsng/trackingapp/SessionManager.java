package initsng.trackingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;



/**
 * Created by Mike on 3/21/2018.
 */

public class SessionManager {
    private static final String PREF_NAME = "final_class";
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context _context;

    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }
    public void setLat(String lat){
        editor.putString("lat",lat);
        editor.apply();
    }
    public void setLong(String lng){
        editor.putString("lng",lng);
        editor.apply();
    }
    public String getLat(){
       return pref.getString("lat", "");
    }
    public String getLong(){
        return pref.getString("lng", "");
    }
}

