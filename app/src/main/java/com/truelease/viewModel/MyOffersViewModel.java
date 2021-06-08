package com.truelease.viewModel;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.truelease.ApiData.APIClient;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.MyDialog.DialogInterface;
import com.truelease.R;
import com.truelease.RetrofitModel.OffersModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOffersViewModel extends ViewModel {

    private String offerID = "";
    private String endDate = "";
    private String offerImg = "";
    private String offerPath = "";
    private String offerDiscount = "";
    private String offerDiscription = "";

    public MutableLiveData<List<MyOffersViewModel>> mutableLiveDataList = new MutableLiveData<>();

    private List<MyOffersViewModel> myOffersViewModelList;

    public String getOfferID() {
        return offerID;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getOfferImg() {
        return offerImg;
    }

    public String getOfferPath() {
        return offerPath;
    }

    public String getOfferDiscount() {
        return offerDiscount;
    }

    public String getOfferDiscription() {
        return offerDiscription;
    }

    @BindingAdapter({"image"})
    public static void loadImage(ImageView imageView, String imgPath) {
        Glide.with(imageView.getContext()).load(imgPath).placeholder(R.drawable.logo2).into(imageView);
    }

    public MyOffersViewModel() {

    }

    public MyOffersViewModel(OffersModel.Datum data) {

        this.offerID = data.getOfferID();
        this.endDate = data.getOfferEndDate();
        this.offerImg = data.getImage();
        this.offerPath = data.getPath();
        this.offerDiscount = data.getDiscount();
        this.offerDiscription = data.getDescription();

    }


    public MutableLiveData<List<MyOffersViewModel>> getMutableLiveDataList(Context context) {
        final DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, context);
        Call<OffersModel> call = APIClient.getAPIClient().showOffers();
        call.enqueue(new Callback<OffersModel>() {
            @Override
            public void onResponse(@NonNull Call<OffersModel> call, @NonNull Response<OffersModel> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, response.code() + " Something went wrong", Toast.LENGTH_SHORT).show();
                }

                OffersModel offersModel = response.body();
                if (offersModel != null) {
                    if (offersModel.getResult()) {
                        myOffersViewModelList = new ArrayList<>();
                        List<OffersModel.Datum> dataList = offersModel.getData();
                        for (int i = 0; i < dataList.size(); i++) {
                            OffersModel.Datum data = dataList.get(i);
                            MyOffersViewModel viewModel = new MyOffersViewModel(data);
                            myOffersViewModelList.add(viewModel);
                            mutableLiveDataList.setValue(myOffersViewModelList);
                        }
                        dialogInterface.hideDialog();
                    } else {
                        dialogInterface.hideDialog();
                        Toast.makeText(context, offersModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    dialogInterface.hideDialog();

                }
            }

            @Override
            public void onFailure(@NonNull Call<OffersModel> call, @NonNull Throwable t) {
                dialogInterface.hideDialog();
            }
        });

        return mutableLiveDataList;
    }


}
