package com.truelease.Activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.truelease.ApiData.APIClient;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.MyDialog.DialogInterface;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.R;
import com.truelease.RetrofitModel.ChangePasswordModel;
import com.truelease.User.UserData;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    MaterialButton btnChange;
    TextInputEditText etCurrentPass, etNewPassword, etConfirmPassword;
    String strCurrentPass = "", strNewPass = "", strConfPass = "";
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        btnChange = findViewById(R.id.btnChange);
        etCurrentPass = findViewById(R.id.etCurrentPass);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        back = findViewById(R.id.back);


        back.setOnClickListener(v -> finish());


        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strCurrentPass = etCurrentPass.getText().toString().trim();
                strNewPass = etNewPassword.getText().toString().trim();
                strConfPass = etConfirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(strCurrentPass)) {
                    ReturnErrorToast.showWarningToast(ChangePasswordActivity.this, "Current password empty");
                } else if (TextUtils.isEmpty(strNewPass)) {
                    ReturnErrorToast.showWarningToast(ChangePasswordActivity.this, "New password empty");

                } else if (TextUtils.isEmpty(strNewPass)) {
                    ReturnErrorToast.showWarningToast(ChangePasswordActivity.this, "Confirm password empty");

                } else if (!strNewPass.equals(strConfPass)) {
                    ReturnErrorToast.showWarningToast(ChangePasswordActivity.this, "Password not matched");

                } else {
                    Map<String, String> map = new HashMap<>();
                    map.put("userID", UserData.getUserID(getApplicationContext()));
                    map.put("password", strNewPass);
                    map.put("new_password", strConfPass);
                    changePassword(map);
                }


            }
        });
    }

    public void changePassword(Map<String, String> param) {

        DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, ChangePasswordActivity.this);


        Call<ChangePasswordModel> call = APIClient.getAPIClient().changePassword(param);
        call.enqueue(new Callback<ChangePasswordModel>() {
            @Override
            public void onResponse(@NonNull Call<ChangePasswordModel> call, @NonNull Response<ChangePasswordModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(ChangePasswordActivity.this);
                    dialogInterface.hideDialog();
                }

                ChangePasswordModel model = response.body();

                if (model != null) {
                    dialogInterface.hideDialog();
                    if (model.getResult()) {
                        dialogInterface.hideDialog();
                        dialog(model.getMessage()).show();
                    } else {
                        ReturnErrorToast.showWarningToast(ChangePasswordActivity.this, model.getMessage());
                        dialogInterface.hideDialog();
                    }
                } else {
                    dialogInterface.hideDialog();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ChangePasswordModel> call, @NonNull Throwable t) {
                dialogInterface.hideDialog();
            }
        });

    }

    public Dialog dialog(String msg) {
        Dialog dialog = new Dialog(ChangePasswordActivity.this);
        dialog.setContentView(R.layout.success_dialog);
        TextView text = dialog.findViewById(R.id.text);
        text.setText(msg);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

}