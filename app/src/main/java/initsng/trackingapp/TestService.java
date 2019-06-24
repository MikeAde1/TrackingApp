package initsng.trackingapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import initsng.trackingapp.MapsActivity;
import initsng.trackingapp.R;

/**
 * Created by Mike on 4/13/2018.
 */

public class TestService extends Service {
    private static final int NOTIFICATION_ID = 0;
    private GoogleApiClient mGoogleApiClient;
    boolean isServiceRunning;
    double currentLatitude,  currentLongitude;
    NotificationManager mNotifyMgr;
    private LocationRequest mLocationRequest;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startServiceWithNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && "start".equals(intent.getAction())) {
            startServiceWithNotification();
        }
        else {
            stopForeground(true);
            stopSelf();
            isServiceRunning = false;
        }
        return START_STICKY;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void startServiceWithNotification() {
        if (isServiceRunning)
            return;
        isServiceRunning = true;
        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        remoteConfig.setDefaults(R.xml.config_defaults);
        int interval = Integer.valueOf(remoteConfig.getString("location_interval"));

        Log.e("LOCATION", "Interval is " + interval);

        //Location
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that â€œthisâ€ current class will handle connection stuff
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        if (ActivityCompat.checkSelfPermission(TestService.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(TestService.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            Log.d("LOCATION", "Permission issues");
                            return;
                        }
                        Log.d("mine", "miin");
                        if (mGoogleApiClient.isConnected()) {
                            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, new com.google.android.gms.location.LocationListener() {
                                @Override
                                public void onLocationChanged(Location location) {
                                        currentLatitude = location.getLatitude();
                                        currentLongitude = location.getLongitude();
                          //      FirebaseDatabase database = FirebaseDatabase.getInstance();
                          //       DatabaseReference myRef = database.getReference(Realm.getDefaultInstance().where(Driver.class).findFirst().getListener_url());

//                                //set current location
//                                myRef.child("Current").child("lat").setValue(currentLatitude);
//                                myRef.child("Current").child("long").setValue(currentLongitude);
//                                DatabaseReference trackRef = myRef.child("Track").push();
//                                trackRef.child("lat").setValue(currentLatitude);
//                                trackRef.child("long").setValue(currentLongitude);
                                        String lat = String.valueOf(currentLatitude);
                                        String lng = String.valueOf(currentLongitude);
/*
                                        if(lat.isEmpty() && lng.isEmpty()){
                                            Toast.makeText(getApplicationContext(),"empty", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                        SessionManager sessionManager =new SessionManager(getApplicationContext());
                                        sessionManager.setLat(lat);
                                        sessionManager.setLong(lng);
                                        }
*/
                                    /*Utilities.setStringPreferences(mContext, "latitude", lat);
                                    Utilities.setStringPreferences(mContext, "longitude", lng);*/

                                        Log.d("LOCATION LAT&LNG", currentLatitude + "  always Plus " + currentLongitude);
                                        Dispatcher dispatcher = new Dispatcher(getApplicationContext());
                                        dispatcher.sendBroadcast(Dispatcher.Broadcast.UPDATE_LOCATION, lat , lng);

                                    /*if (Utilities.isOnline(mContext)) {
                                        try {
                                            Log.e("system time", System.currentTimeMillis() + "");
                                            updateDriverCurrentLocation(lat, lng);
                                            getCityName(lat, lng);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }*/
                                    }
                                /*@Override
                                public void onStatusChanged(String s, int i, Bundle bundle) {
                                }
                                @Override
                                public void onProviderEnabled(String s) {
                                }
                                @Override
                                public void onProviderDisabled(String s) {
                                }*/
                            });
                        }
                    }
                    @Override
                    public void onConnectionSuspended(int i) {
                        Log.d("LOCATION", "Connection suspended due to " + i);
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
                    }
                })
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();
        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_LOW_POWER)
                .setInterval(120 * 1000)        // 2 minute
                .setFastestInterval(120 * 1000);

        /*private void updateDriverCurrentLocation(String lat, String lng) {
            String url = getString(R.string.url) + "updateDriverCurrentLocation";
            List<Pair<String, Object>> params = new ArrayList<>();
            params.add(new Pair<String, Object>("token", token));
            params.add(new Pair<String, Object>("deviceid", Utilities.getDeviceID(mContext)));
            params.add(new Pair<String, Object>("imei", Utilities.getDeviceIMEI(mContext)));
            params.add(new Pair<String, Object>("latitude", lat));
            params.add(new Pair<String, Object>("longitude", lng));
            params.add(new Pair<String, Object>("orderId", orderId));
            Fuel.post(url, params).responseString(new Handler<String>() {
                @Override
                public void success(@NotNull Request request, @NotNull Response response, String s) {
                    try {
                        final JSONObject baseObject = new JSONObject(s);
                        if (baseObject.getBoolean("success")) {
                            //Log.d(Constant.TAG,baseObject.toString());
                        } else {
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failure(@NotNull Request request, @NotNull Response response, @NotNull FuelError fuelError) {
                    fuelError.printStackTrace();
                }
            });
        }*/
                    //}).build();

        Intent notificationIntent = new Intent(getApplicationContext(), MapsActivity.class);
        notificationIntent.setAction("gk");  // A string containing the action name
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(this)
						.setSmallIcon(R.mipmap.ic_launcher)
						.setContentTitle("title")
						.setAutoCancel(false)
						.setContentText("nin");

        Toast.makeText(getApplicationContext(),"start",Toast.LENGTH_SHORT).show();
        /*notification.flags = notification.flags | Notification.FLAG_NO_CLEAR;*/
        // NO_CLEAR makes the notification stay when the user performs a "delete all" command
        mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (mNotifyMgr != null) {
            mNotifyMgr.notify(0, mBuilder.build());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isServiceRunning = false;
        mNotifyMgr.cancel(0);
    }
}

