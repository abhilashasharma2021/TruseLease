package com.truelease.MvpEx;

import java.util.Map;

import retrofit2.http.FieldMap;

public interface TestIPresenter<V extends TestI> extends MvpPresenter<V>  {

    void getLogin(@FieldMap Map<String,String> param);
}
