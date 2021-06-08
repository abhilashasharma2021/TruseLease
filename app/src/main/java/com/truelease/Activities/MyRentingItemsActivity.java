package com.truelease.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.truelease.Adapters.MyRentingItemAdapter;
import com.truelease.ApiData.APIClient;
import com.truelease.Model.MyRentingItemData;
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

public class MyRentingItemsActivity extends AppCompatActivity {


    RecyclerView rentingListRecycler;
    ImageView back;
    RelativeLayout rel_empty;
    List<MyRentingItemData> myRentingItemDataList;


    BroadcastReceiver mBroadcastReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            showMyRentings();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_renting_items);

        rentingListRecycler = findViewById(R.id.rentingListRecycler);
        rel_empty = findViewById(R.id.rel_empty);
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> finish());
        rentingListRecycler.setItemViewCacheSize(20);
        rentingListRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rentingListRecycler.setHasFixedSize(true);


        showMyRentings();
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(mBroadcastReciever, new IntentFilter("Check"));
    }


    public void showMyRentings() {

        Log.e("jxsaxkaa", UserData.getUserID(getApplicationContext())+"");

        DialogInterface dialog = new CustomDialog();
        dialog.showDialog(R.layout.pr_dialog, MyRentingItemsActivity.this);

        Call<MyRentingItemModel> call = APIClient.getAPIClient().showRentedItems(UserData.getUserID(getApplicationContext()), "1");
        call.enqueue(new Callback<MyRentingItemModel>() {
            @Override
            public void onResponse(@NonNull Call<MyRentingItemModel> call, @NonNull Response<MyRentingItemModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(MyRentingItemsActivity.this);
                    dialog.hideDialog();
                }

                MyRentingItemModel model = response.body();

                if (model != null) {

                    if (model.getResult()) {
                        dialog.hideDialog();
                        myRentingItemDataList = new ArrayList<>();
                        List<MyRentingItemModel.Datum> dataList = model.getData();

                        if (dataList != null && dataList.size() > 0) {

                            for (MyRentingItemModel.Datum data : dataList) {

                                MyRentingItemData itemData = new MyRentingItemData();
                                itemData.setProductName(data.getProduct());
                                itemData.setPrice_type(data.getPriceType());
                                itemData.setProductID(data.getProductID());
                                itemData.setRent_per_month(data.getRentPerMonth());
                                itemData.setRentStatus(data.getRentStatus());
                                itemData.setBookingID(data.getBookingID());
                                itemData.setBookingFrom(data.getBooking_from());
                                itemData.setBookingTo(data.getBooking_upto());


                                MyRentingItemModel.Datum.ProductImages productImage = data.getProductImages();
                                MyRentingItemModel.Datum.UserDetail userDetail = data.getUserDetail();

                                if (productImage != null) {
                                    itemData.setPath(productImage.getPath());

                                    itemData.setImage(productImage.getImage());

                                }


                                if (userDetail != null) {
                                    MyRentingItemModel.Datum.UserDetail user = data.getUserDetail();
                                    if (user != null) {

                                        itemData.setRenterName(user.getName());


                                    }

                                }


                                myRentingItemDataList.add(itemData);
                            }
                            rentingListRecycler.setAdapter(new MyRentingItemAdapter(myRentingItemDataList, MyRentingItemsActivity.this));
                            rel_empty.setVisibility(View.GONE);
                        } else {
                            dialog.hideDialog();
                            rel_empty.setVisibility(View.VISIBLE);
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
}