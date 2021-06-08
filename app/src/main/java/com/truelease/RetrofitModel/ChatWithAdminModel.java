package com.truelease.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatWithAdminModel {

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


    public class Data {

        @SerializedName("supportID")
        @Expose
        private String supportID;
        @SerializedName("sanderID")
        @Expose
        private String sanderID;
        @SerializedName("reciverID")
        @Expose
        private String reciverID;
        @SerializedName("text")
        @Expose
        private String text;
        @SerializedName("message_time")
        @Expose
        private String messageTime;

        public String getSupportID() {
            return supportID;
        }

        public void setSupportID(String supportID) {
            this.supportID = supportID;
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

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getMessageTime() {
            return messageTime;
        }

        public void setMessageTime(String messageTime) {
            this.messageTime = messageTime;
        }

    }

}