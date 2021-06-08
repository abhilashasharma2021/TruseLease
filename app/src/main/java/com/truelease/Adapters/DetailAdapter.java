package com.truelease.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.truelease.Activities.SavedDetailsActivity;
import com.truelease.R;
import com.truelease.Room.SaveDetails.Details;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.Viewholder> {


    public List<Details> detailsList;
    Context context;
    public static final String NAME = "NAME";
    public static final String EMAIL_ID = "EMAIL_ID";
    public static final String MOBILE_NUMBER = "MOBILE_NUMBER";
    public static final String FULL_ADDRESS = "FULL_ADDRESS";
    public static final String PINCODE = "PINCODE";
    public static final String CITY_ID = "CITY_ID";
    public static final String STATE = "STATE";


    public DetailAdapter(List<Details> detailsList, Context context) {
        this.detailsList = detailsList;
        this.context = context;
    }

    @NonNull
    @Override
    public DetailAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.details_list, parent, false);
        return new DetailAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapter.Viewholder holder, int position) {

        Details details = detailsList.get(position);
        if (details != null) {

            holder.name.setText(details.getName());
            holder.address.setText(details.getFulladdress());
            holder.email.setText(details.getEmail());

            holder.delete.setOnClickListener(v -> {
                SavedDetailsActivity.detailViewModel.delete(details);
                detailsList.remove(details);
                notifyDataSetChanged();
            });

            holder.relative.setOnClickListener(v -> {
                Log.e("skjxsx", "getName: " + details.getName());
                Log.e("skjxsx", "getEmail: " + details.getEmail());
                Log.e("skjxsx", "getNumber: " + details.getNumber());
                Log.e("skjxsx", "getFulladdress: " + details.getFulladdress());
                Log.e("skjxsx", "getPincode: " + details.getPincode());
                Log.e("skjxsx", "getCityId: " + details.getCityId());
                Log.e("skjxsx", "getState: " + details.getState());

                if (context instanceof SavedDetailsActivity){
                    Intent intent = new Intent();
                    intent.putExtra(NAME, details.getName());
                    intent.putExtra(EMAIL_ID, details.getEmail());
                    intent.putExtra(MOBILE_NUMBER, details.getNumber());
                    intent.putExtra(FULL_ADDRESS, details.getFulladdress());
                    intent.putExtra(PINCODE, details.getPincode());
                    intent.putExtra(CITY_ID, details.getCityId());
                    intent.putExtra(STATE, details.getState());
                    ((Activity) context).setResult(Activity.RESULT_OK,intent);
                    ((Activity) context).finish();
                }else {
                    Toast.makeText(context, "else", Toast.LENGTH_SHORT).show();
                }



            });


        }
    }


    public void setDetails(List<Details> details) {
        this.detailsList = details;
        notifyDataSetChanged();
    }

    public Details getWishListFromPosition(int position) {
        return detailsList.get(position);
    }


    @Override
    public int getItemCount() {
        return detailsList.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder {


        TextView name, address, email;
        ImageView delete;
        RelativeLayout relative;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            email = itemView.findViewById(R.id.email);
            delete = itemView.findViewById(R.id.delete);
            relative = itemView.findViewById(R.id.relative);
        }
    }
}
