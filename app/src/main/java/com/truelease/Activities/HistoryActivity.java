package com.truelease.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.truelease.Adapters.BookingHistoryAdapter;
import com.truelease.ApiData.APIClient;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.MyDialog.DialogInterface;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.R;
import com.truelease.RetrofitModel.BookingHistoryModel;
import com.truelease.RetrofitModel.DeleteBookingModel;
import com.truelease.User.UserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {


    RecyclerView historyRecycler;
    List<BookingHistoryModel.Datum> bookingHistoryModelList;
    ImageView back;
    TextView noHistory, deleteAll;
    BookingHistoryAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        historyRecycler = findViewById(R.id.historyRecycler);
        deleteAll = findViewById(R.id.deleteAll);
        back = findViewById(R.id.back);
        noHistory = findViewById(R.id.noHistory);
        back.setOnClickListener(v -> finish());
        historyRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        historyRecycler.setItemViewCacheSize(20);
        historyRecycler.setHasFixedSize(true);
        showHistory();




    }


    private void showHistory() {
        DialogInterface dialog = new CustomDialog();
        dialog.showDialog(R.layout.pr_dialog, HistoryActivity.this);

        Call<BookingHistoryModel> call = APIClient.getAPIClient().bookingHistory(UserData.getUserID(getApplicationContext()));
        call.enqueue(new Callback<BookingHistoryModel>() {
            @Override
            public void onResponse(@NonNull Call<BookingHistoryModel> call, @NonNull Response<BookingHistoryModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(HistoryActivity.this);
                    dialog.hideDialog();
                } else {

                    BookingHistoryModel model = response.body();

                    if (model != null) {
                        dialog.hideDialog();
                        if (model.getResult()) {
                            bookingHistoryModelList = new ArrayList<>();

                            if (model.getData().size() > 0) {
                                bookingHistoryModelList.addAll(model.getData());
                                noHistory.setVisibility(View.GONE);
                                adapter = new BookingHistoryAdapter(bookingHistoryModelList, HistoryActivity.this);
                                historyRecycler.setAdapter(adapter);
                                adapter.setOnDeleteBookingListner(bookingID -> {

                                    Log.e("sjxsxa", bookingID + "");
                                    Map<String, String> map = new HashMap<>();
                                    map.put("id", bookingID);
                                    deleteBookings(map);
                                    showHistory();
                                    adapter.notifyDataSetChanged();

                                });


                                deleteAll.setOnClickListener(v -> {
                                    if (adapter.getItemCount() > 0) {
                                        if (!UserData.getUserID(HistoryActivity.this).equals("")){
                                            Map<String, String> map = new HashMap<>();
                                            map.put("userID", UserData.getUserID(HistoryActivity.this));
                                            deleteAllBookings(map);
                                        }else {
                                            Toast.makeText(HistoryActivity.this, "Login first", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(HistoryActivity.this, "No notifications to delete", Toast.LENGTH_SHORT).show();
                                    }



                                });


                            } else {
                                noHistory.setVisibility(View.VISIBLE);
                            }

                        }

                    } else {

                        dialog.hideDialog();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BookingHistoryModel> call, @NonNull Throwable t) {
                dialog.hideDialog();
            }
        });

    }


    private void deleteBookings(Map<String, String> param) {


        Call<DeleteBookingModel> call = APIClient.getAPIClient().deleteBooking(param);
        call.enqueue(new Callback<DeleteBookingModel>() {
            @Override
            public void onResponse(@NonNull Call<DeleteBookingModel> call, @NonNull Response<DeleteBookingModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(HistoryActivity.this);
                } else {

                    DeleteBookingModel model = response.body();
                    if (model != null) {

                        if (model.getResult()) {
                            bookingHistoryModelList = new ArrayList<>();
                            bookingHistoryModelList.clear();
                            Toast.makeText(HistoryActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DeleteBookingModel> call, @NonNull Throwable t) {

                Log.e("owidsw", t.getMessage() + "");
            }
        });
    }


    private void deleteAllBookings(Map<String, String> param) {


        Call<DeleteBookingModel> call = APIClient.getAPIClient().deleteBooking(param);
        call.enqueue(new Callback<DeleteBookingModel>() {
            @Override
            public void onResponse(@NonNull Call<DeleteBookingModel> call, @NonNull Response<DeleteBookingModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(HistoryActivity.this);
                } else {

                    DeleteBookingModel model = response.body();
                    if (model != null) {

                        if (model.getResult()) {

                            Toast.makeText(HistoryActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DeleteBookingModel> call, @NonNull Throwable t) {

                Log.e("owidsw", t.getMessage() + "");
            }
        });
    }


}