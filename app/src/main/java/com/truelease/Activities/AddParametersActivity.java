package com.truelease.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.truelease.ApiData.APIClient;
import com.truelease.Others.AppConstats;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.Others.SharedHelper;
import com.truelease.R;
import com.truelease.RetrofitModel.BrandModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddParametersActivity extends AppCompatActivity {

    Button btn;
    ImageView back;
    EditText etPower, etWeight, etColor, etHeight, etWidth, etLength;
    String strBrand = "", strPower = "", strWeight = "", strColor = "", strHeight = "", strWidth = "", strLength = "";
    Spinner spBrand;
    ArrayList<String> brandID, brandName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parameters);

        btn = findViewById(R.id.btn);
        spBrand = findViewById(R.id.spBrand);
        etPower = findViewById(R.id.etPower);
        etWeight = findViewById(R.id.etWeight);
        etColor = findViewById(R.id.etColor);
        etHeight = findViewById(R.id.etHeight);
        etWidth = findViewById(R.id.etWidth);
        etLength = findViewById(R.id.etLength);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strPower = etPower.getText().toString().trim();
                strWeight = etWeight.getText().toString().trim();
                strColor = etColor.getText().toString().trim();
                strHeight = etHeight.getText().toString().trim();
                strWidth = etWidth.getText().toString().trim();
                strLength = etLength.getText().toString().trim();


                SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_POWER, strPower);
                SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_WEIGHT, strWeight);
                SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_COLOR, strColor);
                SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_HEIGHT, strHeight);
                SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_WIDTH, strWidth);
                SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_LENGTH, strLength);
                SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_LOCATION, "");
                startActivity(new Intent(getApplicationContext(), AddProductStepFourthActivity.class));


            }
        });


        spBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strBrand= brandID.get(i);
                SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_BRAND_ID, strBrand);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        showBrands();

    }


    public void showBrands() {

        Call<BrandModel> call = APIClient.getAPIClient().showBrands();
        call.enqueue(new Callback<BrandModel>() {
            @Override
            public void onResponse(@NonNull Call<BrandModel> call, @NonNull Response<BrandModel> response) {

                if (!response.isSuccessful()) {

                    ReturnErrorToast.showToast(AddParametersActivity.this);
                }


                BrandModel brandModel = response.body();
                if (brandModel != null) {

                    if (brandModel.getResult()) {

                        brandID = new ArrayList<>();
                        brandName = new ArrayList<>();

                        List<BrandModel.Datum> dataList = brandModel.getData();
                        for (BrandModel.Datum data : dataList) {

                            brandID.add(data.getBrandID());
                            brandName.add(data.getName());

                        }

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddParametersActivity.this, android.R.layout.simple_list_item_1, brandName);
                        spBrand.setAdapter(arrayAdapter);

                    } else {
                        Toast.makeText(AddParametersActivity.this, brandModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BrandModel> call, @NonNull Throwable t) {

            }
        });
    }


}