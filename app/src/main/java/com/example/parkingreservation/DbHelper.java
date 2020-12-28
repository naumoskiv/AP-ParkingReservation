package com.example.parkingreservation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.parkingreservation.models.City;
import com.example.parkingreservation.models.Parking;
import com.example.parkingreservation.models.Reservation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "database.db";
    //public String DBPATH;


    public DbHelper(Context context) {
        super(context, "database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT)");
        MyDB.execSQL("create Table cities(name TEXT primary key, image INTEGER)");
        MyDB.execSQL("create Table parkings(name TEXT primary key, capacity INTEGER, city TEXT, lat TEXT, lng TEXT)");
        MyDB.execSQL("create Table reservations(reservation_id INTEGER primary key autoincrement, user TEXT, parking TEXT, date TEXT, timeframe TEXT)");

        MyDB.execSQL("INSERT INTO cities VALUES ('Skopje', 2131230895);");
        MyDB.execSQL("INSERT INTO cities VALUES ('Ohrid', 2131230893);");
        MyDB.execSQL("INSERT INTO cities VALUES ('Kicevo', 2131230861);");
        MyDB.execSQL("INSERT INTO cities VALUES ('Kumanovo', 2131230862);");
        MyDB.execSQL("INSERT INTO cities VALUES ('Prilep', 2131230894);");
        MyDB.execSQL("INSERT INTO cities VALUES ('Strumica', 2131230897);");
        MyDB.execSQL("INSERT INTO cities VALUES ('Struga', 2131230896);");
        MyDB.execSQL("INSERT INTO cities VALUES ('Veles', 2131230901);");
        MyDB.execSQL("INSERT INTO cities VALUES ('Gevgelija', 2131230850);");

        MyDB.execSQL("INSERT INTO parkings VALUES ('Skopje City Mall', 300, 'Skopje', '42.00492991775837', '21.391735808202956');");
        MyDB.execSQL("INSERT INTO parkings VALUES ('Ramstore Mall', 200, 'Skopje', '41.991922973379225', '21.427919309629456');");
        MyDB.execSQL("INSERT INTO parkings VALUES ('Vero Taftalidze', 80, 'Skopje', '41.99684691378695', '21.40550635381546');");
        MyDB.execSQL("INSERT INTO parkings VALUES ('Parking Pristaniste', 200, 'Ohrid', '41.11259402722531', '20.799428875675396');");
        MyDB.execSQL("INSERT INTO parkings VALUES ('Parking Plazi', 100, 'Ohrid', '41.10280214528415', '20.807460554680524');");
        MyDB.execSQL("INSERT INTO parkings VALUES ('Sokolana Parking', 80, 'Kumanovo', '42.1291047307248', '21.719541674282006');");
        MyDB.execSQL("INSERT INTO parkings VALUES ('Parking Osnoven Sud', 50, 'Kicevo', '41.51251662927505', '20.959971852516848');");
        MyDB.execSQL("INSERT INTO parkings VALUES ('Parking Centar', 100, 'Prilep', '41.34556484188022', '21.552453230157727');");
        MyDB.execSQL("INSERT INTO parkings VALUES ('Katna Garaza', 150, 'Prilep', '41.34380408233429', '21.551862938884636');");
        MyDB.execSQL("INSERT INTO parkings VALUES ('Global Parking', 250, 'Strumica', '41.43969248241463', '22.63999400970511');");
        MyDB.execSQL("INSERT INTO parkings VALUES ('Hotel Drim Parking', 120, 'Struga', '41.17361811795702', '20.680311736910248');");
        MyDB.execSQL("INSERT INTO parkings VALUES ('Parking Gimnazija', 60, 'Veles', '41.71816684336158', '21.77288032521455');");
        MyDB.execSQL("INSERT INTO parkings VALUES ('Gradski Pazar Parking', 90, 'Gevgelija', '41.14255626997016', '22.493835722781025');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("DROP TABLE IF EXISTS users");
        MyDB.execSQL("DROP TABLE IF EXISTS cities");
        MyDB.execSQL("DROP TABLE IF EXISTS parkings");
        MyDB.execSQL("DROP TABLE IF EXISTS reservations");
    }



 /*   private boolean checkDatabase () {
        try{
            final String mPath = DBPATH + DBNAME;
            final File file = new File (mPath);
            if (file.exists())
                return true;
            else
                return false;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    // Copy the database into local storage
    private void copyDatabase() throws IOException {
        try{
            InputStream mInputStream = context.getAssets().open(DBNAME);
            String outFileName = DBPATH + DBNAME;
            OutputStream mOutputStream = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = mInputStream.read(buffer))> 0){
                mOutputStream.write(buffer, 0, length);
            }
            mOutputStream.flush();
            mOutputStream.close();
            mInputStream.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    // Create the database
    public void createDatabase () throws IOException{
        boolean mDatabaseExists = checkDatabase();
        if (!mDatabaseExists)
        {
            this.getReadableDatabase();
            this.close();
            try{
                copyDatabase();
            }catch (IOException mIOException){
                mIOException.printStackTrace();
                throw new Error("Error copying database");
            } finally {
                this.close();
            }
        }
    }

    @Override
    public synchronized void close(){
        if(dbHelper != null)
            dbHelper.close();
        SQLiteDatabase.releaseMemory();
        super.close();
    }
*/






    public Boolean insertUser(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = MyDB.insert("users", null, contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public Boolean checkUsername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public int limitReservations(String user) {
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("Select * from reservations where user =?", new String [] {user});

        int numberReservations = cursor.getCount();

        cursor.close();
        mydb.close();
        return numberReservations;
    }


    public ArrayList<Reservation> getMyReservations(String username) {
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("Select * from reservations where user =?", new String [] {username});

        ArrayList<Reservation> myReservations = new ArrayList<>();
        int i=0;
        while (cursor.moveToNext()){
            myReservations.add(new Reservation(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            //cities[i] = cursor.getString(1);
            i++;
        }
        cursor.close();
        mydb.close();
        return myReservations;
    }

    public ArrayList<City> getAllCities() {
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("Select * from cities", null);

        int count = cursor.getCount();

        ArrayList<City> citiess =  new ArrayList<>();;
        //String []cities = new String[count];

        int i=0;
        while (cursor.moveToNext()){
            citiess.add(new City(cursor.getString(0), cursor.getInt(1)));
            //cities[i] = cursor.getString(1);
            i++;
        }

        cursor.close();
        mydb.close();
        return citiess;
    }

    public int[] getCityPhotos() {
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("Select * from CITIES", null);

        int count = cursor.getCount();

        int[] images = new int[count];

        int i=0;
        while (cursor.moveToNext()){
            images[i] = cursor.getInt(1);
            i++;
        }

        cursor.close();
        mydb.close();
        return images;
    }

    public String[] getParkingNamesInCity(String cityName) {
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("Select * from parkings where city =?", new String [] {cityName});

        int count = cursor.getCount();

        String [] parking_lot_names = new String[count];

        int i=0;
        while (cursor.moveToNext()){
            parking_lot_names[i] = cursor.getString(0);
            i++;
        }

        cursor.close();
        mydb.close();
        return parking_lot_names;
    }

    public int[] getCapacity(String cityName) {
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("Select * from parkings where city =?", new String [] {cityName});

        int count = cursor.getCount();

        int[] num_parking_spots = new int[count];

        int i=0;
        while (cursor.moveToNext()){
            num_parking_spots[i] = cursor.getInt(1);
            i++;
        }

        cursor.close();
        mydb.close();
        return num_parking_spots;
    }

    public int[] getReservations(String date, String timeframe, String[] parkings) {
        SQLiteDatabase mydb = this.getWritableDatabase();

        int[] takenSpots = new int[parkings.length];

        int i=0;

        String parking_name;

        for (i=0; i<parkings.length; i++){

            parking_name = parkings[i];

            Cursor cursor = mydb.rawQuery("Select * from reservations where parking=? and date=? and timeframe=? ", new String [] {parking_name, date, timeframe});

            takenSpots[i] = cursor.getCount();

            cursor.close();
        }

        mydb.close();
        return takenSpots;
    }

    public int getNumReservations(String parkingName, String date, String timeframe) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from reservations where parking=? and date=? and timeframe=? ", new String [] {parkingName, date, timeframe});
        int taken = cursor.getCount();

        cursor.close();
        db.close();
        return taken;
    }



    public boolean addReservation(String username, String parking, String date, String timeframe) {

        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("user", username);
        contentValues.put("parking", parking);
        contentValues.put("date", date);
        contentValues.put("timeframe", timeframe);

        long insert = MyDB.insert("reservations", null, contentValues);
        if (insert == -1)
            return false;
        else
            return true;
    }

    public String[] getLatitude(String parking) {
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("Select * from parkings where name=?", new String [] {parking});

        int count = cursor.getCount();

        String [] latitude = new String[count];

        int i=0;
        while (cursor.moveToNext()){
            latitude[i] = cursor.getString(3);
            i++;
        }

        cursor.close();
        mydb.close();
        return latitude;
    }

    public String[] getLongitude(String parking) {
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("Select * from parkings where name =?", new String [] {parking});

        int count = cursor.getCount();

        String [] longitude = new String[count];

        int i=0;
        while (cursor.moveToNext()){
            longitude[i] = cursor.getString(4);
            i++;
        }

        cursor.close();
        mydb.close();
        return longitude;
    }
}
