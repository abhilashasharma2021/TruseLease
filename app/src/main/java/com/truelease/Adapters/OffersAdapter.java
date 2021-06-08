package com.truelease.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.truelease.R;
import com.truelease.databinding.MyOfferBinding;
import com.truelease.viewModel.MyOffersViewModel;

import java.util.List;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {

    List<MyOffersViewModel> myOffersViewModelList;
    Context context;
    LayoutInflater layoutInflater;

    public OffersAdapter(List<MyOffersViewModel> myOffersViewModelList, Context context) {
        this.myOffersViewModelList = myOffersViewModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public OffersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        MyOfferBinding myOfferBinding = DataBindingUtil.inflate(layoutInflater, R.layout.offers_layout, parent, false);
        return new OffersAdapter.ViewHolder(myOfferBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersAdapter.ViewHolder holder, int position) {

        MyOffersViewModel data = myOffersViewModelList.get(position);
        holder.bind(data);


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

        private final MyOfferBinding myOfferBinding;


        public ViewHolder(@NonNull MyOfferBinding myOfferBinding) {
            super(myOfferBinding.getRoot());
            this.myOfferBinding = myOfferBinding;
        }


        public void bind(MyOffersViewModel myOffersViewModel) {
            this.myOfferBinding.setMyoffermodel(myOffersViewModel);
            myOfferBinding.executePendingBindings();

        }

        public MyOfferBinding getMyListBinding() {
            return myOfferBinding;
        }
    }
}

