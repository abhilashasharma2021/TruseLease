package com.truelease.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationModel {

    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;

    public Boolean getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public List<Data> getData() {
        return data;
    }


    public class Data {

        @SerializedName("notificationID")
        @Expose
        private String notificationID;
        @SerializedName("userID")
        @Expose
        private String userID;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("regID")
        @Expose
        private String regID;

        public String getNotificationID() {
            return notificationID;
        }

        public String getUserID() {
            return userID;
        }


        public String getMessage() {
            return message;
        }


        public String getTitle() {
            return title;
        }


        public String getRegID() {
            return regID;
        }


    }

}
