package com.truelease.Room.RoomWishList;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Wishlist.class}, version = 3)
public abstract class WishListDatabase extends RoomDatabase {

    private static WishListDatabase instance;

    public abstract WishListDao wishListDao();


    public static synchronized WishListDatabase getInstance(Context context) {

        if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext(), WishListDatabase.class, "wishlist_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
