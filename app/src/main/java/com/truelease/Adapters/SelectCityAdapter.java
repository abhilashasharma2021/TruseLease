package com.truelease.Adapters;

import android.app.Activity;
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
import com.truelease.Activities.BottomNavigationActivity;
import com.truelease.Model.SelectCityData;
import com.truelease.Others.AppConstats;
import com.truelease.Others.SharedHelper;
import com.truelease.R;

import java.util.List;

public class SelectCityAdapter extends RecyclerView.Adapter<SelectCityAdapter.ViewHolder> {

    List<SelectCityData> selectCityDataList;
    Context context;

    public SelectCityAdapter(List<SelectCityData> selectCityDataList, Context context) {
        this.selectCityDataList = selectCityDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public SelectCityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cities_layout, parent, false);
        return new SelectCityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectCityAdapter.ViewHolder holder, int position) {

        final SelectCityData data = selectCityDataList.get(position);

        holder.cityName.setText(data.getCityName());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedHelper.putKey(context, AppConstats.CITY_ID, data.getCityId());
                SharedHelper.putKey(context, AppConstats.CITY_NAME, data.getCityName());
                context.startActivity(new Intent(context, BottomNavigationActivity.class));
                ((Activity) context).finishAffinity();
            }
        });


        if (!data.getCityImage().equals("")) {
            Glide.with(context).load(data.getCityPath() + data.getCityImage()).placeholder(R.drawable.logo2).into(holder.image);
        }


    }


    @Override
    public int getItemCount() {
        return selectCityDataList.size();
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

        TextView cityName;
        MaterialCardView card;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.cityName);
            card = itemView.findViewById(R.id.card);
            image = itemView.findViewById(R.id.image);
        }
    }
}

