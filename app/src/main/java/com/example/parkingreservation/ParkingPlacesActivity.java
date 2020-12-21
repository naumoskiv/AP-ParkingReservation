package com.example.parkingreservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.parkingreservation.models.City;
import com.example.parkingreservation.models.Parking;

import java.util.ArrayList;

public class ParkingPlacesActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ParkingsAdapter mAdapter;


    TextView dateText;
    TextView numberHours;
    TextView cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_places);

        dateText = (TextView) findViewById(R.id.chosen_date_parking);
        numberHours = (TextView) findViewById(R.id.number_of_hours);
        cityName = (TextView) findViewById(R.id.city_name_parking_activity);

        Intent incoming = getIntent();
        final City city = incoming.getParcelableExtra("city");
        int day = incoming.getIntExtra("day", 0);
        int month = incoming.getIntExtra("month", 0);
        int year = incoming.getIntExtra("year", 0);
        String hours = incoming.getStringExtra("hours");
        String date = day + "/" + month + "/" + year;

        assert city != null;
        String city_name = city.getName();

        hours = hours + " hours";
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
        mAdapter = new ParkingsAdapter(city.getParkings(), R.layout.parking_row, this);

        //прикачување на адаптерот на RecyclerView
        mRecyclerView.setAdapter(mAdapter);

    }
}