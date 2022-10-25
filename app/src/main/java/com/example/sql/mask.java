package com.example.sql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class mask implements Parcelable {

    private int ID;
    private String Name;
    private String Power;
    private String Image;

    protected mask(Parcel in) {
        ID = in.readInt();
        Name = in.readString();
        Power = in.readString();
    }

    public static final Creator<mask> CREATOR = new Creator<mask>() {
        @Override
        public mask createFromParcel(Parcel in) {
            return new mask(in);
        }

        @Override
        public mask[] newArray(int size) {
            return new mask[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(Name);
        dest.writeString(Power);
        dest.writeString(Image);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPower() {
        return Power;
    }

    public void setPower(String power) {
        Power = power;
    }

    public mask(int ID, String name, String power, String image)
    {
        this.ID = ID;
        Name = name;
        Power = power;
        Image = image;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}