package com.truelease.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.truelease.Interface.LoginDetail.IdeleteBooking;
import com.truelease.R;
import com.truelease.RetrofitModel.BookingHistoryModel;

import java.util.Date;
import java.util.List;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.ViewHolder> {

    List<BookingHistoryModel.Datum> bookingHistoryModelList;
    Context context;
    IdeleteBooking ideleteBooking;


    public BookingHistoryAdapter(List<BookingHistoryModel.Datum> bookingHistoryModelList, Context context) {
        this.bookingHistoryModelList = bookingHistoryModelList;
        this.context = context;
    }


    public void setOnDeleteBookingListner(IdeleteBooking ideleteBooking) {

        this.ideleteBooking = ideleteBooking;
    }


    @NonNull
    @Override
    public BookingHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.history_layout, parent, false);
        return new BookingHistoryAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BookingHistoryAdapter.ViewHolder holder, int position) {

        BookingHistoryModel.Datum data = bookingHistoryModelList.get(position);

        if (data != null) {

            if (!data.getProductDetail().getProductImages().equals("")) {
                Glide.with(context).load(data.getProductDetail().getProductImages()).override(250, 250).placeholder(R.drawable.logo2).into(holder.r_image);

            }

            if (data.getSellerDetail() != null) {
                holder.dealWith.setText("Deal with: " + data.getSellerDetail().getName());
                holder.email.setText("Owner email: " + data.getSellerDetail().getEmail());
                holder.txt_time.setText("Date: " + new Date().toString());
            }


            holder.imgDelete.setOnClickListener(v -> {

                if (!data.getId().equals("")) {
                    ideleteBooking.deleteBooking(data.getId());
                    bookingHistoryModelList.remove(data);
                }
            });


        }

    }


    @Override
    public int getItemCount() {
        return bookingHistoryModelList.size();
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


        ImageView r_image, imgDelete;
        TextView dealWith, email, txt_time;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            r_image = itemView.findViewById(R.id.r_image);
            dealWith = itemView.findViewById(R.id.dealWith);
            email = itemView.findViewById(R.id.email);
            txt_time = itemView.findViewById(R.id.txt_time);
            imgDelete = itemView.findViewById(R.id.imgDelete);


        }
    }
}



