package com.truelease.Model;

public class MyChatData {

    private String chatID;
    private String senderID;
    private String recieverID;
    private String message_time;
    private String last_msg;
    private int msz_count;
    private String msgsSenderName;
    private String profile_image;
    private boolean isSelected = false;


    public String getChatID() {
        return chatID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getRecieverID() {
        return recieverID;
    }

    public void setRecieverID(String recieverID) {
        this.recieverID = recieverID;
    }

    public String getMessage_time() {
        return message_time;
    }

    public void setMessage_time(String message_time) {
        this.message_time = message_time;
    }

    public String getLast_msg() {
        return last_msg;
    }

    public void setLast_msg(String last_msg) {
        this.last_msg = last_msg;
    }

    public int getMsz_count() {
        return msz_count;
    }

    public void setMsz_count(int msz_count) {
        this.msz_count = msz_count;
    }


    public String getMsgsSenderName() {
        return msgsSenderName;
    }

    public void setMsgsSenderName(String msgsSenderName) {
        this.msgsSenderName = msgsSenderName;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}