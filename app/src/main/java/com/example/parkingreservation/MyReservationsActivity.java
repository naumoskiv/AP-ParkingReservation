package com.example.parkingreservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.parkingreservation.models.Reservation;

import java.util.ArrayList;

public class MyReservationsActivity extends AppCompatActivity {

    DbHelper db;
    RecyclerView mRecyclerView;
    reservationsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservations);

        Intent incoming = getIntent();

        String username = incoming.getStringExtra("username");

        db = new DbHelper(this);
        ArrayList<Reservation> myReservations = new ArrayList<>();
        myReservations = db.getMyReservations(username);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_reservations);

        // оваа карактеристика може да се користи ако се знае дека промените
        // во содржината нема да ја сменат layout големината на RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // ќе користиме LinearLayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // и default animator (без анимации)
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // сетирање на кориснички дефиниран адаптер myAdapter (посебна класа)
        mAdapter = new reservationsAdapter(myReservations, username, R.layout.reservation_row,this);

        //прикачување на адаптерот на RecyclerView
        mRecyclerView.setAdapter(mAdapter);
    }
}