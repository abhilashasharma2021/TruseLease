package com.truelease.Room.SaveDetails;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Details.class}, version = 3)
public abstract class DetailsDatabase extends RoomDatabase {

    private static DetailsDatabase instance;

    public abstract DetailDao detailDao();


    public static synchronized DetailsDatabase getInstance(Context context) {

        if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext(), DetailsDatabase.class, "detail_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

}
