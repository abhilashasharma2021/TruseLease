package com.truelease.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.mlsdev.animatedrv.AnimatedRecyclerView;
import com.truelease.Adapters.SelectCityAdapter;
import com.truelease.ApiData.APIClient;
import com.truelease.Model.SelectCityData;
import com.truelease.R;
import com.truelease.RetrofitModel.CitiesModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectCityActivity extends AppCompatActivity {

    public static AnimatedRecyclerView citiesRecycler;
    List<SelectCityData> selectCityDataList;
    SelectCityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);


        citiesRecycler = findViewById(R.id.citiesRecycler);
        citiesRecycler.setHasFixedSize(true);
        citiesRecycler.setItemViewCacheSize(20);
        citiesRecycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        citiesRecycler.setDrawingCacheEnabled(true);

        citiesRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        citiesRecycler.scheduleLayoutAnimation();

        showCities();
    }

    public void showCities() {
        Call<CitiesModel> call = APIClient.getAPIClient().showCities();
        call.enqueue(new Callback<CitiesModel>() {
            @Override
            public void onResponse(@NonNull Call<CitiesModel> call, @NonNull Response<CitiesModel> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(SelectCityActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                }
                    CitiesModel citiesModel = response.body();
                    if (citiesModel != null) {
                        if (citiesModel.getResult()) {
                            selectCityDataList = new ArrayList<>();
                            List<CitiesModel.Datum> dataList = citiesModel.getData();
                            if (dataList.size() > 0) {
                                for (CitiesModel.Datum data : dataList) {
                                    SelectCityData cityData = new SelectCityData(data.getCityID(), data.getCity(), data.getImage(), data.getPath());
                                    selectCityDataList.add(cityData);
                                }
                                adapter = new SelectCityAdapter(selectCityDataList, SelectCityActivity.this);
                                citiesRecycler.setAdapter(adapter);
                            } else {
                                Toast.makeText(SelectCityActivity.this, "No cities to show", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(SelectCityActivity.this, citiesModel.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }



            }

            @Override
            public void onFailure(@NonNull Call<CitiesModel> call, @NonNull Throwable t) {

            }
        });
    }
}