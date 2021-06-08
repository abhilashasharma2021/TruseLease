package com.truelease.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.truelease.Adapters.DetailAdapter;
import com.truelease.R;
import com.truelease.Room.SaveDetails.DetailViewModel;
import com.truelease.Room.SaveDetails.Details;
import com.truelease.User.UserData;

import java.util.ArrayList;
import java.util.List;

public class SavedDetailsActivity extends AppCompatActivity {


    RecyclerView detailsRecycler;
    public static DetailViewModel detailViewModel;
    ImageView back;
    TextView noData;

    List<Details> detailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_address);

        detailsRecycler = findViewById(R.id.detailsRecycler);
        back = findViewById(R.id.back);
        noData = findViewById(R.id.noData);


        back.setOnClickListener(v -> finish());


        detailsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        detailsRecycler.setHasFixedSize(true);
        detailsRecycler.setItemViewCacheSize(20);
        detailsList = new ArrayList<>();


        DetailAdapter adapter = new DetailAdapter(detailsList, SavedDetailsActivity.this);
        detailsRecycler.setAdapter(adapter);
        detailViewModel = new ViewModelProvider(SavedDetailsActivity.this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(DetailViewModel.class);

        if (!UserData.getUserID(this).equals("")) {
            detailViewModel.getAllListByUser(UserData.getUserID(this)).observe(this, new Observer<List<Details>>() {
                @Override
                public void onChanged(List<Details> details) {

                    if (!details.isEmpty()) {
                        adapter.setDetails(details);
                        noData.setVisibility(View.GONE);
                    } else {
                        noData.setVisibility(View.VISIBLE);
                        Log.e("owiwyyy", "No data found");
                    }
                }
            });
        }


    }


}