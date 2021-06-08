package com.truelease.Room.RoomWishList;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WishListDao {

    @Insert
    void insert(Wishlist wishlist);


    @Update
    void update(Wishlist wishlist);

    @Delete
    void delete(Wishlist wishlist);

    @Query("SELECT * FROM table_wishlist WHERE productID =:id")
    List<Wishlist> getProduct(String id);

    @Query("SELECT * FROM table_wishlist")
    LiveData<List<Wishlist>> getAllWishList();


    @Query("SELECT * FROM table_wishlist WHERE userID=:userID")
    LiveData<List<Wishlist>> getWishlistByUSerId(String userID);


    @Query("UPDATE table_wishlist SET status = :nstatus WHERE productID =:id")
    void updateStatus(String id, String nstatus);


    default void insertOrupdate(Wishlist wishlist) {
        List<Wishlist> itemsFromDB = getProduct(wishlist.getProductID());
        if (itemsFromDB.isEmpty())
            insert(wishlist);
        else
            update(wishlist);
    }

    @Query("SELECT * FROM table_wishlist WHERE productID =:id AND userID=:userid")
    LiveData<List<Wishlist>> getProductbyID(String id,String userid);

}


