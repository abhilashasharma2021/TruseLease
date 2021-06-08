package com.truelease.Room.RoomDatabase;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "address_table")
public class Address {


    @PrimaryKey(autoGenerate = true)
    private int id;

    private String city;

    private String fullAddress;


    public Address( String city, String fullAddress) {
        this.city = city;
        this.fullAddress = fullAddress;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }


}
