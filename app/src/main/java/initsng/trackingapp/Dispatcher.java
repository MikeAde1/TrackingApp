package initsng.trackingapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Mike on 3/13/2018.
 */

public class Dispatcher {
    public static final String TAG = "header";
    public static final String DispatcherBroadCast = "BACKEND_BROADCAST_UPDATE";
    private Context context;
    //backend is  for all firebase authentications
    public Dispatcher(Context context){
        this.context = context;
    }
    //used in signIn class
    public enum Broadcast {
        UPDATE_LOCATION
    }

    public void sendBroadcast(Broadcast type, String message, String message2){
        Log.e("dispatcher","Broadcast::"+type);
        Intent intent = new Intent(DispatcherBroadCast);
        intent.putExtra("broadcast_type",type);
        intent.putExtra("message",message);
        intent.putExtra("message2",message2);
        context.sendBroadcast(intent);
    }
}
