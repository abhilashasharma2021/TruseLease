package com.truelease.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShowNotiStatusModel {

    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("app_status")
    @Expose
    private String appStatus;

    public Boolean getResult() {
        return result;
    }


    public String getMessage() {
        return message;
    }



    public String getAppStatus() {
        return appStatus;
    }



}