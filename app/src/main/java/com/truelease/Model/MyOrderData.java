package com.truelease.Model;

public class MyOrderData {


    private String productID;
    private String productName;
    private String rent_per_month;
    private String price_type;
    private String image;
    private String path;
    private String rent_status;
    private String pOwnerId;


    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRent_per_month() {
        return rent_per_month;
    }

    public void setRent_per_month(String rent_per_month) {
        this.rent_per_month = rent_per_month;
    }

    public String getPrice_type() {
        return price_type;
    }

    public void setPrice_type(String price_type) {
        this.price_type = price_type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public String getRent_status() {
        return rent_status;
    }

    public void setRent_status(String rent_status) {
        this.rent_status = rent_status;
    }

    public String getpOwnerId() {
        return pOwnerId;
    }

    public void setpOwnerId(String pOwnerId) {
        this.pOwnerId = pOwnerId;
    }
}
