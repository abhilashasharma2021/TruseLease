package com.truelease.Room.RoomDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AddressViewModel extends AndroidViewModel {

    private final AddressRepository repository;
    private final LiveData<List<Address>> allAddress;

    public AddressViewModel(@NonNull Application application) {
        super(application);
        repository = new AddressRepository(application);
        allAddress = repository.getAllAddress();
    }


    public void insert(Address address){
        repository.insert(address);
    }


    public void update(Address address){
        repository.update(address);
    }


    public void delete(Address address){
        repository.delete(address);
    }


    public void deleteAll(){
        repository.deleteAll();
    }


    public LiveData<List<Address>> getAllAddress(){
        return allAddress;
    }

}
