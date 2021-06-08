package com.truelease.MvpEx;

import android.app.Activity;

import java.util.HashMap;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private final CompositeDisposable mCompositeDisposable;
    private V mMvpView;

    public BasePresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }



    @Override
    public Activity activity() {
        return getMvpView().activity();
    }

    @Override
    public MvpApplication appContext() {
        return MvpApplication.getInstance();
    }

    @Override
    public void attachView(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mCompositeDisposable.dispose();
        mMvpView = null;
    }

    @Override
    public void refreshToken() {

    }

    @Override
    public void logout(HashMap<String, Object> obj) {

    }


    public V getMvpView() {
        return mMvpView;
    }


    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }


}
