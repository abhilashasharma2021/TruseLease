package com.truelease.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShowAdminChatModel {

    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public class Result {

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
