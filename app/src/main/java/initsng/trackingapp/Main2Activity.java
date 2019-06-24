package initsng.trackingapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent startIntent = new Intent(getApplicationContext(), TestService.class);
        startIntent.setAction("start");
        startService(startIntent);
        Intent notificationIntent = new Intent(getApplicationContext(), MapsActivity.class);
        notificationIntent.setAction("gk");  // A string containing the action name
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);


    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent startIntent = new Intent(getApplicationContext(), TestService.class);
        startIntent.setAction("start");
        startService(startIntent);
    }
}
