package com.truelease.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewNotificationModel {

    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    public Boolean getResult() {
        return result;
    }


    public String getMessage() {
        return message;
    }


    public Data getData() {
        return data;
    }


    public static class Data {

        @SerializedName("unreadID")
        @Expose
        private String unreadID;
        @SerializedName("userID")
        @Expose
        private String userID;
        @SerializedName("currently_renting")
        @Expose
        private String currentlyRenting;
        @SerializedName("wallet")
        @Expose
        private String wallet;
        @SerializedName("sample_agreement")
        @Expose
        private String sampleAgreement;
        @SerializedName("my_order")
        @Expose
        private String myOrder;

        public String getUnreadID() {
            return unreadID;
        }


        public String getUserID() {
            return userID;
        }


        public String getCurrentlyRenting() {
            return currentlyRenting;
        }


        public String getWallet() {
            return wallet;
        }


        public String getSampleAgreement() {
            return sampleAgreement;
        }


        public String getMyOrder() {
            return myOrder;
        }


    }

}
