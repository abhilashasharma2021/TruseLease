package com.truelease.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.truelease.Activities.NotificationsActivity;
import com.truelease.ApiData.APIClient;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.MyDialog.DialogInterface;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.R;
import com.truelease.RetrofitModel.DeleteNotificationModel;
import com.truelease.RetrofitModel.NotificationModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    List<NotificationModel.Data> notificationList;
    Context context;


    public NotificationAdapter(List<NotificationModel.Data> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notifications_list, parent, false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {

        final NotificationModel.Data data = notificationList.get(position);



        if (data != null) {

            empty();

            holder.title.setText(data.getTitle());
            holder.message.setText(data.getMessage());
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Delete Notification");
                    builder.setIcon(android.R.drawable.ic_delete);
                    builder.setMessage("Do you want to remove this notification?");

                    builder.setPositiveButton("Yes", (dialog, which) -> {

                        if (!data.getNotificationID().equals("")) {
                            Map<String, String> param = new HashMap<>();
                            param.put("notificationID", data.getNotificationID());
                            deleteNotification(param,data);
                            notifyDataSetChanged();
                        }


                    }).setNegativeButton("No", (dialog, which) -> dialog.dismiss());

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

        }

    }

    public void empty() {

        if (notificationList.size() == 0) {
            NotificationsActivity.noNoti.setVisibility(View.VISIBLE);
        }

    }



    @Override
    public int getItemCount() {
        return notificationList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_profile, imgDelete;
        MaterialCardView card;
        TextView message, title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_profile = itemView.findViewById(R.id.img_profile);
            card = itemView.findViewById(R.id.card);
            title = itemView.findViewById(R.id.title);
            message = itemView.findViewById(R.id.message);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }


    private void deleteNotification(Map<String, String> param, NotificationModel.Data data) {

        DialogInterface dialog = new CustomDialog();
        dialog.showDialog(R.layout.pr_dialog, context);

        Call<DeleteNotificationModel> call = APIClient.getAPIClient().deleteNotification(param);
        call.enqueue(new Callback<DeleteNotificationModel>() {
            @Override
            public void onResponse(@NonNull Call<DeleteNotificationModel> call, @NonNull Response<DeleteNotificationModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast((Activity) context);
                    dialog.hideDialog();
                } else {

                    DeleteNotificationModel model = response.body();
                    if (model != null) {
                        dialog.hideDialog();
                        if (model.getResult()) {
                            ((NotificationsActivity)context).showNoti();
                            notificationList.remove(data);
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();

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
