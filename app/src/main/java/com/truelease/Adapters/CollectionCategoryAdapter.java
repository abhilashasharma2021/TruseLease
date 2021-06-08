package com.truelease.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.truelease.Model.CollectionCategoryData;
import com.truelease.Model.SubcategoryData;
import com.truelease.R;
import com.truelease.RetrofitModel.CollectionCategoryModel;

import java.util.List;

public class CollectionCategoryAdapter extends RecyclerView.Adapter<CollectionCategoryAdapter.ViewHolder> {

    List<CollectionCategoryModel.Datum> collectionCategoryDataList;
    Context context;

    public CollectionCategoryAdapter(List<CollectionCategoryModel.Datum> collectionCategoryDataList, Context context) {
        this.collectionCategoryDataList = collectionCategoryDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public CollectionCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sub_category_layout, parent, false);
        return new CollectionCategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionCategoryAdapter.ViewHolder holder, int position) {

        CollectionCategoryModel.Datum data = collectionCategoryDataList.get(position);

        holder.collcategoryName.setText(data.getCollectionName());



    }


    @Override
    public int getItemCount() {
        return collectionCategoryDataList.size();
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

        LinearLayout linear;
        TextView collcategoryName;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linear = itemView.findViewById(R.id.linear);
            collcategoryName = itemView.findViewById(R.id.subcategoryName);
            image = itemView.findViewById(R.id.image);
        }
    }
}

