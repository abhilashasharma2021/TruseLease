package com.truelease.Activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.truelease.Adapters.OffersAdapter;
import com.truelease.R;
import com.truelease.viewModel.MyOffersViewModel;

public class OffersActivity extends AppCompatActivity {


    RecyclerView offerRecycler;
    ImageView back;
    OffersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        offerRecycler = findViewById(R.id.offerRecycler);
        back = findViewById(R.id.back);
        offerRecycler.setItemViewCacheSize(20);
        offerRecycler.setHasFixedSize(true);
        offerRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        MyOffersViewModel myOffersViewModel = ViewModelProviders.of(this).get(MyOffersViewModel.class);
        myOffersViewModel.getMutableLiveDataList(this).observe(this, myOffersViewModels -> {

            adapter = new OffersAdapter(myOffersViewModels, getApplicationContext());
            offerRecycler.setLayoutManager(new LinearLayoutManager(OffersActivity.this, LinearLayoutManager.VERTICAL, false));
            offerRecycler.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        });


        back.setOnClickListener(view -> finish());
    }


}