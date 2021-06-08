package com.truelease.Adapters;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.truelease.Activities.ProductDetailsActivity;
import com.truelease.Activities.WishListActivity;
import com.truelease.ApiData.APIClient;
import com.truelease.Model.MyPostData;
import com.truelease.Others.AppConstats;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.Others.SharedHelper;
import com.truelease.R;
import com.truelease.RetrofitModel.DeletePostModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.ViewHolder> {

    List<MyPostData> myPostDataList;
    Context context;

    public MyPostAdapter(List<MyPostData> myPostDataList, Context context) {
        this.myPostDataList = myPostDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mypost, parent, false);
        return new MyPostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostAdapter.ViewHolder holder, int position) {

        final MyPostData data = myPostDataList.get(position);

        if (data != null) {


            if (data.getProductName().length() > 15) {
                holder.productName.setText(data.getProductName().substring(0,10)+"...");
            }else {
                holder.productName.setText(data.getProductName());
            }


            holder.productPrice.setText(data.getRent_per_month() + "Rs/month");
            Glide.with(context).load(data.getPath() + data.getImage())
                    .placeholder(R.drawable.logo2).override(250, 250)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.image);


            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SharedHelper.putKey(context, AppConstats.PRODUCT_ID, data.getProductID());
                    context.startActivity(new Intent(context, ProductDetailsActivity.class));
                }
            });


            holder.deleteItem.setOnClickListener(view -> deleteDialog(data.getProductID(), data));


        }


    }


    public void empty() {
        if (myPostDataList.size() == 0) {
            WishListActivity.rel_emptycart.setVisibility(View.VISIBLE);
        }

    }


    public void deleteDialog(String pID, MyPostData data) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.delete_dialog);
        RelativeLayout relYes = dialog.findViewById(R.id.relYes);
        RelativeLayout relNo = dialog.findViewById(R.id.relNo);

        relYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteMyPost(pID, data);

                dialog.dismiss();

            }
        });


        relNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {
            Log.e("dskjdsjd", e.getMessage(), e);
        }

    }


    @Override
    public int getItemCount() {
        return myPostDataList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        ImageView remove;
        TextView productName, productPrice;
        CardView card;
        Button deleteItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            image = itemView.findViewById(R.id.image);
            remove = itemView.findViewById(R.id.remove);
            card = itemView.findViewById(R.id.card);
            deleteItem = itemView.findViewById(R.id.deleteItem);
        }
    }


    public void deleteMyPost(String productID, MyPostData data) {

        Call<DeletePostModel> call = APIClient.getAPIClient().deleteMyPost(productID);
        call.enqueue(new Callback<DeletePostModel>() {
            @Override
            public void onResponse(@NonNull Call<DeletePostModel> call, @NonNull Response<DeletePostModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast((Activity) context);
                }

                DeletePostModel model = response.body();
                if (model != null) {

                    if (model.getResult()) {
                        myPostDataList.remove(data);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, model.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DeletePostModel> call, @NonNull Throwable t) {
                Log.e("cbdsjhjs", t.getMessage());
            }
        });
    }


}
