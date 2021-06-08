package com.truelease.MvpEx;

import com.truelease.RetrofitModel.LoginData;

public interface TestI extends MvpView {

    void onSuccess(LoginData data);
    void onError(Throwable e);

}
