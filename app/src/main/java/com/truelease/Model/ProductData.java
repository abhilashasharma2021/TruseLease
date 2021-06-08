package com.truelease.Model;

import com.truelease.RetrofitModel.ShowProductsModel;

import java.util.List;

public class ProductData {

    private String ProId;
    private String ProName;
    private String ProPrice;
    private String ProImage;
    private String ProPath;
    private String categoryID;
    private String subCategoryID;
    private String rentPerMonth;
    private String currencySymbol;
    private String rentStatus;



    public String getRentStatus() {
        return rentStatus;
    }

    public void setRentStatus(String rentStatus) {
        this.rentStatus = rentStatus;
    }

    public String getProImage() {
        return ProImage;
    }

    public void setProImage(String proImage) {
        ProImage = proImage;
    }

    public String getProPath() {
        return ProPath;
    }

    public void setProPath(String proPath) {
        ProPath = proPath;
    }

    List<ShowProductsModel.Datum.Image> image;

    public List<ShowProductsModel.Datum.Image> getImage() {
        return image;
    }

    public void setImage(List<ShowProductsModel.Datum.Image> image) {
        this.image = image;
    }

    public String getProId() {
        return ProId;
    }

    public void setProId(String proId) {
        ProId = proId;
    }

    public String getProName() {
        return ProName;
    }

    public void setProName(String proName) {
        ProName = proName;
    }

    public String getProPrice() {
        return ProPrice;
    }

    public void setProPrice(String proPrice) {
        ProPrice = proPrice;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getSubCategoryID() {
        return subCategoryID;
    }

    public void setSubCategoryID(String subCategoryID) {
        this.subCategoryID = subCategoryID;
    }


    public String getRentPerMonth() {
        return rentPerMonth;
    }

    public void setRentPerMonth(String rentPerMonth) {
        this.rentPerMonth = rentPerMonth;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }
}
