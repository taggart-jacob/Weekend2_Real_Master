package com.example.weekend2_real_master;

import android.os.Parcel;
import android.os.Parcelable;

public class Celebrity implements Parcelable {
    //declaring the celebrity object's parameters
    private String name;
    private String age;
    private String profession;

    //receives the parcel in a protected class
    protected Celebrity(Parcel in) {
        name = in.readString();
        age = in.readString();
        profession = in.readString();
    }

    //constructor for Celebrity object
    public Celebrity(String name, String age, String profession) {
        this.name = name;
        this.age = age;
        this.profession = profession;
    }

    //implementation of the Creator method which parcels the celebrity parameters into an array so each can
    //be assigned to individual variables upon un-packaging the parcel
    public static final Parcelable.Creator<Celebrity> CREATOR = new Parcelable.Creator<Celebrity>() {
        @Override
        public Celebrity createFromParcel(Parcel in) {
            return new Celebrity(in);
        }

        @Override
        public Celebrity[] newArray(int size) {
            return new Celebrity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    //implementation of writeToParcel for when an object is passed across classes
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(age);
        parcel.writeString(profession);
    }

    //getters for the retrieval of object parameters
    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getProfession() {
        return profession;
    }

    //setters for the attributes for when object's are modified
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}
