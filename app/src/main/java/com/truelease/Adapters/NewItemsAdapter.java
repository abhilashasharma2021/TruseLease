package com.truelease.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;
import com.truelease.Activities.ProductDetailsActivity;
import com.truelease.Model.NewItemsData;
import com.truelease.Others.AppConstats;
import com.truelease.Others.CustomImageView;
import com.truelease.Others.SharedHelper;
import com.truelease.R;

import java.util.List;

public class NewItemsAdapter extends RecyclerView.Adapter<NewItemsAdapter.ViewHolder> {

    List<NewItemsData> newItemsDataList;
    Context context;
    View view;

    public NewItemsAdapter(List<NewItemsData> newItemsDataList, Context context) {
        this.newItemsDataList = newItemsDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            view = LayoutInflater.from(context).inflate(R.layout.ou_services_layout, parent, false);

        } catch (Exception e) {

        }
        return new NewItemsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewItemsAdapter.ViewHolder holder, int position) {

        NewItemsData data = newItemsDataList.get(position);

        if (data != null) {
            Picasso.get().load(data.getPath() + data.getImage()).placeholder(R.drawable.logo2)
                    .into(holder.image);
            holder.image.setOnClickListener(v -> {
                SharedHelper.putKey(context, AppConstats.PRODUCT_ID, data.getProductID());
                context.startActivity(new Intent(context, ProductDetailsActivity.class));

            });

        }

    }

    @Override
    public int getItemCount() {
        return newItemsDataList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        CustomImageView image;
        MaterialCardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            card = itemView.findViewById(R.id.card);
        }
    }
}

