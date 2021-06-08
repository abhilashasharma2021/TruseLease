package com.truelease.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.truelease.Adapters.ShowImageAdapter;
import com.truelease.ApiData.APIClient;
import com.truelease.Model.ShowImageData;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.MyDialog.DialogInterface;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.R;
import com.truelease.RetrofitModel.ShowProductsModel;
import com.truelease.User.UserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowImagesActivity extends AppCompatActivity {


    RecyclerView recyclerImage;
    ImageView back;
    List<ShowImageData> showImageDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_images);

        recyclerImage = findViewById(R.id.recyclerImage);
        back = findViewById(R.id.back);
        recyclerImage.setItemViewCacheSize(20);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerImage.setLayoutManager(gridLayoutManager);

        back.setOnClickListener(v->finish());

        showProductDetail();
    }


    public void showProductDetail() {
        DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, ShowImagesActivity.this);

        Log.e("wuewewuoied", UserData.getProductID(ShowImagesActivity.this));
        Log.e("wuewewuoied", UserData.getUserID(ShowImagesActivity.this));

        Map<String, String> maps = new HashMap<>();
        maps.put("productID", UserData.getProductID(ShowImagesActivity.this));
        maps.put("user_id", UserData.getUserID(ShowImagesActivity.this));
        Call<ShowProductsModel> call = APIClient.getAPIClient().showProductDetail(maps);
        call.enqueue(new Callback<ShowProductsModel>() {


            @Override
            public void onResponse(@NonNull Call<ShowProductsModel> call, @NonNull Response<ShowProductsModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(ShowImagesActivity.this);
                    dialogInterface.hideDialog();
                }
                ShowProductsModel productsModel = response.body();
                if (productsModel != null) {
                    if (productsModel.getResult()) {
                        showImageDataList = new ArrayList<>();
                        List<ShowProductsModel.Datum> dataList = productsModel.getData();
                        for (ShowProductsModel.Datum data : dataList) {


                            List<ShowProductsModel.Datum.Image> datanew = data.getImage();

                            if (datanew != null) {
                                for (ShowProductsModel.Datum.Image image : datanew) {
                                    ShowImageData showImageData = new ShowImageData();
                                    showImageData.setPath(image.getPath());
                                    showImageData.setImage(image.getImage());
                                    showImageDataList.add(showImageData);
                                }

                                recyclerImage.setAdapter(new ShowImageAdapter(showImageDataList, ShowImagesActivity.this));

                            }


                        }

                        dialogInterface.hideDialog();

                    } else {
                        dialogInterface.hideDialog();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ShowProductsModel> call, @NonNull Throwable t) {
                dialogInterface.hideDialog();
            }
        });


    }


}