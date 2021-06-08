package com.truelease.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendMessageModel {

    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("message")
    @Expose
    private String message;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public class Result {

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

    }

}