package com.truelease.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.mlsdev.animatedrv.AnimatedRecyclerView;
import com.truelease.Activities.BottomNavigationActivity;
import com.truelease.Activities.MapSearchProductsActivity;
import com.truelease.Activities.MyChatsActivity;
import com.truelease.Adapters.AwesomeItemAdapter;
import com.truelease.Adapters.ExploreProductAdapter;
import com.truelease.Adapters.NewItemsAdapter;
import com.truelease.ApiData.APIClient;
import com.truelease.Interface.LoginDetail.LoginDetail;
import com.truelease.Interface.LoginDetail.UserLoginDetail;
import com.truelease.Model.AwesomeItemData;
import com.truelease.Model.ExploreProductData;
import com.truelease.Model.NewItemsData;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.MyDialog.DialogInterface;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.R;
import com.truelease.RetrofitModel.AwesomeItemModel;
import com.truelease.RetrofitModel.NewItemsModel;
import com.truelease.RetrofitModel.ShowCategoriesData;
import com.truelease.RetrofitModel.ShowNotiStatusModel;
import com.truelease.User.UserData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    public static AnimatedRecyclerView exploreProductRecycler, ourServicesRecycler;
    List<ExploreProductData> exploreProductDataList;
    List<NewItemsData> newItemsDataList;
    List<AwesomeItemData> awesomeList;
    RecyclerView awesomeRecycler;

    ExploreProductAdapter adapter;
    NewItemsAdapter newItemsAdapter;
    RecyclerView.LayoutManager layoutManagerOfrecyclerView;
    RecyclerView.LayoutManager layoutManagerOfrecyclerView2;
    ImageView menu, dot, noti;
    MaterialCardView card;
    RelativeLayout rel;
    EditText etSearch;
    TextView item, txtCityName;
    RecyclerView.LayoutManager layoutManager;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        exploreProductRecycler = view.findViewById(R.id.exploreProductRecycler);
        ourServicesRecycler = view.findViewById(R.id.ourServicesRecycler);
        awesomeRecycler = view.findViewById(R.id.awesomeRecycler);
        menu = view.findViewById(R.id.menu);
        card = view.findViewById(R.id.card);
        rel = view.findViewById(R.id.rel);
        etSearch = view.findViewById(R.id.etSearch);
        item = view.findViewById(R.id.item);
        dot = view.findViewById(R.id.dot);
        awesomeRecycler = view.findViewById(R.id.awesomeRecycler);
        noti = view.findViewById(R.id.noti);
        txtCityName = view.findViewById(R.id.txtCityName);


        exploreProductRecycler.setHasFixedSize(true);
        ourServicesRecycler.setHasFixedSize(true);


        txtCityName.setText(UserData.getCityName(getActivity()));


        layoutManagerOfrecyclerView = new LinearLayoutManager(getActivity());
        layoutManagerOfrecyclerView2 = new LinearLayoutManager(getActivity());

        exploreProductRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        ourServicesRecycler.setLayoutManager(layoutManager);
        awesomeRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        ourServicesRecycler.setItemViewCacheSize(20);
        exploreProductRecycler.setItemViewCacheSize(20);
        awesomeRecycler.setItemViewCacheSize(20);


        Log.e("sodips", UserData.getUserID(getActivity()) + "");

        awesomeRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        exploreProductRecycler.scheduleLayoutAnimation();
        ourServicesRecycler.scheduleLayoutAnimation();


        LinearSnapHelper snapHelper = new LinearSnapHelper() {
            @Override
            public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
                View centerView = findSnapView(layoutManager);
                if (centerView == null)
                    return RecyclerView.NO_POSITION;

                int position = layoutManager.getPosition(centerView);
                int targetPosition = -1;
                if (layoutManager.canScrollHorizontally()) {
                    if (velocityX < 0) {
                        targetPosition = position - 1;
                    } else {
                        targetPosition = position + 1;
                    }
                }

                if (layoutManager.canScrollVertically()) {
                    if (velocityY < 0) {
                        targetPosition = position - 1;
                    } else {
                        targetPosition = position + 1;
                    }
                }

                final int firstItem = 0;
                final int lastItem = layoutManager.getItemCount() - 1;
                targetPosition = Math.min(lastItem, Math.max(targetPosition, firstItem));
                return targetPosition;
            }
        };
        snapHelper.attachToRecyclerView(ourServicesRecycler);


        noti.setOnClickListener(v -> startActivity(new Intent(getActivity(), MyChatsActivity.class)));


        menu.setOnClickListener(view1 -> BottomNavigationActivity.drawer.openDrawer(GravityCompat.START));


        rel.setOnClickListener(view12 -> startActivity(new Intent(getActivity(), MapSearchProductsActivity.class)));

        etSearch.setOnClickListener(view13 -> startActivity(new Intent(getActivity(), MapSearchProductsActivity.class)));

        exploreProducts();
        showNewItems();
        showAwesomeItem();

        for (String m : new String[]{"2021-01"}) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
            Date date = null;
            try {
                date = format.parse(m);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar c = Calendar.getInstance();
            if (date != null) {
                c.setTime(date);
            }

            int start = c.get(Calendar.WEEK_OF_MONTH);

            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DATE, -1);
            int end = c.get(Calendar.WEEK_OF_MONTH);

            Log.e("idfeif", format.format(c.getTime())
                    + ": " + (end - start + 1));
        }


        onBack(view);


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        UserLoginDetail loginDetail = new LoginDetail(UserData.getUserID(getActivity()));
        if (loginDetail.isUserLogin()) showNotiStatus();
    }

    //(tls error) TLS is a cryptographic protocol that provides end-to-end security of data sent between applications over the Internet.
    public void showAwesomeItem() {

        final DialogInterface dialogInterface = new CustomDialog();

        dialogInterface.showDialog(R.layout.pr_dialog, getActivity());


        Call<AwesomeItemModel> call = APIClient.getAPIClient().showAwesomeItems();
        call.enqueue(new Callback<AwesomeItemModel>() {
            @Override
            public void onResponse(@NonNull Call<AwesomeItemModel> call, @NonNull Response<AwesomeItemModel> response) {

                if (!response.isSuccessful()) {
                    dialogInterface.hideDialog();
                    ReturnErrorToast.showToast(getActivity());
                    DialogInterface serverErrorDialog = new CustomDialog();
                    serverErrorDialog.showDialog(R.layout.server_error_dialog, getActivity());
                }

                AwesomeItemModel model = response.body();
                if (model != null) {


                    if (model.getResult()) {
                        awesomeList = new ArrayList<>();

                        dialogInterface.hideDialog();
                        List<AwesomeItemModel.Datum> datalist = model.getData();

                        if (datalist.size() > 0) {

                            for (AwesomeItemModel.Datum data : datalist) {

                                AwesomeItemData awesomeItemData = new AwesomeItemData();

                                AwesomeItemModel.Datum.ProductDetail productDetail = data.getProductDetail();
                                List<AwesomeItemModel.Datum.ProductImage> productImage = data.getProductImage();

                                if (productDetail != null) {

                                    awesomeItemData.setProductId(productDetail.getProductID());
                                    awesomeItemData.setProductName(productDetail.getProduct());

                                } else {
                                    Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
                                }


                                if (productImage.size() > 0) {


                                    for (int i = 0; i < productImage.size(); i++) {
                                        awesomeItemData.setProductPath(productImage.get(0).getPath());
                                        awesomeItemData.setProductImage(productImage.get(0).getImage());
                                    }
                                }


                                awesomeList.add(awesomeItemData);
                            }


                            awesomeRecycler.setAdapter(new AwesomeItemAdapter(awesomeList, getActivity()));
                        }
                    } else {
                        dialogInterface.hideDialog();
                    }


                } else {
                    dialogInterface.hideDialog();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AwesomeItemModel> call, @NonNull Throwable t) {
                dialogInterface.hideDialog();
                Log.e("eryruiyd", t.getMessage() + "msg");
            }
        });

    }


    public void onBack(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {

            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Do you want to exit?");
                builder.setPositiveButton("Yes", (dialogInterface, i) -> getActivity().finishAffinity()).setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
            return false;
        });

    }


    public void exploreProducts() {

        final DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, getActivity());

        Call<ShowCategoriesData> call = APIClient.getAPIClient().showCategories();
        call.enqueue(new Callback<ShowCategoriesData>() {
            @Override
            public void onResponse(@NonNull Call<ShowCategoriesData> call, @NonNull Response<ShowCategoriesData> response) {
                if (!response.isSuccessful()) {
                    dialogInterface.hideDialog();
                    showToast();
                } else {
                    ShowCategoriesData categoriesData = response.body();

                    if (categoriesData != null) {
                        if (categoriesData.getResult()) {
                            exploreProductDataList = new ArrayList<>();
                            List<ShowCategoriesData.Datum> dataList = categoriesData.getData();
                            for (ShowCategoriesData.Datum data : dataList) {
                                ExploreProductData exploreProductData = new ExploreProductData();
                                exploreProductData.setCategoryId(data.getCategoryID());
                                exploreProductData.setCategoryName(data.getName());
                                exploreProductData.setCategoryImage(data.getImage());
                                exploreProductData.setCategoryPath(data.getPath());

                                exploreProductDataList.add(exploreProductData);
                            }
                            dialogInterface.hideDialog();

                            if (getActivity() != null) {
                                adapter = new ExploreProductAdapter(exploreProductDataList, getActivity());
                                exploreProductRecycler.setAdapter(adapter);
                            }

                        } else {
                            dialogInterface.hideDialog();
                            Toast.makeText(getActivity(), categoriesData.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<ShowCategoriesData> call, @NonNull Throwable t) {
                dialogInterface.hideDialog();
                Log.e("sjkjdlksdj", t.getMessage());
            }
        });
    }


    public void showToast() {
        LayoutInflater infl = getLayoutInflater();
        View layout = infl.inflate(R.layout.toast_layoyt, getActivity().findViewById(R.id.toast_layout_root));
        TextView text = layout.findViewById(R.id.text);
        text.setText(R.string.somethingwentwrong);
        Toast toast = new Toast(getActivity());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public void showNotiStatus() {

        Call<ShowNotiStatusModel> call = APIClient.getAPIClient().showNotiStatus(UserData.getUserID(getActivity()));

        call.enqueue(new Callback<ShowNotiStatusModel>() {
            @Override
            public void onResponse(@NonNull Call<ShowNotiStatusModel> call, @NonNull Response<ShowNotiStatusModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(getActivity());
                }


                ShowNotiStatusModel model = response.body();
                if (model != null) {

                    if (model.getAppStatus() != null) {
                        if (model.getAppStatus().equals("0")) {
                            dot.setVisibility(View.VISIBLE);
                        } else {
                            dot.setVisibility(View.GONE);
                        }
                    }


                }
            }

            @Override
            public void onFailure(@NonNull Call<ShowNotiStatusModel> call, @NonNull Throwable t) {

                Log.e("swiduowid", "error");
            }
        });
    }


    public void showNewItems() {

        Call<NewItemsModel> call = APIClient.getAPIClient().showNewItem();
        call.enqueue(new Callback<NewItemsModel>() {
            @Override
            public void onResponse(@NonNull Call<NewItemsModel> call, @NonNull Response<NewItemsModel> response) {
                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(getActivity());
                }
                NewItemsModel newItemsModel = response.body();
                if (newItemsModel != null) {

                    if (newItemsModel.getResult()) {
                        newItemsDataList = new ArrayList<>();
                        if (newItemsModel.getData().size() > 0) {

                            for (NewItemsModel.Datum dataList : newItemsModel.getData()) {

                                NewItemsModel.Datum.ProductImages productImages = dataList.getProductImages();
                                NewItemsData newItemsData = new NewItemsData();
                                newItemsData.setProductID(productImages.getProductID());
                                newItemsData.setImage(productImages.getImage());
                                newItemsData.setPath(dataList.getPath());

                                newItemsDataList.add(newItemsData);
                            }

                            if (getActivity() != null) {
                                newItemsAdapter = new NewItemsAdapter(newItemsDataList, getActivity());
                                ourServicesRecycler.setAdapter(newItemsAdapter);
                            }

                        }
                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<NewItemsModel> call, @NonNull Throwable t) {
                Log.e("swiduowid", "error");
            }
        });
    }


}