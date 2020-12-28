package com.example.parkingreservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.parkingreservation.models.City;
import com.example.parkingreservation.models.Parking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HomeActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    myAdapter mAdapter;
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
        setContentView(R.layout.activity_home);
        db = new DbHelper(this);

        Intent incoming = getIntent();
        username = incoming.getStringExtra("username");

        ArrayList<City> citiess = new ArrayList<>();
        citiess = db.getAllCities();

        /*ArrayList<City> cities = new ArrayList<>();
        ArrayList<Parking> skopje = new ArrayList<>();
        ArrayList<Parking> bitola = new ArrayList<>();
        ArrayList<Parking> ohrid = new ArrayList<>();
        ArrayList<Parking> kumanovo = new ArrayList<>();
        ArrayList<Parking> kicevo = new ArrayList<>();
        ArrayList<Parking> prilep = new ArrayList<>();
        ArrayList<Parking> strumica = new ArrayList<>();
        ArrayList<Parking> struga = new ArrayList<>();
        ArrayList<Parking> veles = new ArrayList<>();
        ArrayList<Parking> gevgelija = new ArrayList<>();*/


        /*skopje.add(new Parking("Skopje City Mall", 300, 120, "Skopje", "42.00492991775837", "21.391735808202956"));
        skopje.add(new Parking("Ramstore Mall", 200, 130, "Skopje", "41.991922973379225", "21.427919309629456"));
        skopje.add(new Parking("Vero Taftalidze", 80, 45, "Skopje", "41.99684691378695", "21.40550635381546"));
        cities.add(new City("Skopje", R.drawable.skopje));

        bitola.add(new Parking("Sportska Sala Parking", 200, 30, "Bitola", "41.0227835298321", "21.33663881479847"));
        bitola.add(new Parking("Shirok Sokak", 50, 15, "Bitola", "41.03111786084998", "21.33623121172701"));
        cities.add(new City("Bitola", R.drawable.bitola));

        ohrid.add(new Parking("Parking Pristaniste", 200, 180, "Ohrid", "41.11259402722531", "20.799428875675396"));
        ohrid.add(new Parking("Parking Plazi", 100, 35, "Ohrid", "41.10280214528415", "20.807460554680524"));
        cities.add(new City("Ohrid", R.drawable.ohrid));

        kumanovo.add(new Parking("Sokolana Parking", 80, 75, "Kumanovo", "42.1291047307248", "21.719541674282006"));
        cities.add(new City("Kumanovo", R.drawable.kumanovo));

        kicevo.add(new Parking("Parking Osnoven Sud", 50, 25, "Kicevo", "41.51251662927505", "20.959971852516848"));
        cities.add(new City("Kicevo", R.drawable.kicevo));

        prilep.add(new Parking("Parking Centar", 200, 120, "Prilep", "41.34556484188022", "21.552453230157727"));
        prilep.add(new Parking("Katna Garaza", 150, 120, "Prilep", "41.34380408233429", "21.551862938884636"));
        cities.add(new City("Prilep", R.drawable.prilep));

        strumica.add(new Parking("Global Parking", 300, 70, "Strumica", "41.43969248241463", "22.63999400970511"));
        cities.add(new City("Strumica", R.drawable.strumica));

        struga.add(new Parking("Hotel Drim Parking", 200, 150, "Struga", "41.17361811795702", "20.680311736910248"));
        cities.add(new City("Struga", R.drawable.struga));

        veles.add(new Parking("Parking Gimnazija", 300, 120, "Veles", "41.71816684336158", "21.77288032521455"));
        cities.add(new City("Veles", R.drawable.veles));

        gevgelija.add(new Parking("Gradski Pazar Parking", 100, 30, "Gevgelija", "41.14255626997016", "22.493835722781025"));
        cities.add(new City("Gevgelija", R.drawable.gevgelija));*/


        //сетирање на RecyclerView контејнерот
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_list);

        // оваа карактеристика може да се користи ако се знае дека промените
        // во содржината нема да ја сменат layout големината на RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // ќе користиме LinearLayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // и default animator (без анимации)
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // сетирање на кориснички дефиниран адаптер myAdapter (посебна класа)
        mAdapter = new myAdapter(citiess, R.layout.my_row, username, this);

        //прикачување на адаптерот на RecyclerView
        mRecyclerView.setAdapter(mAdapter);

    }


}

