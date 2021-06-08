package com.truelease.Room.SaveDetails;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "details_table")
public class Details {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String email;
    private String number;
    private String fulladdress;
    private String pincode;
    private String cityId;
    private String state;
    private String userId;


    public Details(String name, String email, String number, String fulladdress, String pincode, String cityId,String state,String userId) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.fulladdress = fulladdress;
        this.pincode = pincode;
        this.cityId = cityId;
        this.state = state;
        this.userId = userId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

    public String getFulladdress() {
        return fulladdress;
    }

    public String getPincode() {
        return pincode;
    }

    public String getCityId() {
        return cityId;
    }

    public String getState() {
        return state;
    }


    public String getUserId() {
        return userId;
    }
}
