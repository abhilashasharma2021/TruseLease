package com.truelease.Model;

public class CartData {

    private String cartID;
    private String productId;
    private String productName;
    private String price;
    private String deliverIn;
    private String description;
    private String image;
    private String path;
    private String quantitiy;
    private String prOwnerID;
    private String total_selected_days;
    private String total;
    private String priceType;
    private String currencySymbol;
    private String deliveryType;
    private String orderID;
    private String pOwnerID;


    public String getCartID() {
        return cartID;
    }

    public String getpOwnerID() {
        return pOwnerID;
    }

    public void setpOwnerID(String pOwnerID) {
        this.pOwnerID = pOwnerID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public String getPrOwnerID() {
        return prOwnerID;
    }

    public void setPrOwnerID(String prOwnerID) {
        this.prOwnerID = prOwnerID;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDeliverIn() {
        return deliverIn;
    }

    public void setDeliverIn(String deliverIn) {
        this.deliverIn = deliverIn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getQuantitiy() {
        return quantitiy;
    }

    public void setQuantitiy(String quantitiy) {
        this.quantitiy = quantitiy;
    }

    public String getTotal_selected_days() {
        return total_selected_days;
    }

    public void setTotal_selected_days(String total_selected_days) {
        this.total_selected_days = total_selected_days;
    }


    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }


    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
}
