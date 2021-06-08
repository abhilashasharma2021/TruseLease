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

import com.truelease.Adapters.NotificationAdapter;
import com.truelease.ApiData.APIClient;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.MyDialog.DialogInterface;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.R;
import com.truelease.RetrofitModel.DeleteNotificationModel;
import com.truelease.RetrofitModel.NotificationModel;
import com.truelease.User.UserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsActivity extends AppCompatActivity {
    DialogInterface dialogI;
    List<NotificationModel.Data> notificationList;
    RecyclerView notiRecycler;
    public static TextView clearAll, noNoti;
    NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        ImageView back = findViewById(R.id.back);
        notiRecycler = findViewById(R.id.notiRecycler);
        noNoti = findViewById(R.id.noNoti);
        clearAll = findViewById(R.id.clearAll);


        notiRecycler.setItemViewCacheSize(20);
        notiRecycler.setHasFixedSize(true);
        notiRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        back.setOnClickListener(view -> finish());

        showNoti();

        clearAll.setOnClickListener(v -> {
            adapter = new NotificationAdapter(notificationList, NotificationsActivity.this);
            if (adapter.getItemCount() > 0) {
                Map<String, String> param = new HashMap<>();
                param.put("userID", UserData.getUserID(getApplicationContext()));
                deleteNotification(param);
            } else {
                Toast.makeText(this, "No notifications to delete", Toast.LENGTH_SHORT).show();
            }


        });


    }


    public void showNoti() {

        dialogI = new CustomDialog();
        dialogI.showDialog(R.layout.pr_dialog, NotificationsActivity.this);

        Call<NotificationModel> call = APIClient.getAPIClient().showNotifications(UserData.getUserID(getApplicationContext()));
        call.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(@NonNull Call<NotificationModel> call, @NonNull Response<NotificationModel> response) {

                if (!response.isSuccessful()) {
                    dialogI.hideDialog();
                    ReturnErrorToast.showToast(NotificationsActivity.this);
                } else {

                    NotificationModel model = response.body();
                    if (model != null) {
                        dialogI.hideDialog();
                        if (model.getResult()) {
                            notificationList = new ArrayList<>();

                            if (model.getData().size() > 0) {
                                Log.e("owowqpp", "if");
                                notificationList.addAll(model.getData());
                                adapter = new NotificationAdapter(notificationList, NotificationsActivity.this);
                                notiRecycler.setAdapter(adapter);
                            } else {
                                Log.e("owowqpp", "else");
                                noNoti.setVisibility(View.VISIBLE);
                            }

                        } else {
                            Log.e("skdlks", "error" + "List Empty");
                        }
                    } else {
                        dialogI.hideDialog();
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<NotificationModel> call, @NonNull Throwable t) {
                dialogI.hideDialog();
            }
        });
    }


    private void deleteNotification(Map<String, String> param) {

        DialogInterface dialog = new CustomDialog();
        dialog.showDialog(R.layout.pr_dialog, NotificationsActivity.this);

        Call<DeleteNotificationModel> call = APIClient.getAPIClient().deleteNotification(param);
        call.enqueue(new Callback<DeleteNotificationModel>() {
            @Override
            public void onResponse(@NonNull Call<DeleteNotificationModel> call, @NonNull Response<DeleteNotificationModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(NotificationsActivity.this);
                    dialog.hideDialog();
                } else {

                    DeleteNotificationModel model = response.body();
                    if (model != null) {
                        dialog.hideDialog();
                        if (model.getResult()) {
                            showNoti();
                            notificationList.clear();
                            Toast.makeText(NotificationsActivity.this, "All Notifications Deleted", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        dialog.hideDialog();
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<DeleteNotificationModel> call, @NonNull Throwable t) {
                dialog.hideDialog();
            }
        });


    }

}