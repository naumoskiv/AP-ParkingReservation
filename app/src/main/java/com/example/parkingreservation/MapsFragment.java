package com.example.parkingreservation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parkingreservation.models.Parking;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {

            Intent intent = getActivity().getIntent();
            Parking parking = intent.getParcelableExtra("parking");
            String lat = parking.getLat();
            String lng = parking.getLng();
            String cityName = parking.getCityName();
            String parkingName = parking.getParkingName();

            LatLng location = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
            LatLngBounds bounds = new LatLngBounds(new LatLng(Double.parseDouble(lat) - 0.05, Double.parseDouble(lng) - 0.05), new LatLng(Double.parseDouble(lat) + 0.05, Double.parseDouble(lng) + 0.05));
            googleMap.addMarker(new MarkerOptions().position(location).title(parkingName + ", " + cityName));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}