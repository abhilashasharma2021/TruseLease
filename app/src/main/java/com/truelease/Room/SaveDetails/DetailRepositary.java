package com.truelease.Room.SaveDetails;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DetailRepositary {

    LiveData<List<Details>> getAllDetails;
    LiveData<List<Details>> getAllDetailsById;
    LiveData<List<Details>> getAllListByUser;
    private DetailDao detailDao;


    public DetailRepositary(Application application) {
        DetailsDatabase detailsDatabase = DetailsDatabase.getInstance(application);
        detailDao = detailsDatabase.detailDao();
        getAllDetails = detailDao.getAllList();
    }


    public void insert(Details details) {

        new InsertAsyncTask(detailDao).execute(details);

    }


    public void update(Details details) {

        new UpdateTask(detailDao).execute(details);

    }


    public void delete(Details details) {

        new DeleteTask(detailDao).execute(details);

    }


    public LiveData<List<Details>> getAllDetails() {
        return this.getAllDetails;
    }

    public LiveData<List<Details>> getAllListByUser(String userID) {
        return this.getAllListByUser=detailDao.getAllListByUser(userID);
    }


    public LiveData<List<Details>> getAllDetailsById(String userId, String name, String mobile, String fullAddress, String pincode) {
        return this.getAllDetailsById=detailDao.getAllDetailsById(userId, name, mobile, fullAddress, pincode);
    }


    private static class InsertAsyncTask extends AsyncTask<Details, Void, Void> {

        private final DetailDao detailDao;

        public InsertAsyncTask(DetailDao detailDao) {
            this.detailDao = detailDao;
        }

        @Override
        protected Void doInBackground(Details... wishlists) {
            detailDao.insertOrUpdate(wishlists[0]);
            return null;
        }
    }


    private static class UpdateTask extends AsyncTask<Details, Void, Void> {

        private final DetailDao detailDao;

        public UpdateTask(DetailDao detailDao) {
            this.detailDao = detailDao;
        }

        @Override
        protected Void doInBackground(Details... wishlists) {
            detailDao.update(wishlists[0]);
            return null;
        }
    }


    private static class DeleteTask extends AsyncTask<Details, Void, Void> {

        private final DetailDao detailDao;

        public DeleteTask(DetailDao detailDao) {
            this.detailDao = detailDao;
        }

        @Override
        protected Void doInBackground(Details... wishlists) {
            detailDao.delete(wishlists[0]);
            return null;
        }
    }
}
