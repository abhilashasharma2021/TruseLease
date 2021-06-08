package com.truelease.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.truelease.R;
import com.truelease.viewModel.MyOffersViewModel;

import java.util.List;

public class SecondOfferAdapter extends RecyclerView.Adapter<SecondOfferAdapter.ViewHolder> {

    List<MyOffersViewModel> myOffersViewModelList;
    Context context;
    LayoutInflater layoutInflater;

    public SecondOfferAdapter(List<MyOffersViewModel> myOffersViewModelList, Context context) {
        this.myOffersViewModelList = myOffersViewModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public SecondOfferAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.offers_list, parent, false);

        return new SecondOfferAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SecondOfferAdapter.ViewHolder holder, int position) {

        MyOffersViewModel data = myOffersViewModelList.get(position);

        if (data != null) {

            if (!data.getOfferDiscription().equals("")){
                holder.description.setText(data.getOfferDiscription());
            }



        }

    }


    @Override
    public int getItemCount() {
        return myOffersViewModelList.size();
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


        TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            description = itemView.findViewById(R.id.description);
        }
    }
}


