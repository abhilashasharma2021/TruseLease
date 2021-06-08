package com.truelease.Room.SaveDetails;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DetailDao {

    @Insert
    void insert(Details details);

    @Update
    void update(Details details);

    @Delete
    void delete(Details details);

    @Query("SELECT * FROM details_table")
    LiveData<List<Details>> getAllList();

    @Query("SELECT * FROM details_table WHERE userId=:id AND fulladdress=:address AND number=:number AND email=:email")
    List<Details> getProductById(String id, String address, String number, String email);


    @Query("SELECT * FROM details_table WHERE userId=:userId AND name=:name AND number=:mobile AND fulladdress=:fullAddress AND pincode=:pincode")
    LiveData<List<Details>> getAllDetailsById(String userId, String name, String mobile, String fullAddress, String pincode);


    default void insertOrUpdate(Details details) {

        List<Details> listLiveData = getProductById(details.getUserId(), details.getFulladdress(), details.getNumber(), details.getEmail());

        if (listLiveData.isEmpty()) {
            insert(details);
        } else {
            update(details);
        }

    }


    @Query("SELECT * FROM details_table WHERE userId=:userID")
    LiveData<List<Details>> getAllListByUser(String userID);


}
