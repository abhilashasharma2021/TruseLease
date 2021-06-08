package com.truelease.Activities;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.truelease.ApiData.APIClient;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.R;
import com.truelease.RetrofitModel.TermAndConditionModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermAndConditionActivity extends AppCompatActivity {


    TextView textTc;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_and_condition);

        textTc = findViewById(R.id.textTc);
        back = findViewById(R.id.back);

        back.setOnClickListener(view -> finish());

        showTermsAndCondition();
    }


    public void showTermsAndCondition() {

        Call<TermAndConditionModel> call = APIClient.getAPIClient().showTermAndCondition();
        call.enqueue(new Callback<TermAndConditionModel>() {
            @Override
            public void onResponse(@NonNull Call<TermAndConditionModel> call, @NonNull Response<TermAndConditionModel> response) {
                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(TermAndConditionActivity.this);
                }

                TermAndConditionModel conditionModel = response.body();
                if (conditionModel != null) {


                    if (conditionModel.getResult()) {

                        Log.e("KJkjskjs",conditionModel.getResult() + "");
                        TermAndConditionModel.Data data = conditionModel.getData();

                        Log.e("KJkjskjs", data.getTc());
                        textTc.setText(Html.fromHtml(data.getTc()));
                    } else {
                        Toast.makeText(TermAndConditionActivity.this, conditionModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TermAndConditionModel> call, @NonNull Throwable t) {
                Toast.makeText(TermAndConditionActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                Log.e("KJkjskjs", t.getMessage());
            }
        });
    }
}