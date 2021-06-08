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

import com.squareup.picasso.Picasso;
import com.truelease.Fragments.ProductFragment;
import com.truelease.Model.SubcategoryData;
import com.truelease.R;
import com.truelease.User.UserData;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {

    List<SubcategoryData> subcategoryDataList;
    Context context;
    ProductFragment productFragment;

    public SubCategoryAdapter(List<SubcategoryData> subcategoryDataList, Context context,ProductFragment productFragment) {
        this.subcategoryDataList = subcategoryDataList;
        this.context = context;
        this.productFragment = productFragment;
    }

    @NonNull
    @Override
    public SubCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sub_category_layout, parent, false);
        return new SubCategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.ViewHolder holder, int position) {

        SubcategoryData data = subcategoryDataList.get(position);
        holder.subcategoryName.setText(data.getName());

        Picasso.get().load(data.getPath() + data.getImage()).placeholder(R.drawable.logo2)
                .resize(150,150)
                .into(holder.image);


        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                productFragment.showProduct( data.getCategoryID(),data.getSub_categoryID(), UserData.getCityID(context));
                notifyDataSetChanged();

            }
        });



    }


    @Override
    public int getItemCount() {
        return subcategoryDataList.size();
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
        TextView subcategoryName;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linear = itemView.findViewById(R.id.linear);
            subcategoryName = itemView.findViewById(R.id.subcategoryName);
            image = itemView.findViewById(R.id.image);
        }
    }
}

