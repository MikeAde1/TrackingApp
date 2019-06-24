package initsng.trackingapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    BroadcastReceiver broadcastReceiver;
    //String lat, lng;
    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //gets an intent or message from the location changing notifier

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (mMap != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
            }
        assert locationManager != null;
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    LatLng latLng = new LatLng(latitude, longitude);
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        String str = addresses.get(0).getLocality() + ",";
                        str += addresses.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    /*LatLng sydney = new LatLng(latitude,longitude);
                    mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {
                }

                @Override
                public void onProviderEnabled(String s) {
                    Toast.makeText(getApplicationContext(), "enabled", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onProviderDisabled(String s) {
                    Toast.makeText(getApplicationContext(), "nabled", Toast.LENGTH_SHORT).show();
                }
            });
            //Add a marker in Sydney and move the camera
            SessionManager sessionManager = new SessionManager(MapsActivity.this);
        /*if (sessionManager.getLat() != null && sessionManager.getLong()!= null) {*/
            // LatLng sydney = new LatLng(Double.parseDouble(sessionManager.getLat()), Double.parseDouble(sessionManager.getLong()));

            //}
        }
        else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        LatLng latLng = new LatLng(latitude, longitude);
                        Geocoder geocoder = new Geocoder(MapsActivity.this);
                        try {
                            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                            String str = addresses.get(0).getLocality() + ",";
                            str += addresses.get(0).getCountryName();
                            mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    /*LatLng sydney = new LatLng(latitude,longitude);
                    mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {
                    }

                    @Override
                    public void onProviderEnabled(String s) {
                        Toast.makeText(getApplicationContext(), "enabled", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProviderDisabled(String s) {
                        Toast.makeText(getApplicationContext(), "nabled", Toast.LENGTH_SHORT).show();
                    }
                });
                //Add a marker in Sydney and move the camera
                SessionManager sessionManager = new SessionManager(MapsActivity.this);
        /*if (sessionManager.getLat() != null && sessionManager.getLong()!= null) {*/
                // LatLng sydney = new LatLng(Double.parseDouble(sessionManager.getLat()), Double.parseDouble(sessionManager.getLong()));

                //}
            }
    }
    
/*    private void setUpReceivers() {
             broadcastReceiver = new BroadcastReceiver() {
                 @Override
                 public void onReceive(Context context, Intent intent) {
                     Dispatcher.Broadcast broadcastType = (Dispatcher.Broadcast) intent.getExtras().get("broadcast_type");
                     final String message = intent.getExtras().getString("message");
                     final String message2 = intent.getExtras().getString("message2");
                     assert broadcastType != null;

                     switch (broadcastType) {
                         case UPDATE_LOCATION:
                             lat = message;
                             lng = message2;
                     }
                 }
             };
      *//*  SessionManager sessionManager = new SessionManager(MapsActivity.this);
        if (sessionManager.getLat() != null && sessionManager.getLong() != null){
            LatLng sydney = new LatLng(Double.parseDouble(sessionManager.getLat()),Double.parseDouble(sessionManager.getLong()));*//*
    }*/

    @Override
    protected void onStart() {
        super.onStart();
  //      setUpReceivers();
        registerReceiver(broadcastReceiver,new IntentFilter(Dispatcher.DispatcherBroadCast));
/*
        Intent startIntent = new Intent(getApplicationContext(), OldService.class);
        startIntent.setAction("start");
        startService(startIntent);
*/

/*
        LatLng sydney = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(broadcastReceiver);
    }
    /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
    */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent startIntent = new Intent(getApplicationContext(), TestService.class);
        startIntent.setAction("start");
        startService(startIntent);
    }
}
