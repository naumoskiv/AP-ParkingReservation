package com.example.parkingreservation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkingreservation.models.City;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {

    private ArrayList<City> myList;
    private int rowLayout;
    private Context mContext;

    // Референца на views за секој податок
    // Комплексни податоци може да бараат повеќе views per item
    // Пристап до сите views за податок се дефинира во view holder

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView myName;
        public ImageView Pic;

        public ViewHolder(View itemView) {
            super(itemView);
            myName = (TextView) itemView.findViewById(R.id.Name);
            Pic = (ImageView) itemView.findViewById(R.id.picture);
        }
    }

    // конструктор
    public myAdapter(ArrayList<City> cityList, int rowLayout, Context context) {
        this.myList = cityList;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    // Креирање нови views (повикано од layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    // Замена на содржината во view (повикано од layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        /*viewHolder.myName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                Toast.makeText(mContext, tv.getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, ReservationActivity.class);
                mContext.startActivity(intent);
            }
        });*/

        City entry = myList.get(i);
        viewHolder.Pic.setImageResource(entry.getImage());
        viewHolder.myName.setText(entry.getName());
        viewHolder.myName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ReservationActivity.class);
                intent.putExtra("city", myList.get(i));
                mContext.startActivity(intent);
            }
        });
    }

    // Пресметка на големината на податочното множество (повикано од layout manager)
    @Override
    public int getItemCount() {
        return myList == null ? 0 : myList.size();
    }

}
