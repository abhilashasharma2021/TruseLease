package com.truelease.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;
import com.truelease.Activities.ProductDetailsActivity;
import com.truelease.Model.AwesomeItemData;
import com.truelease.Others.AppConstats;
import com.truelease.Others.SharedHelper;
import com.truelease.R;

import java.util.List;

public class AwesomeItemAdapter extends RecyclerView.Adapter<AwesomeItemAdapter.ViewHolder> {

    List<AwesomeItemData> awesomeItemModelList;
    Context context;
    private int lastPosition = -1;



    public AwesomeItemAdapter(List<AwesomeItemData> awesomeItemModelList, Context context) {
        this.awesomeItemModelList = awesomeItemModelList;
        this.context = context;
    }


    @NonNull
    @Override
    public AwesomeItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.awesome_item_list, parent, false);
        return new AwesomeItemAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AwesomeItemAdapter.ViewHolder holder, int position) {


        AwesomeItemData data = awesomeItemModelList.get(position);

        setAnimation(holder.card, position);

        if (data != null) {

            if (!data.getProductImage().equals("") || !data.getProductPath().equals("")) {

                Picasso.get().load(data.getProductPath() + data.getProductImage()).placeholder(R.drawable.logo2)
                        .resize(200,200)
                        .into(holder.prImage);


            } else {
                Log.e("oops", "image or path empty");
            }


            if (!data.getProductName().equals("")) {
                if (data.getProductName().length() > 14) {
                    holder.textName.setText(data.getProductName().substring(0, 14) + "....");
                } else {
                    holder.textName.setText(data.getProductName());
                }
            }


            holder.card.setOnClickListener(v -> {
                if (!data.getProductId().equals("")) {
                    SharedHelper.putKey(context, AppConstats.PRODUCT_ID, data.getProductId());
                    context.startActivity(new Intent(context, ProductDetailsActivity.class));
                } else {
                    Toast.makeText(context, "Product data is empty", Toast.LENGTH_SHORT).show();
                }


            });
        }


    }


    private void setAnimation(final View viewToAnimate, final int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    @Override
    public int getItemCount() {
        return awesomeItemModelList.size();
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

        TextView textName;
        ImageView prImage;
        MaterialCardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.textName);
            prImage = itemView.findViewById(R.id.prImage);
            card = itemView.findViewById(R.id.card);
        }
    }
}


