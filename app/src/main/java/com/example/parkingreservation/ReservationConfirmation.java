package com.example.parkingreservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.parkingreservation.models.Parking;

public class ReservationConfirmation extends AppCompatActivity {

    TextView parkingName;
    Button navigate;
    Button openMap;
    String[] latitude;
    String[] longitude;
    DbHelper db;
    String username;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.reservations_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item1) {
            Intent intent = new Intent(this, MyReservationsActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_confirmation);

        Fragment fragment_map = getFragmentManager().findFragmentById(R.id.map_fragment);
        Fragment fragment_confirmation = getFragmentManager().findFragmentById(R.id.confirmation_fragment);

        parkingName = (TextView) findViewById(R.id.chosen_parking_name);
        navigate = (Button) findViewById(R.id.button_navigate);
        openMap = (Button) findViewById(R.id.button_open_map);

        final Intent incoming = getIntent();
        final String parking_name = incoming.getStringExtra("parking");
        username = incoming.getStringExtra("username");

        //final Parking parking = incoming.getParcelableExtra("parking");
        //String name = parking.getParkingName();

        db = new DbHelper(this);

        latitude = db.getLatitude(parking_name);
        longitude = db.getLongitude(parking_name);
        //final String lat = parking.getLat();
        //final String lng = parking.getLng();

        parkingName.setText(parking_name);

        navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q=" + latitude[0] + "," + longitude[0] + "&model=d"));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                intent.putExtra("parking", parking_name);
                intent.putExtra("lat", latitude[0]);
                intent.putExtra("lng", longitude[0]);
                startActivity(intent);
            }
        });
    }
}