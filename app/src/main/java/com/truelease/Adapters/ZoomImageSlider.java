package com.truelease.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.truelease.Model.ShowImageData;
import com.truelease.R;

import java.util.List;

public class ZoomImageSlider extends SliderViewAdapter<ZoomImageSlider.SliderAdapterViewHolder> {

    private final Context context;
    List<ShowImageData> sliderImageData;



    public ZoomImageSlider(List<ShowImageData> sliderImageData, Context context) {
        this.context = context;
        this.sliderImageData = sliderImageData;
    }

    @Override
    public ZoomImageSlider.SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, null);
        return new ZoomImageSlider.SliderAdapterViewHolder(inflate);
    }


    @Override
    public void onBindViewHolder(ZoomImageSlider.SliderAdapterViewHolder viewHolder, int position) {

        ShowImageData data = sliderImageData.get(position);


        viewHolder.img_Background.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (data != null) {
            Picasso.get().load(data.getPath() + data.getImage()).placeholder(R.drawable.logo2)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(viewHolder.img_Background);
        } else {

            viewHolder.img_Background.setImageResource(R.drawable.logo2);
        }

    }

    @Override
    public int getCount() {
        return sliderImageData.size();
    }

    public static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView img_Background;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);

            img_Background = itemView.findViewById(R.id.img_Background);
            this.itemView = itemView;

        }
    }
}

