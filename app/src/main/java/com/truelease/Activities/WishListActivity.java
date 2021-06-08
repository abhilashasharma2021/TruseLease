package com.truelease.Activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.truelease.Adapters.WishListAdapter;
import com.truelease.R;
import com.truelease.Room.RoomWishList.WishListViewmodel;
import com.truelease.Room.RoomWishList.Wishlist;
import com.truelease.User.UserData;

import java.util.ArrayList;
import java.util.List;

import rb.popview.PopField;

public class WishListActivity extends AppCompatActivity {

    RecyclerView wishlistRecycler;

    ImageView back;
    public static RelativeLayout rel_emptycart;
    List<Wishlist> wishlists;

    public static WishListViewmodel wishListViewmodel;

    public static PopField popField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);


        wishlistRecycler = findViewById(R.id.wishlistRecycler);
        rel_emptycart = findViewById(R.id.rel_emptycart);
        back = findViewById(R.id.back);
        wishlistRecycler.setHasFixedSize(true);
        wishlistRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        wishlists = new ArrayList<>();

        popField = PopField.attach2Window(WishListActivity.this);
        WishListAdapter adapter = new WishListAdapter(wishlists, getApplicationContext());
        wishlistRecycler.setAdapter(adapter);

        wishListViewmodel = new ViewModelProvider(WishListActivity.this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(WishListViewmodel.class);

        wishListViewmodel.getWishlistByUserId(UserData.getUserID(getApplicationContext())).observe(this, new Observer<List<Wishlist>>() {
            @Override
            public void onChanged(List<Wishlist> wishlists) {

                if (wishlists.size() > 0) {

                    for (int i = 0; i < wishlists.size(); i++) {
                        Log.e("woiwoisew", wishlists.get(i).getProductID());
                        Log.e("woiwoisew", wishlists.get(i).getStatus());
                    }

                    adapter.setNotes(wishlists);
                    rel_emptycart.setVisibility(View.GONE);
                } else {
                    rel_emptycart.setVisibility(View.VISIBLE);

                }


            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                MediaPlayer mediaPlayer = MediaPlayer.create(WishListActivity.this, R.raw.remove);
                mediaPlayer.start();
                wishListViewmodel.delete(adapter.getWishListFromPosition(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(wishlistRecycler);


        back.setOnClickListener(v -> finish());


    }


}