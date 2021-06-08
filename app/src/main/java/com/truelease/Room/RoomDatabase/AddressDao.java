package com.truelease.Room.RoomDatabase;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AddressDao {

    @Insert()
    void insertAddress(Address address);

    @Update()
    void updateAddress(Address address);

    @Delete()
    void deleteAddress(Address address);

    @Query("DELETE FROM address_table")
    void deleteAllAddress();

    @Query("SELECT * FROM address_table ORDER BY id DESC")
    LiveData<List<Address>> getAllAddress();

}
