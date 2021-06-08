package com.truelease.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.truelease.R;

import java.io.File;
import java.util.List;

import rb.popview.PopField;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    List<File> imageDataList;
    Context context;

    public ImageAdapter(List<File> imageDataList, Context context) {
        this.imageDataList = imageDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_lauout, parent, false);
        return new ImageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ViewHolder holder, int position) {

        File file = imageDataList.get(position);


        Glide.with(context).load(file).into(holder.image);

        holder.delete.setOnClickListener(view -> {
            imageDataList.remove(file);
            PopField popField = PopField.attach2Window((Activity) context);
            popField.popView(holder.image);
            notifyDataSetChanged();
        });


    }


    @Override
    public int getItemCount() {
        return imageDataList.size();
    }


    public List<File> getImageList() {
        return imageDataList;
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

        ImageView image, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}



