package com.truelease.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.truelease.Activities.ProductDetailsActivity;
import com.truelease.Activities.WishListActivity;
import com.truelease.Others.AppConstats;
import com.truelease.Others.SharedHelper;
import com.truelease.R;
import com.truelease.Room.RoomWishList.Wishlist;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {

    List<Wishlist> wishList;
    Context context;


    public WishListAdapter(List<Wishlist> wishList, Context context) {
        this.wishList = wishList;
        this.context = context;
    }

    @NonNull
    @Override
    public WishListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wishlist, parent, false);
        return new WishListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.ViewHolder holder, int position) {

        final Wishlist data = wishList.get(position);

        if (data != null) {

            if (!data.getProductName().equals("")) {
                if (data.getProductName().length() > 15) {
                    holder.productName.setText(data.getProductName().substring(0, 15) + "....");
                } else {
                    holder.productName.setText(data.getProductName());
                }
            }


            Picasso.get().load(data.getImage()).placeholder(R.drawable.logo2)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(holder.image);

            holder.moveToCart.setOnClickListener(v -> {
                SharedHelper.putKey(context, AppConstats.PRODUCT_ID, data.getProductID());
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            });


            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WishListActivity.wishListViewmodel.delete(data);
                    wishList.remove(data);
                    WishListActivity.popField.popView(holder.card);
                    notifyDataSetChanged();
                    empty();
                }
            });

        }


    }


    public void setNotes(List<Wishlist> wishlistList) {
        this.wishList = wishlistList;
        notifyDataSetChanged();
    }

    public Wishlist getWishListFromPosition(int position) {
        return wishList.get(position);
    }

    public void empty() {
        if (wishList.size() == 0) {
            WishListActivity.rel_emptycart.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public int getItemCount() {
        return wishList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        ImageView remove;
        TextView moveToCart, productName;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            moveToCart = itemView.findViewById(R.id.viewP);
            productName = itemView.findViewById(R.id.productName);
            image = itemView.findViewById(R.id.image);
            remove = itemView.findViewById(R.id.remove);
            card = itemView.findViewById(R.id.card);
        }
    }


 /*   public void removeProduct(Map<String, String> param, WishListData wishListData, WishListAdapter.ViewHolder holder) {

        Call<ProductRemove> call = APIClient.getAPIClient().removeFromWishList(param);
        call.enqueue(new Callback<ProductRemove>() {
            @Override
            public void onResponse(@NonNull Call<ProductRemove> call, @NonNull Response<ProductRemove> response) {
                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast((Activity) context);
                }

                ProductRemove remove = response.body();
                if (remove != null) {

                    if (remove.getResult()) {
                        wishList.remove(wishListData);
                        PopField popField = PopField.attach2Window((Activity) context);
                        popField.popView(holder.card);
                        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.remove);
                        mediaPlayer.start();
                        notifyDataSetChanged();
                        empty();
                        TastyToast.makeText(context, "Removed", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    } else {
                        TastyToast.makeText(context, remove.getMessage(), TastyToast.LENGTH_SHORT, TastyToast.ERROR);

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductRemove> call, @NonNull Throwable t) {
                Log.e("iwueiwe", t.getMessage());
            }
        });
    }*/


}
