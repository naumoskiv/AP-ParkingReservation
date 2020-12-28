package com.example.parkingreservation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkingreservation.models.City;
import com.example.parkingreservation.models.Parking;

import java.util.ArrayList;

public class ParkingsAdapter extends RecyclerView.Adapter<ParkingsAdapter.ViewHolder>{

    private ArrayList<Parking> myList;
    DbHelper db;
    private int rowLayout;
    private Context mContext;
    private String mDate;
    private String mTime;
    private String mUsername;
    //private String cityName;
    private String[] parkingNames;
    private int[] capacity;
    private int[] reservations;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mName;
        public TextView freePlaces;
        public TextView takenPlaces;
        public Button buttonReserve;

        public ViewHolder( View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.parking_name);
            freePlaces = (TextView) itemView.findViewById(R.id.free_places);
            takenPlaces = (TextView) itemView.findViewById(R.id.taken_places);
            buttonReserve = (Button) itemView.findViewById(R.id.button_reserve);
        }
    }

    public ParkingsAdapter(String[] parkingNames, int[] capacity, int[] reservations, int rowLayout, String date, String time, String username, Context context) {
        this.parkingNames = parkingNames;
        this.capacity = capacity;
        this.reservations = reservations;
        this.rowLayout = rowLayout;
        this.mDate = date;
        this.mTime = time;
        this.mUsername = username;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        db = new DbHelper(mContext);
        final String parking_name = parkingNames[position];
        int takenspots = db.getNumReservations(parking_name, mDate, mTime);
        int available = capacity[position] - takenspots;
        holder.mName.setText(parking_name);
        String taken = String.valueOf(takenspots);
        taken = taken + " taken places";
        String free = String.valueOf(available);
        free = free + " free places";
        holder.takenPlaces.setText(taken);
        holder.freePlaces.setText(free);


        holder.buttonReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper db = new DbHelper(mContext);
                int numberReservations = db.limitReservations(mUsername);
                if (numberReservations > 2) {
                    Toast.makeText(mContext, "Reservation limit exceeded", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean flag = db.addReservation(mUsername, parking_name, mDate, mTime);
                    if (flag) {
                        Toast.makeText(mContext, "Reservation successful", Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = new Intent(mContext, ReservationConfirmation.class);
                    intent.putExtra("parking", parking_name);
                    intent.putExtra("username", mUsername);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return parkingNames.length;
    }

}
