package com.example.parkingreservation;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.parkingreservation.models.Parking;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class NavigationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng myLocation;
    private String lat;
    private String lng;
    //private String cityName;
    private String parkingName;
    private static final int req_code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent incoming = getIntent();
        Parking parking = incoming.getParcelableExtra("parking");
        parkingName = incoming.getStringExtra("parking");
        lat = incoming.getStringExtra("lat");
        lng = incoming.getStringExtra("lng");
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

        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        LatLngBounds bounds = new LatLngBounds(new LatLng(Double.parseDouble(lat) - 0.01, Double.parseDouble(lng) - 0.01), new LatLng(Double.parseDouble(lat) + 0.01, Double.parseDouble(lng) + 0.01));
        mMap.addMarker(new MarkerOptions().position(location).title(parkingName));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));

        myLocation = getMyLocation();
        if (myLocation == null) {
            Toast.makeText(this, "Cannot access location. Check permissions.", Toast.LENGTH_LONG).show();
        } else {
            mMap.addMarker(new MarkerOptions()
                    .position(myLocation)
                    .title("You")
            );
        }
    }

    private LatLng getMyLocation() {
        // обид за добивање локација на еден од три начини: GPS, cell/wifi мрежа и пасивен режим
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, req_code);
        }

        Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (loc == null) {
            loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (loc == null) {
            loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        }
        if (loc == null) {
            return null;
        } else {
            double myLat = loc.getLatitude();
            double myLng = loc.getLongitude();
            return new LatLng(myLat, myLng);
        }
    }
}