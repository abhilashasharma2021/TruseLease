package com.truelease.viewModel;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.truelease.ApiData.APIClient;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.MyDialog.DialogInterface;
import com.truelease.R;
import com.truelease.RetrofitModel.ShowCategoriesData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCategoriesViewModel extends ViewModel {

    public String id = "";
    public String categoryName = "";
    public String categoryImage = "";
    public String categoryPath = "";

    public MutableLiveData<List<MyCategoriesViewModel>> mutableLiveData = new MutableLiveData<>();
    private List<MyCategoriesViewModel> arrayList;


    MutableLiveData<String> stringMutableLiveData = new MutableLiveData<>();

    public String getId() {
        return this.id;
    }

    public String getImage() {
        return this.categoryImage;
    }


    public String getCategoryName() {
        return this.categoryName;
    }

    @BindingAdapter({"imageUrl"})
    public static void loadimage(ImageView imageView, String path) {
        Glide.with(imageView.getContext()).load(path)
                .placeholder(R.drawable.logo2).override(350, 350)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public MyCategoriesViewModel() {

    }

    public MyCategoriesViewModel(ShowCategoriesData.Datum datum) {

        this.id = datum.getCategoryID();
        this.categoryName = datum.getName();
        this.categoryImage = datum.getImage();
        this.categoryPath = datum.getPath();

    }


    public MutableLiveData<List<MyCategoriesViewModel>> getMutableLiveData(final Context context) {

        final DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, context);

        Call<ShowCategoriesData> call = APIClient.getAPIClient().showCategories();
        call.enqueue(new Callback<ShowCategoriesData>() {
            @Override
            public void onResponse(@NonNull Call<ShowCategoriesData> call, @NonNull Response<ShowCategoriesData> response) {

                if (!response.isSuccessful()) {
                    dialogInterface.hideDialog();
                    Toast.makeText(context, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                }

                ShowCategoriesData categoriesData = response.body();

                if (categoriesData != null) {
                    if (categoriesData.getResult()) {
                        arrayList = new ArrayList<>();
                        List<ShowCategoriesData.Datum> list = categoriesData.getData();
                        for (int i = 0; i < list.size(); i++) {
                            ShowCategoriesData.Datum data = list.get(i);
                            MyCategoriesViewModel myListViewModel = new MyCategoriesViewModel(data);
                            arrayList.add(myListViewModel);
                            mutableLiveData.setValue(arrayList);
                            
                        }
                        dialogInterface.hideDialog();
                    } else {
                        dialogInterface.hideDialog();
                        Toast.makeText(context, categoriesData.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<ShowCategoriesData> call, @NonNull Throwable t) {
                dialogInterface.hideDialog();
            }
        });

        return mutableLiveData;
    }
}
