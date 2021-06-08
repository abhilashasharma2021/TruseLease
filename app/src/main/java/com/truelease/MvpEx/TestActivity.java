package com.truelease.MvpEx;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.truelease.R;
import com.truelease.RetrofitModel.LoginData;

import java.util.HashMap;
import java.util.Map;

public class TestActivity extends BaseActivity
        implements TestI  {


    TestPresenter presenter = new TestPresenter();


    @Override
    public void onSuccess(LoginData data) {


        if (data!=null){

            if (data.getResult()){
                Log.e("xclksxcs",data.getMessage()+"vc");
            }
        }
    }


    @Override
    public Activity activity() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initView() {
        presenter.attachView(this);
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> param = new HashMap<>();
                param.put("mobile", "9999999999");
                param.put("regID", "xszxsxkjxnszx");
                param.put("password", "12345");

                presenter.getLogin(param);
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onSuccessLogout(Object object) {

    }

    @Override
    public void onError(Throwable throwable) {

    }


}