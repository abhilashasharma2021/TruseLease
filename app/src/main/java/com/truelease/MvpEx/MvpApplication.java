package com.truelease.MvpEx;

import android.app.Application;
import android.content.Context;



public class MvpApplication extends Application {


    private static MvpApplication mInstance;

    public static synchronized MvpApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected void attachBaseContext(Context newBase) {

    }

}
