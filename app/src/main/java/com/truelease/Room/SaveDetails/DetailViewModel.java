package com.truelease.Room.SaveDetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DetailViewModel extends AndroidViewModel {


    DetailRepositary detailRepositary;
    LiveData<List<Details>> getAllList;
    LiveData<List<Details>> getAllDetailsById;
    LiveData<List<Details>> getAllListByUser;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        detailRepositary = new DetailRepositary(application);
        this.getAllList = detailRepositary.getAllDetails();
    }


    public void insert(Details details) {
        detailRepositary.insert(details);
    }



    public void update(Details details) {
        detailRepositary.update(details);
    }

    public void delete(Details details) {
        detailRepositary.delete(details);
    }

    public LiveData<List<Details>> getAllDetailList() {
        return this.getAllList;
    }

    public LiveData<List<Details>> getAllListByUser(String userID) {
        return this.getAllListByUser=detailRepositary.getAllListByUser(userID);
    }


    public LiveData<List<Details>> getAllDetailsById(String userId, String name, String mobile, String fullAddress, String pincode) {
        return this.getAllDetailsById=detailRepositary.getAllDetailsById(userId, name, mobile, fullAddress, pincode);
    }
}
