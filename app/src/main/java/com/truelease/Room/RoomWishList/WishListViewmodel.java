package com.truelease.Room.RoomWishList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WishListViewmodel extends AndroidViewModel {


    private final WishListRepositary wishListRepositary;
    private final LiveData<List<Wishlist>> wishList;
    private LiveData<List<Wishlist>> getProductbyID;
    private LiveData<List<Wishlist>> getWishlistByUserId;


    public WishListViewmodel(@NonNull Application application) {
        super(application);

        wishListRepositary = new WishListRepositary(application);
        this.wishList = wishListRepositary.getAllWishList();

    }


    public void insert(Wishlist wishlist) {
        wishListRepositary.insert(wishlist);
    }


    public void delete(Wishlist wishlist) {
        wishListRepositary.delete(wishlist);
    }

    public void updateStatus(Wishlist wishlist) {
        wishListRepositary.updateStatus(wishlist);
    }


    public LiveData<List<Wishlist>> getProductByID(String id, String userID) {
        return this.getProductbyID = wishListRepositary.getProductByID(id, userID);
    }


    public LiveData<List<Wishlist>> getWishlistByUserId(String id) {
        return this.getWishlistByUserId = wishListRepositary.getWishlistByUserId(id);
    }


    public LiveData<List<Wishlist>> getAllWishList() {
        return wishList;
    }


}
