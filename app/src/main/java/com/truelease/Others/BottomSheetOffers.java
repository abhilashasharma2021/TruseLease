package com.truelease.Others;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialogFragment;
import com.truelease.Adapters.SecondOfferAdapter;
import com.truelease.R;
import com.truelease.viewModel.MyOffersViewModel;

public class BottomSheetOffers extends RoundedBottomSheetDialogFragment {


    RecyclerView offerRecycler;
    SecondOfferAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        offerRecycler = view.findViewById(R.id.offerRecycler);
        offerRecycler.setItemViewCacheSize(20);
        offerRecycler.setHasFixedSize(true);
        offerRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        MyOffersViewModel myOffersViewModel = ViewModelProviders.of(this).get(MyOffersViewModel.class);
        myOffersViewModel.getMutableLiveDataList(getActivity()).observe(this, myOffersViewModels -> {

            adapter = new SecondOfferAdapter(myOffersViewModels, getActivity());
            offerRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            offerRecycler.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        });







        return view;
    }


}
