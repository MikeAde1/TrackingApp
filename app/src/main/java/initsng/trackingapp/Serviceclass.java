package initsng.trackingapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.Pair;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike on 4/13/2018.
 */

public class Serviceclass extends Service {
    private GoogleApiClient mGoogleApiClient;

    //private LocationRequest mLocationRequest;
    private double currentLatitude = 0;
    private double currentLongitude = 0;
    private Context mContext;
//    private Realm realm;
//    private Driver driver;

    private String orderId = "";
    private String token;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("onService", "onCreate");


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("onService", "onStartCommand");
        mContext = this;
        try {
//            orderId = intent.getStringExtra(Constant.ORDER_ID);
//            if (orderId == null)
//                orderId = "";
//            Log.e("Order Id", "" + orderId);
            initialize();
            mGoogleApiClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Service.START_STICKY;
    }

    private void initialize() {

//        realm = Realm.getDefaultInstance();
//        driver = realm.where(Driver.class).findFirst();



        Log.e("LOCATION", "Location services started");
        //
      FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        remoteConfig.setDefaults(R.xml.config_defaults);
        int interval = Integer.valueOf(remoteConfig.getString("location_interval"));

        //
        Log.e("LOCATION", "Interval is " + interval);

        //Location
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that â€œthisâ€ current class will handle connection stuff
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        if (ActivityCompat.checkSelfPermission(Serviceclass.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(Serviceclass.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            Log.d("LOCATION", "Permission issues");
                            return;
                        }
                        if (mGoogleApiClient.isConnected()) {
                            //Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                         /*   LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, new LocationListener() {
                                @Override
                                public void onLocationChanged(Location location) {
                                    currentLatitude = location.getLatitude();
                                    currentLongitude = location.getLongitude();
//                                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                                DatabaseReference myRef = database.getReference(Realm.getDefaultInstance().where(Driver.class).findFirst().getListener_url());
//
//                                //set current location
//                                myRef.child("Current").child("lat").setValue(currentLatitude);
//                                myRef.child("Current").child("long").setValue(currentLongitude);
//                                DatabaseReference trackRef = myRef.child("Track").push();
//                                trackRef.child("lat").setValue(currentLatitude);
//                                trackRef.child("long").setValue(currentLongitude);
                                    String lat = String.valueOf(currentLatitude);
                                    String lng = String.valueOf(currentLongitude);

                                    *//*Utilities.setStringPreferences(mContext, "latitude", lat);
                                    Utilities.setStringPreferences(mContext, "longitude", lng);*//*

                                    Log.d("LOCATION LAT&LNG", currentLatitude + "  always Plus " + currentLongitude);

                                    *//*if (Utilities.isOnline(mContext)) {
                                        try {
                                            Log.e("system time", System.currentTimeMillis() + "");
                                            updateDriverCurrentLocation(lat, lng);*//**//*
                                            getCityName(lat, lng);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }*//*
                                }

                                @Override
                                public void onStatusChanged(String s, int i, Bundle bundle) {

                                }

                                @Override
                                public void onProviderEnabled(String s) {

                                }

                                @Override
                                public void onProviderDisabled(String s) {

                                }
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
                .build();*/

        // Create the LocationRequest object
        //mLocationRequest = LocationRequest.create()
          //      .setPriority(LocationRequest.PRIORITY_LOW_POWER)
            //    .setInterval(120 * 1000)        // 2 minute
              //  .setFastestInterval(120 * 1000);
    }}

                    @Override
                    public void onConnectionSuspended(int i) {

                    }

/*
    private void updateDriverCurrentLocation(String lat, String lng) {
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
}).build();}}
































