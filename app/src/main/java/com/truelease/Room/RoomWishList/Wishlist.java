package com.truelease.Room.RoomWishList;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_wishlist")
public class Wishlist {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String productID;

    private String userID;

    private String productName;

    private String productAmount;

    private String image;

    private String status;

    public Wishlist(String productID, String userID, String productName, String productAmount, String image, String status) {
        this.productID = productID;
        this.userID = userID;
        this.productName = productName;
        this.productAmount = productAmount;
        this.image = image;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }


    public String getUserID() {
        return userID;
    }
}
