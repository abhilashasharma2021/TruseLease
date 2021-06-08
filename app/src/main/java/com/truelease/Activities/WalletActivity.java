package com.truelease.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.truelease.Adapters.TransactionAdapter;
import com.truelease.ApiData.APIClient;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.MyDialog.DialogInterface;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.R;
import com.truelease.RetrofitModel.ShowWalletAmountModel;
import com.truelease.RetrofitModel.TransactionData;
import com.truelease.User.UserData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletActivity extends AppCompatActivity {

    RecyclerView transactionsRecycler;
    ImageView back, prf;
    TextView name,txtAmount;
    List<TransactionData.Datum> transactionDataList;
    ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);


        transactionsRecycler = findViewById(R.id.transactionsRecycler);
        back = findViewById(R.id.back);
        prf = findViewById(R.id.prf);
        name = findViewById(R.id.name);
        txtAmount = findViewById(R.id.txtAmount);
        progress = findViewById(R.id.progress);


        name.setText(UserData.getUserName(getApplicationContext()));


        if (!UserData.getUserPath(WalletActivity.this).equals("") && !UserData.getUserImage(WalletActivity.this).equals("")) {
            Picasso.get().load(UserData.getUserPath(WalletActivity.this) + UserData.getUserImage(WalletActivity.this)).placeholder(R.drawable.avatar).into(prf);

        }


        back.setOnClickListener(v -> finish());

        transactionsRecycler.setHasFixedSize(true);
        transactionsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        transactionsRecycler.setItemViewCacheSize(20);

        if (!UserData.getUserID(this).equals("")){
            showTransactions();
            showAmount();
        }else {
            Toast.makeText(this, "Login first", Toast.LENGTH_SHORT).show();
        }



    }

    private void showTransactions() {


        DialogInterface dialog = new CustomDialog();
        dialog.showDialog(R.layout.pr_dialog,WalletActivity.this);

        Call<TransactionData> call = APIClient.getAPIClient().showWallet(UserData.getUserID(getApplicationContext()));

        call.enqueue(new Callback<TransactionData>() {
            @Override
            public void onResponse(@NonNull Call<TransactionData> call, @NonNull Response<TransactionData> response) {

                if (!response.isSuccessful()) {
                    dialog.hideDialog();
                    ReturnErrorToast.showToast(WalletActivity.this);
                } else {

                    TransactionData transactionData = response.body();
                    if (transactionData != null) {
                        dialog.hideDialog();
                        if (transactionData.getResult()) {
                            transactionDataList = new ArrayList<>();

                            List<TransactionData.Datum> datum = transactionData.getData();

                            transactionDataList.addAll(datum);


                        }

                        transactionsRecycler.setAdapter(new TransactionAdapter(transactionDataList, WalletActivity.this));
                    }else {
                        dialog.hideDialog();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TransactionData> call, @NonNull Throwable t) {
                dialog.hideDialog();
            }
        });
    }


    private void showAmount() {
        progress.setVisibility(View.VISIBLE);
        Call<ShowWalletAmountModel> call = APIClient.getAPIClient().showAmount(UserData.getUserID(WalletActivity.this));
        call.enqueue(new Callback<ShowWalletAmountModel>() {
            @Override
            public void onResponse(@NonNull Call<ShowWalletAmountModel> call, @NonNull Response<ShowWalletAmountModel> response) {

                if (!response.isSuccessful()) {
                    progress.setVisibility(View.GONE);
                    ReturnErrorToast.showToast(WalletActivity.this);
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
                            ReturnErrorToast.showWarningToast(WalletActivity.this, model.getMessage());
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


}