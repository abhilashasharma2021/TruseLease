package com.truelease.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.truelease.Activities.ProductDetailsActivity;
import com.truelease.Model.BottomSheetSearchProductData;
import com.truelease.Others.AppConstats;
import com.truelease.Others.SharedHelper;
import com.truelease.R;

import java.util.List;

public class BottomSheetSearchProductAdapter extends RecyclerView.Adapter<BottomSheetSearchProductAdapter.ViewHolder> {

    List<BottomSheetSearchProductData> bottomSheetSearchProductDataList;
    Context context;


    public BottomSheetSearchProductAdapter(List<BottomSheetSearchProductData> bottomSheetSearchProductDataList, Context context) {
        this.bottomSheetSearchProductDataList = bottomSheetSearchProductDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public BottomSheetSearchProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.searchproduct_list, parent, false);
        return new BottomSheetSearchProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BottomSheetSearchProductAdapter.ViewHolder holder, int position) {
        BottomSheetSearchProductData data = bottomSheetSearchProductDataList.get(position);

        if (data != null) {
            Glide.with(context).load(data.getPath() + data.getImage()).placeholder(R.drawable.logo2).into(holder.image);
            holder.price.setText(data.getPrice());
            holder.prName.setText(data.getProductName());

            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedHelper.putKey(context, AppConstats.PRODUCT_ID,data.getId());
                    context.startActivity(new Intent(context, ProductDetailsActivity.class));
                }
            });
        }


    }


    @Override
    public int getItemCount() {
        return bottomSheetSearchProductDataList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        MaterialCardView card;
        TextView price, prName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            card = itemView.findViewById(R.id.card);
            price = itemView.findViewById(R.id.price);
            prName = itemView.findViewById(R.id.prName);
        }
    }
}

