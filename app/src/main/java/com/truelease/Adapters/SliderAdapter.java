package com.truelease.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;
import com.truelease.Activities.ProductDetailsActivity;
import com.truelease.Model.SliderImageData;
import com.truelease.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {

    private final Context context;
    ArrayList<SliderImageData> sliderImageData;


    public SliderAdapter(ArrayList<SliderImageData> sliderImageData, Context context) {
        this.context = context;
        this.sliderImageData = sliderImageData;
    }

    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, null);
        return new SliderAdapterViewHolder(inflate);
    }


    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, int position) {
        SliderImageData data = sliderImageData.get(position);
        viewHolder.img_Background.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (data != null) {

            Picasso.get().load(data.getPath() + data.getImage()).placeholder(R.drawable.logo2)
                    .into(viewHolder.img_Background);

            ProductDetailsActivity.whatsApp.setOnClickListener(view -> shareAppWithSocial(context, "com.whatsapp", data.getId(), viewHolder));
            ProductDetailsActivity.facebook.setOnClickListener(view -> shareAppWithSocial(context, "com.facebook.katana", data.getId(), viewHolder));


        } else {

            viewHolder.img_Background.setImageResource(R.drawable.logo2);
        }

    }


    public void shareAppWithSocial(Context context, String application,
                                   String id, SliderAdapterViewHolder viewHolder) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setPackage(application);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(android.content.Intent.EXTRA_TITLE, "True Lease");
        intent.setType("text/plain");

        intent.setType("image/*");
        String tit = "True Lease";
        Drawable mDrawable = viewHolder.img_Background.getDrawable();
        Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), mBitmap, "True Lease", null);
        Uri imageUri = Uri.parse(path);
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        String shareMessage = "\nHave a look at this amazing product available on rent on this app called True Lease.\n" +
                "Download from Google Playstore:";

        shareMessage = tit + " " + shareMessage + " " + "http://www.truelease.com/product" + "/" + id;

        intent.putExtra(Intent.EXTRA_TEXT, shareMessage);

        try {
            context.startActivity(Intent.createChooser(intent, "Choose one"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "Please Install App", Toast.LENGTH_LONG).show();
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
