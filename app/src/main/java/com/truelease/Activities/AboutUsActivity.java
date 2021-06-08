package com.truelease.Activities;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.truelease.ApiData.APIClient;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.MyDialog.DialogInterface;
import com.truelease.R;
import com.truelease.RetrofitModel.AboutUsModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUsActivity extends AppCompatActivity {
    TextView textAboutUs;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        textAboutUs = findViewById(R.id.textAboutUs);

        back = findViewById(R.id.back);


        back.setOnClickListener(v -> finish());

        showAboutUs();
    }



    public void showAboutUs() {
        DialogInterface dialog = new CustomDialog();
        dialog.showDialog(R.layout.pr_dialog, AboutUsActivity.this);


        Call<AboutUsModel> call = APIClient.getAPIClient().aboutUS();
        call.enqueue(new Callback<AboutUsModel>() {
            @Override
            public void onResponse(@NonNull Call<AboutUsModel> call, @NonNull Response<AboutUsModel> response) {
                if (!response.isSuccessful()) {
                    dialog.hideDialog();
                }
                AboutUsModel model = response.body();

                if (model != null) {
                    dialog.hideDialog();
                    AboutUsModel.Data data = model.getData();

                    if (data != null) {

                        textAboutUs.setText(Html.fromHtml(data.getAbout()).toString());
                    } else {
                        Toast.makeText(AboutUsActivity.this, model.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dialog.hideDialog();
                }

            }

            @Override
            public void onFailure(@NonNull Call<AboutUsModel> call, @NonNull Throwable t) {
                dialog.hideDialog();
            }
        });
    }
}