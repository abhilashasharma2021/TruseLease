package com.truelease.Room.RoomDatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.truelease.R;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.Viewholder> {

    List<Address> addressList;
    Context context;


    public AddressAdapter(List<Address> addressList, Context context) {
        this.addressList = addressList;
        this.context = context;
    }

    @NonNull
    @Override
    public AddressAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.address_list, parent, false);
        return new AddressAdapter.Viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.Viewholder holder, int position) {

        Address address = addressList.get(position);
        holder.city.setText(address.getCity());
        holder.address.setText(address.getFullAddress());
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }


    public void setNotes(List<Address> addressList) {
        this.addressList = addressList;
        notifyDataSetChanged();
    }


    public List<Address> getAddressList() {

        return addressList;
    }


    public Address getAddressAt(int position) {
        return addressList.get(position);
    }


    public static class Viewholder extends RecyclerView.ViewHolder {


        TextView city, address;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            city = itemView.findViewById(R.id.city);
            address = itemView.findViewById(R.id.address);

        }
    }
}
