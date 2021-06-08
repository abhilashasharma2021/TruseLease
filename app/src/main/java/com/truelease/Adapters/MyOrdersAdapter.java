package com.truelease.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.truelease.Activities.ChatActivity;
import com.truelease.Activities.MyOrdersActivity;
import com.truelease.Activities.ProductDetailsActivity;
import com.truelease.ApiData.APIClient;
import com.truelease.Model.MyOrderData;
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

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.ViewHolder> {

    List<MyOrderData> myOrdersModelList;
    Context context;

    public MyOrdersAdapter(List<MyOrderData> myOrdersModelList, Context context) {
        this.myOrdersModelList = myOrdersModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_orders_list, parent, false);
        return new MyOrdersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersAdapter.ViewHolder holder, int position) {

        final MyOrderData data = myOrdersModelList.get(position);

        if (data != null) {

            if (!data.getPath().equals("") && !data.getImage().equals("")) {
                Glide.with(context).load(data.getPath() + data.getImage()).placeholder(R.drawable.logo2).
                        override(350, 350).
                        diskCacheStrategy(DiskCacheStrategy.ALL).
                        into(holder.img);
            }


            holder.relChat.setOnClickListener(view -> {
                if (!data.getpOwnerId().equals("0")){
                    SharedHelper.putKey(context,AppConstats.CHAT_CLICKED_ON,"MyOrders");
                    SharedHelper.putKey(context,AppConstats.CHAT_OWNER_ID_MY_ORDER,data.getpOwnerId());
                    context.startActivity(new Intent(context, ChatActivity.class));
                }else {
                    ReturnErrorToast.showWarningToast((Activity) context,"Owner detail not availbale or owner must delete his/her account");
                }

            });


            holder.img.setOnClickListener(v -> {
                SharedHelper.putKey(context, AppConstats.PRODUCT_ID, data.getProductID());
                context.startActivity(new Intent(context, ProductDetailsActivity.class));
            });



            if (!data.getRent_status().equals("")) {


                Log.e("oioqwsqw", data.getRent_status() + "");

                if (data.getRent_status().equals("1")) {
                    holder.btnOne.setEnabled(false);
                    holder.btnOne.setText("Wait for approval..");
                    // holder.btnTwo.setEnabled(false);
                    holder.btnOne.setBackgroundColor(Color.parseColor("#F9F200"));
                    holder.btnOne.setTextColor(Color.parseColor("#000000"));

                } else if (data.getRent_status().equals("2")) {
                    holder.btnOne.setEnabled(true);
                    holder.btnOne.setText("Receive");
                    // holder.btnTwo.setEnabled(false);
                } else if (data.getRent_status().equals("3")) {
                    holder.btnOne.setEnabled(true);
                    holder.btnOne.setText("Return");
                    holder.btnOne.setBackgroundColor(Color.parseColor("#2EE1C3"));
                    holder.btnOne.setTextColor(Color.parseColor("#FFFFFF"));
                    //  holder.btnTwo.setEnabled(true);

                    holder.btnOne.setOnClickListener(v -> {
                        Map<String, String> param = new HashMap<>();
                        param.put("userID", UserData.getUserID(context));
                        param.put("productID", data.getProductID());
                        param.put("rent_status", data.getRent_status());

                        changeStatus(param, data);


                    });


                } else if (data.getRent_status().equals("4")) {

                    holder.btnOne.setEnabled(false);
                    holder.btnOne.setText("Return request sent");
                    //holder.btnTwo.setEnabled(false);
                    // holder.btnTwo.setText("Return request sent");
                }


            }


            if (!data.getRent_status().equals("") && data.getRent_status().equals("2")) {


                holder.btnOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Map<String, String> param = new HashMap<>();
                        param.put("userID", UserData.getUserID(context));
                        param.put("productID", data.getProductID());
                        param.put("rent_status", data.getRent_status());

                        changeStatus(param, data);


                    }
                });

            }


            if (!data.getRent_status().equals("") && data.getRent_status().equals("3")) {


                holder.btnOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Map<String, String> param = new HashMap<>();
                        param.put("userID", UserData.getUserID(context));
                        param.put("productID", data.getProductID());
                        param.put("rent_status", data.getRent_status());

                        changeStatus(param, data);


                    }
                });

            }


        }


    }


    @Override
    public int getItemCount() {
        return myOrdersModelList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        Button btnOne;
        RelativeLayout relChat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            btnOne = itemView.findViewById(R.id.btnOne);
            relChat = itemView.findViewById(R.id.relChat);
           // btnTwo = itemView.findViewById(R.id.btnTwo);
        }
    }


    public void changeStatus(Map<String, String> param, MyOrderData data) {

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
                        myOrdersModelList.remove(data);
                        Toast.makeText(context, "Request Sent....", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        ((MyOrdersActivity) context).showMyOrders();
                    } else {
                        Toast.makeText(context, model.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else {

                }
            }

            @Override
            public void onFailure(@NonNull Call<MyRentingItemModel> call, @NonNull Throwable t) {

            }
        });
    }

}

