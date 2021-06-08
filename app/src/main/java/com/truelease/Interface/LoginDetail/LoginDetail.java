package com.truelease.Interface.LoginDetail;

public class LoginDetail implements UserLoginDetail {

    String userID;

    public LoginDetail(String userID) {
        this.userID = userID;
    }

    @Override
    public boolean isUserLogin() {
        return !userID.equals("");
    }
}
