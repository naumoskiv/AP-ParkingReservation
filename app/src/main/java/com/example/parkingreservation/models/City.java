package com.example.parkingreservation.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class City implements Parcelable {
    private String mName;
    private int mImage;

    public City(String name, int image) {
        mName = name;
        mImage = image;
    }


    protected City(Parcel in) {
        mName = in.readString();
        mImage = in.readInt();
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    public String getName () {
        return mName;
    }

    public int getImage() {
        return mImage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeInt(mImage);
    }
}

