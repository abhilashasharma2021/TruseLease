package com.truelease.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCartModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }



    public class Data {

        @SerializedName("cartID")
        @Expose
        private String cartID;
        @SerializedName("userID")
        @Expose
        private String userID;
        @SerializedName("productID")
        @Expose
        private String productID;
        @SerializedName("quwantity")
        @Expose
        private String quwantity;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("orderID")
        @Expose
        private String orderID;
        @SerializedName("placing_time")
        @Expose
        private String placingTime;
        @SerializedName("strtotime")
        @Expose
        private String strtotime;

        public String getCartID() {
            return cartID;
        }

        public void setCartID(String cartID) {
            this.cartID = cartID;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getProductID() {
            return productID;
        }

        public void setProductID(String productID) {
            this.productID = productID;
        }

        public String getQuwantity() {
            return quwantity;
        }

        public void setQuwantity(String quwantity) {
            this.quwantity = quwantity;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOrderID() {
            return orderID;
        }

        public void setOrderID(String orderID) {
            this.orderID = orderID;
        }

        public String getPlacingTime() {
            return placingTime;
        }

        public void setPlacingTime(String placingTime) {
            this.placingTime = placingTime;
        }

        public String getStrtotime() {
            return strtotime;
        }

        public void setStrtotime(String strtotime) {
            this.strtotime = strtotime;
        }

    }

}
