package com.truelease.Activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.truelease.ApiData.APIClient;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.MyDialog.DialogInterface;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.R;
import com.truelease.RetrofitModel.ForgotPasswordModel;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {


    Button btnSubmit;
    EditText etNumber;
    String strNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        btnSubmit = findViewById(R.id.btnSubmit);
        etNumber = findViewById(R.id.etNumber);

        ImageView back = findViewById(R.id.back);

        btnSubmit.setOnClickListener(v -> {
            strNumber = etNumber.getText().toString().trim();
            if (strNumber.equals("")) {
                ReturnErrorToast.showWarningToast(ForgotPasswordActivity.this, "Enter your mobile number");
            } else if (strNumber.length() < 10) {
                ReturnErrorToast.showWarningToast(ForgotPasswordActivity.this, "Invalid number");

            } else {

                forgotPass(strNumber);
            }
        });


        back.setOnClickListener(v -> finish());
    }


    public void forgotPass(String mobileNumber) {


        DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, ForgotPasswordActivity.this);

        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobileNumber);
        Call<ForgotPasswordModel> call = APIClient.getAPIClient().forgotPassword(params);
        call.enqueue(new Callback<ForgotPasswordModel>() {
            @Override
            public void onResponse(@NonNull Call<ForgotPasswordModel> call, @NonNull Response<ForgotPasswordModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(ForgotPasswordActivity.this);
                    dialogInterface.hideDialog();
                }

                ForgotPasswordModel model = response.body();
                if (model != null) {

                    if (model.getResult()) {
                        dialogInterface.hideDialog();
                        etNumber.setText("");

                        dialog().show();


                    } else {
                        dialogInterface.hideDialog();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ForgotPasswordModel> call, @NonNull Throwable t) {
                dialogInterface.hideDialog();
                Log.e("uyuwewd", t.getMessage() + "err");
            }
        });
    }


    public Dialog dialog() {
        Dialog dialog = new Dialog(ForgotPasswordActivity.this);
        dialog.setContentView(R.layout.success_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return  dialog;
    }
}