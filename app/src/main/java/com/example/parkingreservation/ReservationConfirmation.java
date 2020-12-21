package com.example.parkingreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.parkingreservation.models.Parking;

public class ReservationConfirmation extends AppCompatActivity {

    TextView parkingName;
    Button navigate;
    Button openMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_confirmation);

        Fragment fragment_map = getFragmentManager().findFragmentById(R.id.map_fragment);
        Fragment fragment_confirmation = getFragmentManager().findFragmentById(R.id.confirmation_fragment);

        parkingName = (TextView) findViewById(R.id.chosen_parking_name);
        navigate = (Button) findViewById(R.id.button_navigate);
        openMap = (Button) findViewById(R.id.button_open_map);

        Intent incoming = getIntent();
        final Parking parking = incoming.getParcelableExtra("parking");
        String name = parking.getParkingName();

        final String lat = parking.getLat();
        final String lng = parking.getLng();

        parkingName.setText(name);

        navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q=" + lat + "," + lng + "&model=d"));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                intent.putExtra("parking", parking);
                startActivity(intent);
            }
        });
    }
}