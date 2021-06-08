package com.truelease.MvpEx;

import android.app.Activity;

public interface MvpView {

    Activity activity();

    void showLoading();

    void hideLoading() throws Exception;

    void onSuccessLogout(Object object);

    void onError(Throwable throwable) throws Exception;
}

