package com.truelease.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.truelease.Activities.ShowImagesActivity;
import com.truelease.Model.ShowImageData;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.R;

import java.util.ArrayList;
import java.util.List;

public class ShowImageAdapter extends RecyclerView.Adapter<ShowImageAdapter.ViewHolder> {

    List<ShowImageData> showImageDataList;
    Context context;

    public ShowImageAdapter(List<ShowImageData> showImageDataList, Context context) {
        this.showImageDataList = showImageDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ShowImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_list, parent, false);
        return new ShowImageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowImageAdapter.ViewHolder holder, int position) {

        final ShowImageData data = showImageDataList.get(position);


        if (data != null) {

            Log.e("dslkdsl", data.getPath() + data.getImage());

            //Glide.with(context).load(data.getPath() + data.getImage()).placeholder(R.drawable.logo2).into(holder.image);
            Picasso.get().load(data.getPath() + data.getImage()).placeholder(R.drawable.logo2)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(holder.image);
            holder.image.setOnClickListener(v -> {

                if (!data.getPath().equals("") && !data.getImage().equals("")) {


                    List<String> imageDataList = new ArrayList<>();
                    for (int i = 0; i < showImageDataList.size(); i++) {


                        if (position == 0) {
                            Log.e("slkcxls", position + "");
                            imageDataList.add(0, showImageDataList.get(position).getImage());
                        } else {
                            Log.e("dfssfdsf", position + "");

                            imageDataList.add(i, showImageDataList.get(i).getImage());
                        }

                    }

                    showZoomImage(showImageDataList);
                } else {
                    ReturnErrorToast.showWarningToast((ShowImagesActivity) context, "Something went wrong");
                }

            });
        }


    }


    private void showZoomImage(List<ShowImageData> showImageDataList) {

        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.zoom_image);
        SliderView imageSlider = dialog.findViewById(R.id.imageSlider);
        imageSlider.setIndicatorAnimation(IndicatorAnimations.DROP);
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);


        ZoomImageSlider sliderAdapter = new ZoomImageSlider(showImageDataList, context);
        imageSlider.setSliderAdapter(sliderAdapter);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        try {
            dialog.show();

        } catch (WindowManager.BadTokenException e) {
            Log.e("dskjdsjd", "xsx" + e.getMessage(), e);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

    }


    @Override
    public int getItemCount() {
        return showImageDataList.size();
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

        ImageView image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}

