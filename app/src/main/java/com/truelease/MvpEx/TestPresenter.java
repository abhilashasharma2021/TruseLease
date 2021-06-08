package com.truelease.MvpEx;

import com.truelease.ApiData.APIClient;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TestPresenter<V extends TestI> extends BasePresenter<V> implements TestIPresenter<V> {


    @Override
    public void getLogin(Map<String,String> param) {

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(APIClient
                .getAPIClient()
                .slogin(param)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getMvpView()::onSuccess,getMvpView()::onError));


    }
}
