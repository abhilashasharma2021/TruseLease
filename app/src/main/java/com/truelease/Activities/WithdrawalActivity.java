package com.truelease.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import com.truelease.ApiData.APIClient;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.MyDialog.DialogInterface;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.R;
import com.truelease.RetrofitModel.ShowWalletAmountModel;
import com.truelease.RetrofitModel.WithdrawRequestModel;
import com.truelease.User.UserData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WithdrawalActivity extends AppCompatActivity {
    ImageView back, prf;
    TextView name, txtAmount;
    RadioGroup radioGroup;
    RadioButton radioButton, radio_button_1, radio_button_2;

    ProgressBar progress;
    MaterialButton btnWithdraw, btnWithdraw2;
    LinearLayout linearOne, linearTwo;

    ShowWalletAmountModel.Datum showData = null;

    TextInputEditText etName, etIFSC, etIFSCr, etMobile, etAccount, etAmount, amount, email;
    String strName = "", strIFSC = "", strIFSCr = "", strMobile = "", strAccount = "", strAmount = "", strAmount2 = "", strEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);

        prf = findViewById(R.id.prf);
        name = findViewById(R.id.name);
        txtAmount = findViewById(R.id.txtAmount);
        back = findViewById(R.id.back);
        progress = findViewById(R.id.progress);
        radioGroup = findViewById(R.id.radioGroup);
        btnWithdraw = findViewById(R.id.btnWithdraw);
        btnWithdraw2 = findViewById(R.id.btnWithdraw2);
        linearOne = findViewById(R.id.linearOne);
        linearTwo = findViewById(R.id.linearTwo);
        radio_button_1 = findViewById(R.id.radio_button_1);
        radio_button_2 = findViewById(R.id.radio_button_2);

        etName = findViewById(R.id.etName);
        etIFSC = findViewById(R.id.etIFSC);
        etIFSCr = findViewById(R.id.etIFSCr);
        etMobile = findViewById(R.id.etMobile);
        etAccount = findViewById(R.id.etAccount);
        etAmount = findViewById(R.id.etAmount);
        amount = findViewById(R.id.amount);
        email = findViewById(R.id.email);


        radio_button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                linearOne.setVisibility(View.VISIBLE);
                linearTwo.setVisibility(View.GONE);
            }
        });

        radio_button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linearOne.setVisibility(View.GONE);
                linearTwo.setVisibility(View.VISIBLE);
            }
        });


        btnWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selected = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selected);
                if (selected == -1) {
                    Toast.makeText(WithdrawalActivity.this, "Select from the above", Toast.LENGTH_SHORT).show();

                } else {

                    if (radioButton.getText().equals("Account")) {
                        strName = etName.getText().toString().trim();
                        strIFSC = etIFSC.getText().toString().trim();
                        strIFSCr = etIFSCr.getText().toString().trim();
                        strMobile = etMobile.getText().toString().trim();
                        strAccount = etAccount.getText().toString().trim();
                        strAmount = etAmount.getText().toString().trim();

                        if (TextUtils.isEmpty(strName) || TextUtils.isEmpty(strIFSC) || TextUtils.isEmpty(strIFSCr) || TextUtils.isEmpty(strMobile) || TextUtils.isEmpty(strAccount) || TextUtils.isEmpty(strAmount)) {
                            ReturnErrorToast.showWarningToast(WithdrawalActivity.this, "Please fill all details");
                        } else if (!strIFSC.equals(strIFSCr)) {
                            Toast.makeText(WithdrawalActivity.this, "IFSC not matched", Toast.LENGTH_SHORT).show();
                        } else {


                            Map<String, String> param = new HashMap<>();
                            param.put("userID", UserData.getUserID(getApplicationContext()));
                            param.put("request_type", "0");
                            param.put("recipient_name", strName);
                            param.put("ifsc_code", strIFSC);
                            param.put("mobile_number", strMobile);
                            param.put("account_number", strAccount);
                            param.put("amount", strAmount);

                            sendRequest(param);

                        }

                    } else {
                        strAmount2 = amount.getText().toString().trim();
                        strEmail = email.getText().toString().trim();
                        if (TextUtils.isEmpty(strAmount2) || TextUtils.isEmpty(strEmail)) {
                            ReturnErrorToast.showWarningToast(WithdrawalActivity.this, "Please fill all details");
                        } else {

                            Map<String, String> param = new HashMap<>();
                            param.put("userID", UserData.getUserID(getApplicationContext()));
                            param.put("request_type", "1");
                            param.put("email", strEmail);
                            param.put("amount", strAmount);

                            sendRequest(param);
                        }
                    }

                }
            }
        });


        btnWithdraw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selected = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selected);
                if (selected == -1) {
                    Toast.makeText(WithdrawalActivity.this, "Select from the above", Toast.LENGTH_SHORT).show();

                } else {

                    if (radioButton.getText().equals("Account")) {
                        strName = etName.getText().toString().trim();
                        strIFSC = etIFSC.getText().toString().trim();
                        strIFSCr = etIFSCr.getText().toString().trim();
                        strMobile = etMobile.getText().toString().trim();
                        strAccount = etAccount.getText().toString().trim();
                        strAmount = etAmount.getText().toString().trim();

                        if (TextUtils.isEmpty(strName) || TextUtils.isEmpty(strIFSC) || TextUtils.isEmpty(strIFSCr) || TextUtils.isEmpty(strMobile) || TextUtils.isEmpty(strAccount) || TextUtils.isEmpty(strAmount)) {
                            ReturnErrorToast.showWarningToast(WithdrawalActivity.this, "Please fill all details");
                        } else if (!strIFSC.equals(strIFSCr)) {
                            Toast.makeText(WithdrawalActivity.this, "IFSC not matched", Toast.LENGTH_SHORT).show();
                        } else {


                            Map<String, String> param = new HashMap<>();
                            param.put("userID", UserData.getUserID(getApplicationContext()));
                            param.put("request_type", "0");
                            param.put("recipient_name", strName);
                            param.put("ifsc_code", strIFSC);
                            param.put("mobile_number", strMobile);
                            param.put("account_number", strAccount);
                            param.put("amount", strAmount);

                            sendRequest(param);

                        }

                    } else {
                        strAmount2 = amount.getText().toString().trim();
                        strEmail = email.getText().toString().trim();
                        if (TextUtils.isEmpty(strAmount2) || TextUtils.isEmpty(strEmail)) {
                            ReturnErrorToast.showWarningToast(WithdrawalActivity.this, "Please fill all details");
                        } else {

                            Map<String, String> param = new HashMap<>();
                            param.put("userID", UserData.getUserID(getApplicationContext()));
                            param.put("request_type", "1");
                            param.put("email", strEmail);
                            param.put("amount", strAmount2);

                            sendRequest(param);
                        }
                    }

                }
            }
        });


        name.setText(UserData.getUserName(getApplicationContext()));

        back.setOnClickListener(v -> finish());

        if (!UserData.getUserPath(WithdrawalActivity.this).equals("") && !UserData.getUserImage(WithdrawalActivity.this).equals("")) {
            Picasso.get().load(UserData.getUserPath(WithdrawalActivity.this) + UserData.getUserImage(WithdrawalActivity.this)).placeholder(R.drawable.avatar).into(prf);

        }

        showAmount();

    }


    private void showAmount() {
        progress.setVisibility(View.VISIBLE);
        Call<ShowWalletAmountModel> call = APIClient.getAPIClient().showAmount(UserData.getUserID(WithdrawalActivity.this));
        call.enqueue(new Callback<ShowWalletAmountModel>() {
            @Override
            public void onResponse(@NonNull Call<ShowWalletAmountModel> call, @NonNull Response<ShowWalletAmountModel> response) {

                if (!response.isSuccessful()) {
                    progress.setVisibility(View.GONE);
                    ReturnErrorToast.showToast(WithdrawalActivity.this);
                } else {
                    progress.setVisibility(View.GONE);
                    ShowWalletAmountModel model = response.body();
                    if (model != null) {

                        if (model.getResult()) {
                            List<ShowWalletAmountModel.Datum> dataList = model.getData();

                            if (dataList.size() > 0) {

                                for (ShowWalletAmountModel.Datum data : dataList) {
                                    txtAmount.setVisibility(View.VISIBLE);
                                    txtAmount.setText("Rs " + data.getWithdrawAmount());


                                }
                            }
                        } else {
                            txtAmount.setVisibility(View.VISIBLE);
                            ReturnErrorToast.showWarningToast(WithdrawalActivity.this, model.getMessage());
                            txtAmount.setText("Rs. 00.00");
                        }

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ShowWalletAmountModel> call, @NonNull Throwable t) {
                progress.setVisibility(View.GONE);
                Log.e("sjkdks", t.getMessage(), t);
            }
        });

    }


    private void sendRequest(Map<String, String> param) {

        DialogInterface dialog = new CustomDialog();
        dialog.showDialog(R.layout.pr_dialog, WithdrawalActivity.this);

        Call<WithdrawRequestModel> call = APIClient.getAPIClient().sendWithdrawRequest(param);
        call.enqueue(new Callback<WithdrawRequestModel>() {
            @Override
            public void onResponse(@NonNull Call<WithdrawRequestModel> call, @NonNull Response<WithdrawRequestModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(WithdrawalActivity.this);
                    dialog.hideDialog();
                } else {

                    WithdrawRequestModel model = response.body();
                    if (model != null) {
                        dialog.hideDialog();
                        if (model.getResult()) {

                            Toast.makeText(WithdrawalActivity.this, model.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(WithdrawalActivity.this, model.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        dialog.hideDialog();
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<WithdrawRequestModel> call, @NonNull Throwable t) {
                dialog.hideDialog();

                Log.e("jsadk", t.getMessage(), t);
            }
        });
    }
}