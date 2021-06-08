package com.truelease.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.truelease.Adapters.MyPostAdapter;
import com.truelease.ApiData.APIClient;
import com.truelease.Model.MyPostData;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.MyDialog.DialogInterface;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.R;
import com.truelease.RetrofitModel.MyPostModel;
import com.truelease.User.UserData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPostsActivity extends AppCompatActivity {

    RecyclerView myPostRecycler;
    List<MyPostData> myPostDataList;
    MyPostAdapter adapter;
    ImageView back;
    TextView noPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);

        myPostRecycler = findViewById(R.id.myPostRecycler);
        noPosts = findViewById(R.id.noPosts);
        back = findViewById(R.id.back);
        myPostRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        myPostRecycler.setHasFixedSize(true);
        myPostRecycler.setItemViewCacheSize(20);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        showMyPost();
    }


    public void showMyPost() {

        DialogInterface dialog = new CustomDialog();
        dialog.showDialog(R.layout.pr_dialog, MyPostsActivity.this);

        Call<MyPostModel> call = APIClient.getAPIClient().showPosts(UserData.getUserID(MyPostsActivity.this));
        call.enqueue(new Callback<MyPostModel>() {
            @Override
            public void onResponse(@NonNull Call<MyPostModel> call, @NonNull Response<MyPostModel> response) {


                if (!response.isSuccessful()) {

                    ReturnErrorToast.showToast(MyPostsActivity.this);
                    dialog.hideDialog();
                }

                MyPostModel model = response.body();
                if (model != null) {


                    if (model.getResult()) {

                        List<MyPostModel.Datum> dataList = model.getData();
                        dialog.hideDialog();

                        if (dataList.size() > 0) {

                            myPostDataList = new ArrayList<>();


                            for (MyPostModel.Datum data : dataList) {

                                MyPostData myPostData = new MyPostData();
                                myPostData.setProductID(data.getProductID());
                                myPostData.setProductName(data.getProduct());
                                myPostData.setRent_per_month(data.getRentPerMonth());
                                MyPostModel.Datum.UserInfo userInfo = data.getUserInfo();

                                List<MyPostModel.Datum.Image> imageList = data.getImage();

                                if (imageList.size() > 0) {

                                    for (MyPostModel.Datum.Image image : imageList) {

                                        myPostData.setImage(imageList.get(0).getImage());
                                        myPostData.setPath(imageList.get(0).getPath());

                                    }
                                }

                                myPostDataList.add(myPostData);

                            }

                            adapter = new MyPostAdapter(myPostDataList, MyPostsActivity.this);
                            myPostRecycler.setAdapter(adapter);
                            noPosts.setVisibility(View.GONE);
                        }else {
                            noPosts.setVisibility(View.VISIBLE);
                        }

                    } else {
                        dialog.hideDialog();
                    }


                }
            }

            @Override
            public void onFailure(@NonNull Call<MyPostModel> call, @NonNull Throwable t) {
                dialog.hideDialog();
            }
        });
    }
}