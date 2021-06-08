package com.truelease.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyChatModel {

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

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("chatID")
        @Expose
        private String chatID;
        @SerializedName("text")
        @Expose
        private String text;
        @SerializedName("sanderID")
        @Expose
        private String sanderID;
        @SerializedName("reciverID")
        @Expose
        private String reciverID;
        @SerializedName("message_time")
        @Expose
        private String messageTime;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("app_status")
        @Expose
        private String appStatus;
        @SerializedName("last_maz")
        @Expose
        private LastMaz lastMaz;
        @SerializedName("msz_count")
        @Expose
        private Integer mszCount;
        @SerializedName("sender_detail")
        @Expose
        private SenderDetail senderDetail;

        public String getChatID() {
            return chatID;
        }

        public void setChatID(String chatID) {
            this.chatID = chatID;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getSanderID() {
            return sanderID;
        }

        public void setSanderID(String sanderID) {
            this.sanderID = sanderID;
        }

        public String getReciverID() {
            return reciverID;
        }

        public void setReciverID(String reciverID) {
            this.reciverID = reciverID;
        }

        public String getMessageTime() {
            return messageTime;
        }

        public void setMessageTime(String messageTime) {
            this.messageTime = messageTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAppStatus() {
            return appStatus;
        }


        public LastMaz getLastMaz() {
            return lastMaz;
        }



        public Integer getMszCount() {
            return mszCount;
        }

        public void setMszCount(Integer mszCount) {
            this.mszCount = mszCount;
        }

        public SenderDetail getSenderDetail() {
            return senderDetail;
        }


        public class LastMaz {

            @SerializedName("chatID")
            @Expose
            private String chatID;
            @SerializedName("text")
            @Expose
            private String text;
            @SerializedName("sanderID")
            @Expose
            private String sanderID;
            @SerializedName("reciverID")
            @Expose
            private String reciverID;
            @SerializedName("message_time")
            @Expose
            private String messageTime;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("app_status")
            @Expose
            private String appStatus;

            public String getChatID() {
                return chatID;
            }

            public void setChatID(String chatID) {
                this.chatID = chatID;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getSanderID() {
                return sanderID;
            }

            public void setSanderID(String sanderID) {
                this.sanderID = sanderID;
            }

            public String getReciverID() {
                return reciverID;
            }

            public void setReciverID(String reciverID) {
                this.reciverID = reciverID;
            }

            public String getMessageTime() {
                return messageTime;
            }

            public void setMessageTime(String messageTime) {
                this.messageTime = messageTime;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getAppStatus() {
                return appStatus;
            }

            public void setAppStatus(String appStatus) {
                this.appStatus = appStatus;
            }

        }


        public class SenderDetail {

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
            @SerializedName("password")
            @Expose
            private String password;
            @SerializedName("DOB")
            @Expose
            private String dOB;
            @SerializedName("address")
            @Expose
            private String address;
            @SerializedName("brief_intro")
            @Expose
            private String briefIntro;
            @SerializedName("profile_image")
            @Expose
            private String profileImage;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("strtotime")
            @Expose
            private String strtotime;
            @SerializedName("refferal_code")
            @Expose
            private String refferalCode;
            @SerializedName("reffers_code")
            @Expose
            private String reffersCode;
            @SerializedName("regID")
            @Expose
            private String regID;
            @SerializedName("authID")
            @Expose
            private String authID;
            @SerializedName("auth_provider")
            @Expose
            private String authProvider;
            @SerializedName("verification_documents")
            @Expose
            private String verificationDocuments;

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

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
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

            public String getBriefIntro() {
                return briefIntro;
            }

            public void setBriefIntro(String briefIntro) {
                this.briefIntro = briefIntro;
            }

            public String getProfileImage() {
                return profileImage;
            }

            public void setProfileImage(String profileImage) {
                this.profileImage = profileImage;
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

            public String getRefferalCode() {
                return refferalCode;
            }

            public void setRefferalCode(String refferalCode) {
                this.refferalCode = refferalCode;
            }

            public String getReffersCode() {
                return reffersCode;
            }

            public void setReffersCode(String reffersCode) {
                this.reffersCode = reffersCode;
            }

            public String getRegID() {
                return regID;
            }

            public void setRegID(String regID) {
                this.regID = regID;
            }

            public String getAuthID() {
                return authID;
            }

            public void setAuthID(String authID) {
                this.authID = authID;
            }

            public String getAuthProvider() {
                return authProvider;
            }

            public void setAuthProvider(String authProvider) {
                this.authProvider = authProvider;
            }

            public String getVerificationDocuments() {
                return verificationDocuments;
            }

            public void setVerificationDocuments(String verificationDocuments) {
                this.verificationDocuments = verificationDocuments;
            }

        }





    }

}