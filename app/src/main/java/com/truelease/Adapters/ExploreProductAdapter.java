package com.truelease.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.card.MaterialCardView;
import com.truelease.Fragments.ProductFragment;
import com.truelease.Model.ExploreProductData;
import com.truelease.Others.AppConstats;
import com.truelease.Others.SharedHelper;
import com.truelease.R;

import java.util.List;

public class ExploreProductAdapter extends RecyclerView.Adapter<ExploreProductAdapter.ViewHolder> {

    List<ExploreProductData> exploreProductDataList;
    Context context;

    private int lastPosition = -1;

    public ExploreProductAdapter(List<ExploreProductData> exploreProductDataList, Context context) {
        this.exploreProductDataList = exploreProductDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ExploreProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.explore_product_layout, parent, false);
        return new ExploreProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreProductAdapter.ViewHolder holder, int position) {

        final ExploreProductData data = exploreProductDataList.get(position);


        if (data!=null){
            holder.name.setText(data.getCategoryName());
            setAnimation(holder.card, position);

            Glide.with(context).load(data.getCategoryPath()+data.getCategoryImage())
                    .placeholder(R.drawable.logo2).override(250, 250)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.image);



            holder.card.setOnClickListener(view -> {

                SharedHelper.putKey(context, AppConstats.CATEGORY_ID, data.getCategoryId());
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new ProductFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();

            });




        }

    }


    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return exploreProductDataList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        MaterialCardView card;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            card = itemView.findViewById(R.id.card);
            name = itemView.findViewById(R.id.name);
        }
    }


}
