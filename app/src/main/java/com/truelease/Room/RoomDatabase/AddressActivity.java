package com.truelease.Room.RoomDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sdsmdg.tastytoast.TastyToast;
import com.truelease.R;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity {

    AddressViewModel addressViewModel;
    RecyclerView addressRecycler;
    FloatingActionButton addAddress;
    ImageView back;
    TextView notFound;

    public static final int ADD_RESULT = 1;

    List<Address> addressList = new ArrayList<>();

    AddressAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        addressRecycler = findViewById(R.id.addressRecycler);
        addAddress = findViewById(R.id.addAddress);
        back = findViewById(R.id.back);
        notFound = findViewById(R.id.notFound);

        addressRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        addressRecycler.setHasFixedSize(true);
        addressRecycler.setItemViewCacheSize(20);

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AddAddressActivity.class);
                startActivityForResult(intent, ADD_RESULT);
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        adapter = new AddressAdapter(addressList, this);
        addressRecycler.setAdapter(adapter);


        addressViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(AddressViewModel.class);

        addressViewModel.getAllAddress().observe(AddressActivity.this, new Observer<List<Address>>() {
            @Override
            public void onChanged(List<Address> addresses) {

                if (addresses.size() > 0) {
                    adapter.setNotes(addresses);
                    notFound.setVisibility(View.GONE);
                } else {
                    notFound.setVisibility(View.VISIBLE);

                }


            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                addressViewModel.delete(adapter.getAddressAt(viewHolder.getAdapterPosition()));
                TastyToast.makeText(getApplicationContext(), "Deleted", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
            }
        }).attachToRecyclerView(addressRecycler);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_RESULT && resultCode == RESULT_OK) {
            String city = data.getStringExtra(AddAddressActivity.EXTRA_CITY);
            String address = data.getStringExtra(AddAddressActivity.EXTRA_ADDRESS);

            Address add = new Address(city, address);


            Log.e("snjsn", address + "," + city);
            Log.e("snjsn", adapter.getAddressList().size() + "");


            addressViewModel.insert(add);


        } else {

            TastyToast.makeText(getApplicationContext(), "Address not saved", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
        }
    }
}