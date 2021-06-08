package com.truelease.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {

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

    public void setResult(Boolean result) {
        this.result = result;
    }

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

    public static class Data {

        @SerializedName("userID")
        @Expose
        private String userID;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("DOB")
        @Expose
        private String dOB;
        @SerializedName("address")
        @Expose
        private String address;

        @SerializedName("brief_intro")
        @Expose
        private String brief_intro;
        @SerializedName("profile_image")
        @Expose
        private String profileImage;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("strtotime")
        @Expose
        private String strtotime;

        @SerializedName("path")
        @Expose
        private String path;


        @SerializedName("verification_documents")
        @Expose
        private String verification_documents;


        @SerializedName("profile_status")
        @Expose
        private String profile_status;


        @SerializedName("D_license_front")
        @Expose
        private String D_license_front;

        @SerializedName("D_license_back")
        @Expose
        private String D_license_back;

        @SerializedName("paspot")
        @Expose
        private String paspot;

        @SerializedName("adhar_card")
        @Expose
        private String adhar_card;

        @SerializedName("refferal_code")
        @Expose
        private String refferal_code;


        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getDOB() {
            return dOB;
        }

        public void setDOB(String dOB) {
            this.dOB = dOB;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStrtotime() {
            return strtotime;
        }

        public void setStrtotime(String strtotime) {
            this.strtotime = strtotime;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getBrief_intro() {
            return brief_intro;
        }


        public String getVerification_documents() {
            return verification_documents;
        }


        public String getProfile_status() {
            return profile_status;
        }


        public String getdOB() {
            return dOB;
        }

        public String getD_license_front() {
            return D_license_front;
        }

        public String getD_license_back() {
            return D_license_back;
        }

        public String getPaspot() {
            return paspot;
        }

        public String getAdhar_card() {
            return adhar_card;
        }

        public String getRefferal_code() {
            return refferal_code;
        }
    }

}