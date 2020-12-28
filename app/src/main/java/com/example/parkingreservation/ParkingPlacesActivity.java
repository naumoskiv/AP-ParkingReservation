package com.example.parkingreservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.parkingreservation.models.City;
import com.example.parkingreservation.models.Parking;

import java.util.ArrayList;

public class ParkingPlacesActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ParkingsAdapter mAdapter;
    DbHelper db;

    String[] parkingNames;
    int[] capacity;
    int [] reservations;
    String city_name;
    String username;

    TextView dateText;
    TextView numberHours;
    TextView cityName;

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
        setContentView(R.layout.activity_parking_places);

        dateText = (TextView) findViewById(R.id.chosen_date_parking);
        numberHours = (TextView) findViewById(R.id.number_of_hours);
        cityName = (TextView) findViewById(R.id.city_name_parking_activity);
        db = new DbHelper(this);


        Intent incoming = getIntent();
        username = incoming.getStringExtra("username");

        final City city = incoming.getParcelableExtra("city");
        assert city != null;
        city_name = city.getName();

        parkingNames = db.getParkingNamesInCity(city_name);
        capacity = db.getCapacity(city_name);

        int day = incoming.getIntExtra("day", 0);
        int month = incoming.getIntExtra("month", 0);
        int year = incoming.getIntExtra("year", 0);
        String hours = incoming.getStringExtra("hours");
        String date = day + "/" + month + "/" + year;

        reservations = db.getReservations(date, hours, parkingNames);

        date = "Reservation for " + date;

        dateText.setText(date);
        numberHours.setText(hours);
        cityName.setText(city_name);

        //сетирање на RecyclerView контејнерот
        mRecyclerView = (RecyclerView) findViewById(R.id.parking_recycler_view_list);

        // оваа карактеристика може да се користи ако се знае дека промените
        // во содржината нема да ја сменат layout големината на RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // ќе користиме LinearLayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // и default animator (без анимации)
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // сетирање на кориснички дефиниран адаптер myAdapter (посебна класа)
        mAdapter = new ParkingsAdapter(parkingNames, capacity, reservations, R.layout.parking_row, date, hours, username, this);

        //прикачување на адаптерот на RecyclerView
        mRecyclerView.setAdapter(mAdapter);

    }
}