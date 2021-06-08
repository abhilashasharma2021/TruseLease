package com.truelease.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.button.MaterialButton;
import com.truelease.Activities.MyRentingItemsActivity;
import com.truelease.Activities.ProductDetailsActivity;
import com.truelease.ApiData.APIClient;
import com.truelease.Model.MyRentingItemData;
import com.truelease.Others.AppConstats;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.Others.SharedHelper;
import com.truelease.R;
import com.truelease.RetrofitModel.MyRentingItemModel;
import com.truelease.User.UserData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRentingItemAdapter extends RecyclerView.Adapter<MyRentingItemAdapter.ViewHolder> {

    List<MyRentingItemData> myRentingItemDataList;
    Context context;

    View view;

    public MyRentingItemAdapter(List<MyRentingItemData> myRentingItemDataList, Context context) {
        this.myRentingItemDataList = myRentingItemDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyRentingItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            view = LayoutInflater.from(context).inflate(R.layout.rentingitem_list, parent, false);

        } catch (Exception e) {

        }
        return new MyRentingItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRentingItemAdapter.ViewHolder holder, int position) {
        MyRentingItemData data = myRentingItemDataList.get(position);


        if (data != null) {

            if (!data.getPath().equals("") && !data.getImage().equals("")) {
                Glide.with(context).load(data.getPath() + data.getImage()).placeholder(R.drawable.logo2).
                        override(350, 350).
                        diskCacheStrategy(DiskCacheStrategy.ALL).
                        into(holder.img);
            }

            holder.prName.setText(data.getProductName());
            if (!data.getRenterName().equals("")) {
                holder.renterName.setText(data.getRenterName());
            } else {
                holder.renterName.setText("Not available");
            }


            if (!data.getBookingFrom().equals("") && !data.getBookingTo().equals("")) {

                String[] bookingFrom = data.getBookingFrom().split(" ");
                String[] bookingTo = data.getBookingTo().split(" ");

                holder.duration.setText(bookingFrom[0] + "-" + bookingTo[0]);
            }


            holder.img.setOnClickListener(v -> {

                SharedHelper.putKey(context, AppConstats.PRODUCT_ID, data.getProductID());
                context.startActivity(new Intent(context, ProductDetailsActivity.class));

            });


            if (!data.getRentStatus().equals("")) {
                Log.e("slkdl", "status :: " + data.getRentStatus());
                switch (data.getRentStatus()) {
                    case "1":
                        holder.btnOne.setText(context.getString(R.string.give));
                        // holder.btnTwo.setEnabled(false);
                        holder.btnOne.setEnabled(true);
                        break;
                    case "2":
                        holder.btnOne.setText(context.getString(R.string.inProgress));
                        //  holder.btnTwo.setEnabled(false);
                        holder.btnOne.setEnabled(false);
                        holder.btnOne.setBackgroundColor(Color.parseColor("#F9F200"));
                        holder.btnOne.setTextColor(Color.parseColor("#000000"));
                        break;
                    case "3":
                        holder.btnOne.setText(context.getString(R.string.onRent));
                        // holder.btnTwo.setEnabled(false);
                        holder.btnOne.setEnabled(false);
                        holder.btnOne.setBackgroundColor(Color.parseColor("#17A504"));
                        holder.btnOne.setTextColor(Color.parseColor("#FFFFFF"));
                        break;

                    case "4":
                        holder.btnOne.setText("Collect");
                        // holder.btnTwo.setEnabled(true);
                        holder.btnOne.setEnabled(true);
                        holder.btnOne.setBackgroundColor(Color.parseColor("#000CFF"));
                        holder.btnOne.setTextColor(Color.parseColor("#FFFFFF"));
                      /*  holder.btnTwo.setText("Collect");
                        holder.btnTwo.setBackgroundColor(Color.parseColor("#000CFF"));
                        holder.btnTwo.setTextColor(Color.parseColor("#FFFFFF"));*/
                        break;
                }


                holder.btnOne.setOnClickListener(v -> {

                    Map<String, String> param = new HashMap<>();
                    param.put("userID", UserData.getUserID(context));
                    param.put("type", "1");
                    param.put("bookingID", data.getBookingID());
                    param.put("productID", data.getProductID());
                    param.put("rent_status", data.getRentStatus());
                    changeStatus(param, data);
                });


            }


        }


    }


    @Override
    public int getItemCount() {
        return myRentingItemDataList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        ImageView img;
        TextView prName, renterName, duration;
        MaterialButton btnOne;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            prName = itemView.findViewById(R.id.prName);
            btnOne = itemView.findViewById(R.id.btnOne);
            renterName = itemView.findViewById(R.id.renterName);
            duration = itemView.findViewById(R.id.duration);


        }
    }

    public void changeStatus(Map<String, String> param, MyRentingItemData data) {

        Call<MyRentingItemModel> call = APIClient.getAPIClient().changeItemStatus(param);
        call.enqueue(new Callback<MyRentingItemModel>() {
            @Override
            public void onResponse(@NonNull Call<MyRentingItemModel> call, @NonNull Response<MyRentingItemModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast((Activity) context);

                }

                MyRentingItemModel model = response.body();
                if (model != null) {

                    if (model.getResult()) {
                        myRentingItemDataList.remove(data);
                        Toast.makeText(context, "Request Sent", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        ((MyRentingItemsActivity) context).showMyRentings();
                    }


                } else {
                    ;
                }
            }

            @Override
            public void onFailure(@NonNull Call<MyRentingItemModel> call, @NonNull Throwable t) {

                Log.e("pospwd", t.getMessage() + "");
            }
        });
    }

}

