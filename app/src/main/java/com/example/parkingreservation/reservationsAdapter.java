package com.example.parkingreservation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkingreservation.models.City;
import com.example.parkingreservation.models.Reservation;

import java.util.ArrayList;

public class reservationsAdapter extends RecyclerView.Adapter<reservationsAdapter.ViewHolder> {
    private ArrayList<Reservation> myList;
    private String username;
    private int rowLayout;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView date;
        public TextView time;
        public Button info;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.reservation_parking_name);
            date = (TextView) itemView.findViewById(R.id.reservation_date);
            time = (TextView) itemView.findViewById(R.id.reservation_time);
            info = (Button) itemView.findViewById(R.id.button_info);
        }
    }

    public reservationsAdapter(ArrayList<Reservation> reservations, String username, int rowLayout, Context context) {
        this.myList = reservations;
        this.username = username;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }


    public void onBindViewHolder(reservationsAdapter.ViewHolder holder, int position) {
        final Reservation reservation = myList.get(position);

        holder.name.setText(reservation.getParking());
        holder.date.setText(reservation.getDate());
        holder.time.setText(reservation.getTime());

        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ReservationConfirmation.class);
                intent.putExtra("parking", reservation.getParking());
                intent.putExtra("username", username);
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return myList == null ? 0 : myList.size();
    }
}
