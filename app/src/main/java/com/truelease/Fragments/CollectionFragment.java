package com.truelease.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.truelease.Adapters.CollectionCategoryAdapter;
import com.truelease.Adapters.ShowProductAdapter;
import com.truelease.Adapters.SubCategoryAdapter;
import com.truelease.ApiData.APIClient;
import com.truelease.Model.ProductData;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.MyDialog.DialogInterface;
import com.truelease.R;
import com.truelease.RetrofitModel.CollectionCategoryModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CollectionFragment extends Fragment {

    RecyclerView collectionCategortrecycler, showProductrecycler;
    List<CollectionCategoryModel.Datum> collectionCategoryModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        collectionCategortrecycler = view.findViewById(R.id.collectionCategortrecycler);
        collectionCategortrecycler.setHasFixedSize(true);
        collectionCategortrecycler.setItemViewCacheSize(20);
        collectionCategortrecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        showCollCategory();
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

    public void showCollCategory() {

        final DialogInterface dialogI = new CustomDialog();
        dialogI.showDialog(R.layout.pr_dialog, getActivity());

        Call<CollectionCategoryModel> call = APIClient.getAPIClient().collectionCategory();
        call.enqueue(new Callback<CollectionCategoryModel>() {
            @Override
            public void onResponse(@NonNull Call<CollectionCategoryModel> call, @NonNull Response<CollectionCategoryModel> response) {
                if (!response.isSuccessful()) {
                    dialogI.hideDialog();
                    Toast.makeText(getActivity(), "Something went wrong!!", Toast.LENGTH_SHORT).show();
                }


                CollectionCategoryModel categoryModel = response.body();

                if (categoryModel != null) {

                    if (categoryModel.getResult()) {
                        dialogI.hideDialog();
                        collectionCategoryModelList = new ArrayList<>();
                        List<CollectionCategoryModel.Datum> datumList = categoryModel.getData();

                        collectionCategoryModelList.addAll(datumList);
                        collectionCategortrecycler.setAdapter(new CollectionCategoryAdapter(collectionCategoryModelList, getActivity()));

                    } else {
                        Log.e("ieeiew", categoryModel.getMessage());
                    }


                }
            }

            @Override
            public void onFailure(@NonNull Call<CollectionCategoryModel> call, @NonNull Throwable t) {
                Log.e("ieeiew", t.getMessage());
            }
        });
    }
}