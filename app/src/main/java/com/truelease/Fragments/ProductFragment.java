package com.truelease.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.truelease.Activities.AddProductStepOneActivity;
import com.truelease.Activities.LoginSignupActivity;
import com.truelease.Adapters.ShowProductAdapter;
import com.truelease.Adapters.SubCategoryAdapter;
import com.truelease.ApiData.APIClient;
import com.truelease.Interface.LoginDetail.LoginDetail;
import com.truelease.Interface.LoginDetail.UserLoginDetail;
import com.truelease.Model.ProductData;
import com.truelease.Model.SubcategoryData;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.MyDialog.DialogInterface;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.R;
import com.truelease.RetrofitModel.ShowProductsModel;
import com.truelease.RetrofitModel.SubCategoriesModel;
import com.truelease.User.UserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductFragment extends Fragment {

    RecyclerView subCategortrecycler, showProductrecycler;
    List<SubcategoryData> subcategoryDataList;
    SubCategoryAdapter adapter;
    FloatingActionButton fab;
    RelativeLayout rel;
    ShowProductAdapter showProductAdapter;
    List<ProductData> productDataList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        subCategortrecycler = view.findViewById(R.id.subCategortrecycler);
        showProductrecycler = view.findViewById(R.id.showProductrecycler);
        rel = view.findViewById(R.id.rel);
        fab = view.findViewById(R.id.fab);


        subCategortrecycler.setHasFixedSize(true);
        subCategortrecycler.setItemViewCacheSize(20);
        subCategortrecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        showProductrecycler.setHasFixedSize(true);
        showProductrecycler.setItemViewCacheSize(20);
        showProductrecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        showSubCategory();


        fab.setOnClickListener(view1 -> {

            UserLoginDetail userLoginDetail = new LoginDetail(UserData.getUserID(getActivity()));

            if (userLoginDetail.isUserLogin()) {
                startActivity(new Intent(getActivity(), AddProductStepOneActivity.class));

            } else {
                Toast.makeText(getActivity(), "Login First", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), LoginSignupActivity.class));
                getActivity().finishAffinity();

            }

        });


        onBack(view);

        return view;
    }


    public void onBack(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {


                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();


                    return true;
                }
                return false;
            }
        });

    }


    public void showSubCategory() {


        final DialogInterface dialogI = new CustomDialog();
        dialogI.showDialog(R.layout.pr_dialog, getActivity());

        Map<String, String> maps = new HashMap<>();
        maps.put("control", "sub category");
        maps.put("categoryID", UserData.getCategoryID(getActivity()));
        Call<SubCategoriesModel> call = APIClient.getAPIClient().showSubCategory(maps);
        call.enqueue(new Callback<SubCategoriesModel>() {
            @Override
            public void onResponse(@NonNull Call<SubCategoriesModel> call, @NonNull Response<SubCategoriesModel> response) {
                if (!response.isSuccessful()) {
                    dialogI.hideDialog();
                    ReturnErrorToast.showToast(getActivity());

                }
                SubCategoriesModel categoriesModel = response.body();

                String catId = "", subCatID = "";

                if (categoriesModel != null) {

                    if (categoriesModel.getResult()) {

                        subcategoryDataList = new ArrayList<>();
                        List<SubCategoriesModel.Datum> dataList = categoriesModel.getData();

                        if (dataList != null || dataList.size() > 0) {

                            for (SubCategoriesModel.Datum data : dataList) {
                                SubcategoryData subcategoryData = new SubcategoryData();
                                subcategoryData.setSub_categoryID(data.getSubCategoryID());
                                subcategoryData.setCategoryID(data.getCategoryID());
                                subcategoryData.setName(data.getName());
                                subcategoryData.setImage(data.getImage());
                                subcategoryData.setPath(data.getPath());
                                catId = dataList.get(0).getCategoryID();
                                subCatID = dataList.get(0).getSubCategoryID();

                                subcategoryDataList.add(subcategoryData);


                            }


                            showProduct(catId, subCatID, UserData.getCityID(getActivity()));


                        }


                        dialogI.hideDialog();
                        adapter = new SubCategoryAdapter(subcategoryDataList, getActivity(), ProductFragment.this);
                        subCategortrecycler.setAdapter(adapter);


                    } else {
                        dialogI.hideDialog();
                        Toast.makeText(getActivity(), categoriesModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<SubCategoriesModel> call, @NonNull Throwable t) {

                dialogI.hideDialog();
            }
        });

    }


    public void showProduct(String catID, String subCatID, String cityID) {

        Map<String, String> maps = new HashMap<>();
        maps.put("categoryID", catID);
        maps.put("sub_categoryID", subCatID);
        maps.put("cityID", cityID);

        Call<ShowProductsModel> call = APIClient.getAPIClient().showProduct(maps);
        call.enqueue(new Callback<ShowProductsModel>() {
            @Override
            public void onResponse(@NonNull Call<ShowProductsModel> call, @NonNull Response<ShowProductsModel> response) {
                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(getActivity());
                }
                ShowProductsModel productsModel = response.body();
                if (productsModel != null) {

                    if (productsModel.getResult()) {

                        List<ShowProductsModel.Datum> dataList = productsModel.getData();


                        if (dataList.size() > 0) {

                            productDataList = new ArrayList<>();

                            for (ShowProductsModel.Datum data : dataList) {

                                ProductData productData = new ProductData();

                                productData.setProId(data.getProductID());
                                productData.setProName(data.getProduct());
                                productData.setProPrice(data.getMarketPrice());
                                productData.setCategoryID(data.getCategoryID());
                                productData.setSubCategoryID(data.getSubCategoryID());
                                productData.setRentPerMonth(data.getRentPerMonth());
                                productData.setRentStatus(data.getRent_status());


                                if (data.getCurrencyDetail() != null) {

                                    for (ShowProductsModel.Datum.CurrencyDetail currencyDetail : data.getCurrencyDetail()) {
                                        productData.setCurrencySymbol(currencyDetail.getSymbol());
                                    }

                                }

                                List<ShowProductsModel.Datum.Image> datanew = data.getImage();

                                if (datanew.size() > 0) {
                                    for (int i = 0; i < 1; i++) {
                                        productData.setProImage(datanew.get(0).getImage());
                                        productData.setProPath(datanew.get(0).getPath());

                                    }
                                }

                                List<ShowProductsModel.Datum.CurrencyDetail> currencyDetailList = data.getCurrencyDetail();


                                if (currencyDetailList != null) {


                                }


                                rel.setVisibility(View.GONE);
                                productDataList.add(productData);

                            }

                        } else {
                            rel.setVisibility(View.VISIBLE);
                        }

                        showProductAdapter = new ShowProductAdapter(productDataList, getActivity());
                        showProductrecycler.setAdapter(showProductAdapter);
                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(@NonNull Call<ShowProductsModel> call, @NonNull Throwable t) {

            }
        });


    }
}