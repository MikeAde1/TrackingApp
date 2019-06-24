package initsng.trackingapp;

import android.*;
import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by Mike on 4/14/2018.
 */

public class OldService extends Service {
    LocationManager locationManager;
    boolean isServiceRunning;
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

    private void startServiceWithNotification() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(OldService.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(OldService.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //Log.d("bringing", "tag");
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                latitude = -34;
                longitude = 151;

                LatLng latLng = new LatLng(latitude, longitude);
                Geocoder geocoder = new Geocoder(getApplicationContext());
                /*try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    String str = addresses.get(0).getLocality() + ",";
                    str+= addresses.get(0).getCountryName();

                        Toast.makeText(getApplicationContext(),"empty", Toast.LENGTH_SHORT).show();
                */
                /*SessionManager sessionManager =new SessionManager(getApplicationContext());
                  sessionManager.setLat(String.valueOf(latitude));
                  sessionManager.setLong(String.valueOf(longitude));*/
                Dispatcher dispatcher = new Dispatcher(OldService.this);
                dispatcher.sendBroadcast(Dispatcher.Broadcast.UPDATE_LOCATION, String.valueOf(latitude) , String.valueOf(longitude));
                    /*mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));*/
                /*} catch (IOException e) {
                           e.printStackTrace();
                }
  */          }
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
