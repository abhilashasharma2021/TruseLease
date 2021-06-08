package com.truelease.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShowWalletAmountModel {

    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Boolean getResult() {
        return result;
    }


    public String getMessage() {
        return message;
    }


    public List<Datum> getData() {
        return data;
    }


    public static class Datum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("userID")
        @Expose
        private String userID;
        @SerializedName("sellerID")
        @Expose
        private String sellerID;
        @SerializedName("productID")
        @Expose
        private String productID;
        @SerializedName("payment_time")
        @Expose
        private String paymentTime;
        @SerializedName("booking_from")
        @Expose
        private String bookingFrom;
        @SerializedName("booking_upto")
        @Expose
        private String bookingUpto;
        @SerializedName("product_days")
        @Expose
        private String productDays;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("commition")
        @Expose
        private String commition;
        @SerializedName("delevery_charges")
        @Expose
        private String deleveryCharges;
        @SerializedName("total_price")
        @Expose
        private String totalPrice;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("full_address")
        @Expose
        private String fullAddress;
        @SerializedName("lat")
        @Expose
        private String lat;
        @SerializedName("lng")
        @Expose
        private String lng;
        @SerializedName("cityID")
        @Expose
        private String cityID;
        @SerializedName("rent_status")
        @Expose
        private String rentStatus;
        @SerializedName("withdraw_status")
        @Expose
        private String withdrawStatus;
        @SerializedName("withdraw_amount")
        @Expose
        private String withdrawAmount;
        @SerializedName("path")
        @Expose
        private String path;

        public String getId() {
            return id;
        }


        public String getUserID() {
            return userID;
        }


        public String getSellerID() {
            return sellerID;
        }


        public String getProductID() {
            return productID;
        }


        public String getPaymentTime() {
            return paymentTime;
        }


        public String getBookingFrom() {
            return bookingFrom;
        }


        public String getBookingUpto() {
            return bookingUpto;
        }

        public void setBookingUpto(String bookingUpto) {
            this.bookingUpto = bookingUpto;
        }

        public String getProductDays() {
            return productDays;
        }

        public void setProductDays(String productDays) {
            this.productDays = productDays;
        }

        public String getPrice() {
            return price;
        }

        public String getCommition() {
            return commition;
        }

        public String getDeleveryCharges() {
            return deleveryCharges;
        }


        public String getTotalPrice() {
            return totalPrice;
        }


        public String getName() {
            return name;
        }


        public String getEmail() {
            return email;
        }


        public String getMobile() {
            return mobile;
        }


        public String getFullAddress() {
            return fullAddress;
        }


        public String getLat() {
            return lat;
        }


        public String getLng() {
            return lng;
        }


        public String getCityID() {
            return cityID;
        }


        public String getRentStatus() {
            return rentStatus;
        }


        public String getWithdrawStatus() {
            return withdrawStatus;
        }


        public String getWithdrawAmount() {
            return withdrawAmount;
        }

        public String getPath() {
            return path;
        }


    }

}
