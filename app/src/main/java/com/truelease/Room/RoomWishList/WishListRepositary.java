package com.truelease.Room.RoomWishList;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WishListRepositary {


    private LiveData<List<Wishlist>> allWishList;
    private LiveData<List<Wishlist>> getProductByid;
    private LiveData<List<Wishlist>> getWishlistByUserId;
    private WishListDao wishListDao;


    public WishListRepositary(Application application) {

        WishListDatabase wishListDatabase = WishListDatabase.getInstance(application);
        wishListDao = wishListDatabase.wishListDao();
        allWishList = wishListDao.getAllWishList();

    }


    public void insert(Wishlist wishlist) {

        new InsertAsyncTask(wishListDao).execute(wishlist);
    }


    public void updateStatus(Wishlist wishlist) {

        new UpdateStatus(wishListDao).execute(wishlist);
    }


    public LiveData<List<Wishlist>> getProductByID(String id, String userID) {

        return this.getProductByid = wishListDao.getProductbyID(id, userID);
    }


    public LiveData<List<Wishlist>> getWishlistByUserId(String id) {

        return this.getWishlistByUserId = wishListDao.getWishlistByUSerId(id);
    }


    public void delete(Wishlist wishlist) {
        new DeleteTask(wishListDao).execute(wishlist);
    }


    public LiveData<List<Wishlist>> getAllWishList() {
        return allWishList;
    }


    private static class InsertAsyncTask extends AsyncTask<Wishlist, Void, Void> {

        private final WishListDao wishListDao;

        public InsertAsyncTask(WishListDao wishListDao) {
            this.wishListDao = wishListDao;
        }

        @Override
        protected Void doInBackground(Wishlist... wishlists) {
            wishListDao.insertOrupdate(wishlists[0]);
            return null;
        }
    }


    public static class DeleteTask extends AsyncTask<Wishlist, Void, Void> {

        WishListDao wishListDao;

        public DeleteTask(WishListDao wishListDao) {
            this.wishListDao = wishListDao;
        }

        @Override
        protected Void doInBackground(Wishlist... wishlists) {

            wishListDao.delete(wishlists[0]);
            return null;
        }
    }


    public static class UpdateStatus extends AsyncTask<Wishlist, Void, Void> {

        WishListDao wishListDao;

        public UpdateStatus(WishListDao wishListDao) {
            this.wishListDao = wishListDao;
        }

        @Override
        protected Void doInBackground(Wishlist... wishlists) {

            wishListDao.updateStatus(wishlists[0].getProductID(), wishlists[0].getStatus());
            return null;
        }
    }


}
