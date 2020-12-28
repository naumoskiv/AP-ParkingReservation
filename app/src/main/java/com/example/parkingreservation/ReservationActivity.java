package com.example.parkingreservation;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.parkingreservation.models.City;

public class ReservationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatePicker datePicker;
    Spinner spinner;
    Button proceed;
    int mDay, mMonth, mYear;
    String mHours;
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        Fragment fragment1 = getFragmentManager().findFragmentById(R.id.fragment_1);
        Fragment fragment2 = getFragmentManager().findFragmentById(R.id.fragment_2);

        datePicker = (DatePicker) findViewById(R.id.date_widget_fragment_1);
        spinner = (Spinner) findViewById(R.id.spinner_fragment_1);
        proceed = (Button) findViewById(R.id.button_proceed);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.hours, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Intent intent = getIntent();
        final City city  = intent.getParcelableExtra("city");
        username = intent.getStringExtra("username");

        int image_resource = city.getImage();
        String name_resource = city.getName();

        ImageView imageView = findViewById(R.id.image_fragment_2);
        imageView.setImageResource(image_resource);

        TextView textView = findViewById(R.id.city_text_fragment_2);
        textView.setText(name_resource);

        spinner.setOnItemSelectedListener(this);

        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mDay = dayOfMonth;
                mMonth = monthOfYear + 1;
                mYear = year;
            }
        });


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ParkingPlacesActivity.class);
                intent.putExtra("day", mDay);
                intent.putExtra("month", mMonth);
                intent.putExtra("year", mYear);
                intent.putExtra("hours", mHours);
                intent.putExtra("city", city);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mHours = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}