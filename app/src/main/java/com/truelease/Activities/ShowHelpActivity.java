package com.truelease.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.truelease.ApiData.APIClient;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.MyDialog.DialogInterface;
import com.truelease.R;
import com.truelease.RetrofitModel.ShowHelpModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowHelpActivity extends AppCompatActivity {


    TextView textHelp;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_help);

        textHelp = findViewById(R.id.textHelp);
        back = findViewById(R.id.back);


        back.setOnClickListener(v -> finish());

        showHelp();
    }


    public void showHelp() {
        DialogInterface dialog = new CustomDialog();
        dialog.showDialog(R.layout.pr_dialog, ShowHelpActivity.this);


        Call<ShowHelpModel> call = APIClient.getAPIClient().showHelp();
        call.enqueue(new Callback<ShowHelpModel>() {
            @Override
            public void onResponse(@NonNull Call<ShowHelpModel> call, @NonNull Response<ShowHelpModel> response) {
                if (!response.isSuccessful()) {
                    dialog.hideDialog();
                }
                ShowHelpModel model = response.body();

                if (model != null) {
                    dialog.hideDialog();
                    ShowHelpModel.Data data = model.getData();

                    if (data != null) {

                        textHelp.setText(Html.fromHtml(data.getHelp()).toString());
                    } else {
                        Toast.makeText(ShowHelpActivity.this, model.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dialog.hideDialog();
                }

            }

            @Override
            public void onFailure(@NonNull Call<ShowHelpModel> call, @NonNull Throwable t) {
                dialog.hideDialog();
            }
        });
    }
}