package com.truelease.Room.RoomDatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Address.class}, version = 1)
public abstract class AddressDatabase extends RoomDatabase {

    private static AddressDatabase instance;

    public abstract AddressDao addressDao();


    public static synchronized AddressDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AddressDatabase.class, "address_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }


    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAysncTask(instance).execute();
        }
    };

    private static class PopulateDBAysncTask extends AsyncTask<Void, Void, Void> {

        private AddressDao addressDao;

        public PopulateDBAysncTask(AddressDatabase addressDatabase) {
            this.addressDao = addressDatabase.addressDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            //  addressDao.insertAddress(new Address("Bhopal", "Kotra Sultanabad"));
            return null;
        }
    }


}
