package com.truelease.Room.RoomDatabase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AddressRepository {

    private final AddressDao addressDao;
    private final LiveData<List<Address>> allAddress;

    public AddressRepository(Application application) {
        AddressDatabase database = AddressDatabase.getInstance(application);
        this.addressDao = database.addressDao();
        this.allAddress = addressDao.getAllAddress();
    }


    public void insert(Address address) {

        new InsertAddressAsyncTask(addressDao).execute(address);
    }


    public void update(Address address) {
        new UpdateAddressAsyncTask(addressDao).execute(address);
    }


    public void delete(Address address) {
        new DeleteAddressAsyncTask(addressDao).execute(address);
    }


    public void deleteAll() {
        new DeleteAllAddressAsyncTask(addressDao).execute();
    }


    public LiveData<List<Address>> getAllAddress(){
        return allAddress;
    }


    private static class InsertAddressAsyncTask extends AsyncTask<Address, Void, Void> {

        private AddressDao addressDao;

        public InsertAddressAsyncTask(AddressDao addressDao) {
            this.addressDao = addressDao;
        }

        @Override
        protected Void doInBackground(Address... addresses) {
            addressDao.insertAddress(addresses[0]);
            return null;
        }
    }


    private static class UpdateAddressAsyncTask extends AsyncTask<Address, Void, Void> {

        private AddressDao addressDao;


        public UpdateAddressAsyncTask(AddressDao addressDao) {
            this.addressDao = addressDao;
        }

        @Override
        protected Void doInBackground(Address... addresses) {
            addressDao.updateAddress(addresses[0]);
            return null;
        }
    }


    private static class DeleteAddressAsyncTask extends AsyncTask<Address, Void, Void> {

        private AddressDao addressDao;


        public DeleteAddressAsyncTask(AddressDao addressDao) {
            this.addressDao = addressDao;
        }

        @Override
        protected Void doInBackground(Address... addresses) {
            addressDao.deleteAddress(addresses[0]);
            return null;
        }
    }


    private static class DeleteAllAddressAsyncTask extends AsyncTask<Void, Void, Void> {

        private AddressDao addressDao;

        public DeleteAllAddressAsyncTask(AddressDao addressDao) {
            this.addressDao = addressDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            addressDao.deleteAllAddress();
            return null;
        }
    }
}
