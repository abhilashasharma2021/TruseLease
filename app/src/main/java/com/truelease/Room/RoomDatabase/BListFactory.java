package com.truelease.Room.RoomDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BListFactory extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    private final Application application;




    public BListFactory(@NonNull Application application) {
        this.application = application;

    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == AddressViewModel.class) {
            return (T) new AddressViewModel(application);
        }
        return null;
    }
}
