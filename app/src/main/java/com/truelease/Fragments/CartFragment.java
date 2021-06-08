package com.truelease.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.truelease.Activities.BottomNavigationActivity;
import com.truelease.Activities.DeliveryDetailsActivity;
import com.truelease.Adapters.CartAdapter;
import com.truelease.ApiData.APIClient;
import com.truelease.Model.CartData;
import com.truelease.Others.AppConstats;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.MyDialog.DialogInterface;
import com.truelease.Others.SharedHelper;
import com.truelease.R;
import com.truelease.RetrofitModel.ShowCartModel;
import com.truelease.User.UserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment {

    RecyclerView cartRecycler;
    List<CartData> cartDataList;
    public static RelativeLayout rel_emptycart;
    ImageView menu;
    public static TextView total, totalPrice, serviceCharge, rentalCost, duration;
    public static MaterialCardView cardBottom;
    public static MaterialCardView totalAmountCart;
    Button btnContinue;

    CartAdapter cartAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        cartRecycler = view.findViewById(R.id.cartRecycler);
        rel_emptycart = view.findViewById(R.id.rel_emptycart);
        menu = view.findViewById(R.id.menu);
        total = view.findViewById(R.id.total);
        totalPrice = view.findViewById(R.id.totalPrice);
        cardBottom = view.findViewById(R.id.cardBottom);
        totalAmountCart = view.findViewById(R.id.totalAmountCart);
        btnContinue = view.findViewById(R.id.btnContinue);
        serviceCharge = view.findViewById(R.id.serviceCharge);
        duration = view.findViewById(R.id.duration);
        rentalCost = view.findViewById(R.id.rentalCost);

        cartRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        cartRecycler.setHasFixedSize(true);
        cartRecycler.setItemViewCacheSize(20);
        cartAdapter = new CartAdapter(cartDataList, getActivity());


        menu.setOnClickListener(view1 -> BottomNavigationActivity.drawer.openDrawer(GravityCompat.START));


        onBack(view);

        btnContinue.setOnClickListener(v -> {

            continueDialog();
        });


        return view;
    }


    public void continueDialog() {

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.continue_dialog);
        RelativeLayout relYes = dialog.findViewById(R.id.relYes);
        RelativeLayout relNo = dialog.findViewById(R.id.relNo);

        relYes.setOnClickListener(v -> {

            if (cartAdapter.getCartList().size() > 0 && cartAdapter.getCartList() != null) {

                for (int i = 0; i < cartAdapter.getCartList().size(); i++) {

                    SharedHelper.putKey(getActivity(), AppConstats.CART_ID, cartAdapter.getCartList().get(i).getCartID());
                    Log.e("skjlakszx", cartAdapter.getCartList().get(i).getCartID());
                }
            } else {
                Log.e("skjlakszx", "N");

            }

            startActivity(new Intent(getActivity(), DeliveryDetailsActivity.class));
            dialog.dismiss();
        });

        relNo.setOnClickListener(v -> dialog.dismiss());


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


    @Override
    public void onResume() {
        super.onResume();
        showCarts();
    }

    public void onBack(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {

            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {


                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();


                return true;
            }
            return false;
        });

    }


    public void showCarts() {
        final DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, getActivity());

        Log.e("odsia", "uID" + UserData.getUserID(getActivity()));
        Map<String, String> maps = new HashMap<>();
        maps.put("userID", UserData.getUserID(getActivity()));
        Call<ShowCartModel> call = APIClient.getAPIClient().showCart(maps);
        call.enqueue(new Callback<ShowCartModel>() {
            @Override
            public void onResponse(@NonNull Call<ShowCartModel> call, @NonNull Response<ShowCartModel> response) {

                if (!response.isSuccessful()) {
                    dialogInterface.hideDialog();
                    Toast.makeText(getActivity(), response.code() + "", Toast.LENGTH_SHORT).show();
                }

                ShowCartModel cartModel = response.body();
                if (cartModel != null) {
                    dialogInterface.hideDialog();
                    cartDataList = new ArrayList<>();

                    List<ShowCartModel.Datum> dataList = cartModel.getData();

                    if (dataList.size() > 0) {

                        dialogInterface.hideDialog();

                        for (ShowCartModel.Datum mdata : dataList) {
                            CartData cartData = new CartData();

                            cartData.setCartID(mdata.getCartID());
                            cartData.setPrice(mdata.getPrice());
                            cartData.setQuantitiy(mdata.getQuwantity());
                            cartData.setProductId(mdata.getProductID());
                            cartData.setPrOwnerID(mdata.getSellerID());
                            cartData.setTotal_selected_days(mdata.getTotal_selected_days());
                            cartData.setTotal(mdata.getTotal());
                            cartData.setOrderID(mdata.getOrderID());


                            List<ShowCartModel.Datum.ProductDetail> pDetailList = mdata.getProductDetail();

                            if (pDetailList.size() > 0 && pDetailList != null) {
                                for (ShowCartModel.Datum.ProductDetail pData : pDetailList) {

                                    cartData.setProductName(pData.getProduct());
                                    cartData.setDescription(pData.getDescription());
                                    cartData.setDeliverIn(pData.getDeliverIn());
                                    cartData.setPriceType(pData.getPrice_type());
                                    cartData.setDeliveryType(pData.getDelivery_type());

                                    List<ShowCartModel.Datum.ProductDetail.Image> pImage = pData.getImage();

                                    ShowCartModel.Datum.ProductDetail.Currency currency = pData.getCurrency();
                                    if (currency != null) {
                                        cartData.setCurrencySymbol(currency.getSymbol());
                                    }

                                    if (pImage.size() > 0) {
                                        for (int i = 0; i < pImage.size(); i++) {
                                            cartData.setImage(pImage.get(0).getImage());
                                            cartData.setPath(pImage.get(0).getPath());

                                        }
                                    }

                                }

                                cartDataList.add(cartData);
                            }


                        }
                        cartAdapter = new CartAdapter(cartDataList, getActivity());
                        cartRecycler.setAdapter(cartAdapter);
                    } else {
                        rel_emptycart.setVisibility(View.VISIBLE);
                        cardBottom.setVisibility(View.GONE);
                        totalAmountCart.setVisibility(View.GONE);
                        dialogInterface.hideDialog();
                    }


                } else {
                    dialogInterface.hideDialog();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ShowCartModel> call, @NonNull Throwable t) {
                dialogInterface.hideDialog();
            }
        });
    }


}