package com.truelease.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.truelease.Activities.ChatActivity;
import com.truelease.Activities.ProductDetailsActivity;
import com.truelease.ApiData.APIClient;
import com.truelease.Fragments.CartFragment;
import com.truelease.Model.CartData;
import com.truelease.Others.AppConstats;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.Others.SharedHelper;
import com.truelease.R;
import com.truelease.RetrofitModel.DeleteFromCartModel;
import com.truelease.RetrofitModel.UpdateQuantityModel;
import com.truelease.User.UserData;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    public List<CartData> cartDataList;
    Context context;
    String quantities = "";


    public CartAdapter(List<CartData> cartDataList, Context context) {
        this.cartDataList = cartDataList;
        this.context = context;

    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_layout, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.ViewHolder holder, int position) {

        final CartData data = cartDataList.get(position);


        if (data != null) {


            holder.btnChat.setOnClickListener(view -> {
                if (!data.getPrOwnerID().equals("0")){
                    SharedHelper.putKey(context,AppConstats.CHAT_CLICKED_ON,"MyOrders");
                    SharedHelper.putKey(context,AppConstats.CHAT_OWNER_ID_MY_ORDER,data.getPrOwnerID());
                    context.startActivity(new Intent(context, ChatActivity.class));
                }else {
                    ReturnErrorToast.showWarningToast((Activity) context,"Owner detail not availbale or owner must delete his/her account");
                }

            });

            holder.imgDelete.setOnClickListener(view -> {


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Product");
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setMessage("Do you want to remove this item?");

                builder.setPositiveButton("Yes", (dialog, which) -> {
                    deleteFromCart(UserData.getUserID(context), data.getProductId(), view);
                    notifyDataSetChanged();
                    empty();
                }).setNegativeButton("No", (dialog, which) -> dialog.dismiss());

                AlertDialog dialog = builder.create();
                dialog.show();


            });


            if (!data.getQuantitiy().equals("")) {
                quantities = cartDataList.get(position).getQuantitiy();

            }

            holder.prPrice.setText(data.getCurrencySymbol() + " " + data.getTotal());


            holder.prName.setText(data.getProductName());

            double sum = sum();
            CartFragment.total.setText("Total : " + data.getCurrencySymbol() + " " + twoDigitAfterDecimal(sum + charges()));
            CartFragment.totalPrice.setText(data.getCurrencySymbol() + " " + twoDigitAfterDecimal(sum));
            CartFragment.duration.setText(data.getTotal_selected_days() + " days");

            if (!data.getPriceType().equals("")) {

                switch (data.getPriceType()) {
                    case "1":
                        CartFragment.rentalCost.setText(data.getCurrencySymbol() + " " + data.getPrice() + getTimePeriod(data.getPriceType()));
                        break;
                    case "2":
                        CartFragment.rentalCost.setText(data.getCurrencySymbol() + " " + data.getPrice() + getTimePeriod(data.getPriceType()));
                        break;
                    case "3":
                        CartFragment.rentalCost.setText(data.getCurrencySymbol() + " " + data.getPrice() + getTimePeriod(data.getPriceType()));
                        break;
                    case "4":
                        CartFragment.rentalCost.setText(data.getCurrencySymbol() + " " + data.getPrice() + getTimePeriod(data.getPriceType()));
                        break;
                }
            }


            CartFragment.serviceCharge.setText(data.getCurrencySymbol() + " " + twoDigitAfterDecimal(charges()));


            double finalAmount = sum + charges();
            finalAmount = Math.ceil(finalAmount);

            SharedHelper.putKey(context, AppConstats.TOTAL_AMOUNT, finalAmount + "");


            holder.productImage.setOnClickListener(v -> {
                SharedHelper.putKey(context, AppConstats.PRODUCT_ID, data.getProductId());
                context.startActivity(new Intent(context, ProductDetailsActivity.class));
            });


            holder.mcard.setOnClickListener(v -> {

            });


            Glide.with(context).load(data.getPath() + data.getImage()).placeholder(R.drawable.logo2).into(holder.productImage);

            /* holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String q = holder.txquantity.getText().toString();
                  *//*  String qu = String.valueOf(Integer.parseInt(q) + 1);
                    holder.txquantity.setText(qu);
                    Log.e("erewrerw", "rr : " + qu);
                    updateQuantity(UserData.getUserID(context), data.getProductId(), qu, holder, view);*//*

                }
            });


            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String q = holder.txquantity.getText().toString();
                 *//*   String qu = String.valueOf(Integer.parseInt(q) - 1);

                    int quant = Integer.parseInt(qu);
                    if (quant < 1) {

                    } else {
                        holder.txquantity.setText(qu);
                        Log.e("erewrerw", "rr : " + qu);
                        updateQuantity(UserData.getUserID(context), data.getProductId(), qu, holder, v);

                    }*//*

                }
            });
*/

            if (data.getDescription().length() > 80) {
                holder.description.setText(data.getDescription().substring(0, 70) + "....");
            } else {
                holder.description.setText(data.getDescription() + "");
            }


        }


    }


    public String getTimePeriod(String priceType) {
        String timePeriod = "";
        switch (priceType) {
            case "1":
                timePeriod = "/hour";
                break;
            case "2":
                timePeriod = "/day";
                break;
            case "3":
                timePeriod = "/week";
                break;
            case "4":
                timePeriod = "/month";
                break;

            default:
                break;
        }

        return timePeriod;
    }


    public String twoDigitAfterDecimal(double num) {
        return new DecimalFormat("##.##").format(num);
    }


    public List<CartData> getCartList() {
        return cartDataList;
    }

    public double charges() {

        double sum = 0;

        for (int i = 0; i < cartDataList.size(); i++) {
            double val = Double.parseDouble(cartDataList.get(i).getTotal());
            int q = Integer.parseInt(cartDataList.get(i).getTotal_selected_days());
            //  sum = sum + (val * q);
            sum = sum + val;
        }


        double percentage;

        percentage = (7 / 100d) * sum;
        return percentage;

    }


    public double sum() {

        double sum = 0;

        for (int i = 0; i < cartDataList.size(); i++) {
            double val = Double.parseDouble(cartDataList.get(i).getTotal());
            int q = Integer.parseInt(cartDataList.get(i).getTotal_selected_days());
            // sum = sum + (val * q);
            sum = sum + val;
        }


        return sum;
    }


    public void empty() {

        if (cartDataList.size() == 0) {
            CartFragment.rel_emptycart.setVisibility(View.VISIBLE);
            CartFragment.cardBottom.setVisibility(View.GONE);
            CartFragment.totalAmountCart.setVisibility(View.GONE);

        }

    }


    @Override
    public int getItemCount() {
        return cartDataList.size();
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

        ImageView imgDelete, productImage;
        TextView prPrice, prName, tenureMsg, description;
        MaterialButton btnChat;
        MaterialCardView mcard;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            productImage = itemView.findViewById(R.id.productImage);
            prPrice = itemView.findViewById(R.id.prPrice);
            prName = itemView.findViewById(R.id.prName);
            tenureMsg = itemView.findViewById(R.id.tenureMsg);
            mcard = itemView.findViewById(R.id.mcard);
            description = itemView.findViewById(R.id.description);
            btnChat = itemView.findViewById(R.id.btnChat);
        }
    }


    public void deleteFromCart(String userID, String productID, View view) {

        Map<String, String> param = new HashMap<>();
        param.put("userID", userID);
        param.put("productID", productID);
        Call<DeleteFromCartModel> cartModelCall = APIClient.getAPIClient().deleteFromCart(param);
        cartModelCall.enqueue(new Callback<DeleteFromCartModel>() {
            @Override
            public void onResponse(@NonNull Call<DeleteFromCartModel> call, @NonNull Response<DeleteFromCartModel> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(context, response.code(), Toast.LENGTH_SHORT).show();
                }

                DeleteFromCartModel cartModel = response.body();

                if (cartModel != null) {

                    if (cartModel.getResult()) {
                        Toast.makeText(context, "Item removed", Toast.LENGTH_SHORT).show();
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        Fragment myFragment = new CartFragment();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();


                    } else {
                        Toast.makeText(context, cartModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DeleteFromCartModel> call, @NonNull Throwable t) {
                Log.e("uweyw", t.getMessage());
            }
        });
    }


    public void updateQuantity(String userID, String productID, String quantity, CartAdapter.ViewHolder holder, View view) {
        Map<String, String> param = new HashMap<>();
        param.put("userID", userID);
        param.put("productID", productID);
        param.put("quwantity", quantity);
        Call<UpdateQuantityModel> call = APIClient.getAPIClient().updateQuantity(param);
        call.enqueue(new Callback<UpdateQuantityModel>() {
            @Override
            public void onResponse(@NonNull Call<UpdateQuantityModel> call, @NonNull Response<UpdateQuantityModel> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(context, response.code(), Toast.LENGTH_SHORT).show();
                }

                UpdateQuantityModel model = response.body();

                if (model != null) {

                    if (model.getResult()) {

                        UpdateQuantityModel.Data data1 = model.getData();
                        if (data1 != null) {
                            // holder.txquantity.setText(data1.getQuwantity());
                            AppCompatActivity activity = (AppCompatActivity) view.getContext();
                            Fragment myFragment = new CartFragment();
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).commit();


                        }


                    } else {
                        Log.e("ksjkdls", "kljsa" + model.getMessage());
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<UpdateQuantityModel> call, @NonNull Throwable t) {
                Log.e("ksjkdls", "kljsa" + t.getMessage());
            }
        });
    }

}


