package com.truelease.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.truelease.Adapters.MyOrdersAdapter;
import com.truelease.ApiData.APIClient;
import com.truelease.Model.MyOrderData;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.MyDialog.DialogInterface;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.R;
import com.truelease.RetrofitModel.MyRentingItemModel;
import com.truelease.User.UserData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrdersActivity extends AppCompatActivity {


    RecyclerView orderRecycler;
    List<MyOrderData> myOrdersModelList;

    TextView noOrder;

    BroadcastReceiver mBroadcastReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            showMyOrders();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        ImageView back = findViewById(R.id.back);
        noOrder = findViewById(R.id.noOrder);
        orderRecycler = findViewById(R.id.orderRecycler);
        orderRecycler.setHasFixedSize(true);
        orderRecycler.setItemViewCacheSize(20);
        orderRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        showMyOrders();

        back.setOnClickListener(v -> finish());
    }


    public void showMyOrders() {
        DialogInterface dialog = new CustomDialog();
        dialog.showDialog(R.layout.pr_dialog, MyOrdersActivity.this);

        Call<MyRentingItemModel> call = APIClient.getAPIClient().showRentedItems(UserData.getUserID(getApplicationContext()), "");
        call.enqueue(new Callback<MyRentingItemModel>() {
            @Override
            public void onResponse(@NonNull Call<MyRentingItemModel> call, @NonNull Response<MyRentingItemModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(MyOrdersActivity.this);
                    dialog.hideDialog();
                }


                MyRentingItemModel model = response.body();
                if (model != null) {

                    if (model.getResult()) {
                        dialog.hideDialog();
                        myOrdersModelList = new ArrayList<>();
                        List<MyRentingItemModel.Datum> dataList = model.getData();

                        if (dataList != null && dataList.size() > 0) {

                            for (MyRentingItemModel.Datum data : dataList) {

                                MyOrderData itemData = new MyOrderData();
                                itemData.setProductName(data.getProduct());
                                itemData.setPrice_type(data.getPriceType());
                                itemData.setProductID(data.getProductID());
                                itemData.setRent_per_month(data.getRentPerMonth());
                                itemData.setRent_status(data.getRentStatus());
                                itemData.setpOwnerId(data.getUserID());


                                MyRentingItemModel.Datum.ProductImages productImage = data.getProductImages();

                                if (productImage != null) {
                                    itemData.setPath(productImage.getPath());
                                    itemData.setImage(productImage.getImage());
                                }
                                myOrdersModelList.add(itemData);
                            }
                            orderRecycler.setAdapter(new MyOrdersAdapter(myOrdersModelList, MyOrdersActivity.this));
                            noOrder.setVisibility(View.GONE);
                        } else {
                            dialog.hideDialog();
                            noOrder.setVisibility(View.VISIBLE);
                            myOrdersModelList.clear();
                        }

                    }


                } else {
                    dialog.hideDialog();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MyRentingItemModel> call, @NonNull Throwable t) {
                dialog.hideDialog();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();


        registerReceiver(mBroadcastReciever, new IntentFilter("Check"));
    }
}