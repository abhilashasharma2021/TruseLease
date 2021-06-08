package com.truelease.Activities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.truelease.Adapters.ImageAdapter;
import com.truelease.ApiData.API;
import com.truelease.R;
import com.truelease.User.UserData;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddImagesActivity extends AppCompatActivity {


    private static final int REQUEST_READ_EXTERNAL_STORAGE = 6766;
    RecyclerView recycler;
    ImageView addImages, back;
    private final int FILE_REQUEST_CODE = 7777;
    String filePath = "";
    ArrayList<File> fileList = new ArrayList<>();
    List<File> finalList;
    ImageAdapter adapter;
    Button btnSubmit;
    ProgressDialog dialog;
    boolean isPermissionApproved = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_images);

        recycler = findViewById(R.id.recycler);
        addImages = findViewById(R.id.addImages);
        btnSubmit = findViewById(R.id.btnSubmit);
        back = findViewById(R.id.back);

        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new ImageAdapter(fileList, AddImagesActivity.this);

        back.setOnClickListener(view -> finish());

        isPermissionApproved();


        Log.e("nxsjkdn", "//////////////////First step/////////////////////////////");

        Log.e("nxsjkdn", UserData.getAddProductTitle(getApplicationContext()));
        Log.e("nxsjkdn", UserData.getAddProductDesc(getApplicationContext()));
        Log.e("nxsjkdn", UserData.getAddProductAddress(getApplicationContext()));
        Log.e("nxsjkdn", UserData.getAddProductCategoryId(getApplicationContext()));
        Log.e("nxsjkdn", UserData.getAddProductSubCatId(getApplicationContext()));
        Log.e("nxsjkdn", UserData.getAddProductCondition(getApplicationContext()));


        Log.e("nxsjkdn", "//////////////////Second step/////////////////////////////");


        Log.e("nxsjkdn", UserData.getAddProductPrice(getApplicationContext()));
        Log.e("nxsjkdn", UserData.getAddProductDeposit(getApplicationContext()));
        Log.e("nxsjkdn", UserData.getAddProductMarketPrice(getApplicationContext()));
        Log.e("nxsjkdn", UserData.getAddProductMaxPeriod(getApplicationContext()));
        Log.e("nxsjkdn", UserData.getAddProductMinPeriod(getApplicationContext()));

        Log.e("nxsjkdn", "//////////////////Third step/////////////////////////////");

        Log.e("nxsjkdn", UserData.getAddProductBrand(getApplicationContext()));
        Log.e("nxsjkdn", UserData.getAddProductPower(getApplicationContext()));
        Log.e("nxsjkdn", UserData.getAddProductWeight(getApplicationContext()));
        Log.e("nxsjkdn", UserData.getAddProductColor(getApplicationContext()));
        Log.e("nxsjkdn", UserData.getAddProductHeight(getApplicationContext()));
        Log.e("nxsjkdn", UserData.getAddProductWidth(getApplicationContext()));
        Log.e("nxsjkdn", UserData.getAddProductLength(getApplicationContext()));
        Log.e("nxsjkdn", UserData.getAddProductLatitude(getApplicationContext()));
        Log.e("nxsjkdn", UserData.getAddProductLongitude(getApplicationContext()));


        Log.e("nxsjkdn", "//////////////////Fourth step/////////////////////////////");


        Log.e("nxsjkdn", UserData.getAddDate(getApplicationContext()));
        Log.e("nxsjkdn", UserData.getAddTime(getApplicationContext()));
        Log.e("nxsjkdn", UserData.getAddDeliveryPrice(getApplicationContext()));


        addImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isPermissionApproved){
                    Intent intent = new Intent(AddImagesActivity.this, FilePickerActivity.class);
                    intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                            .setCheckPermission(true)
                            .setShowImages(true)
                            .setShowVideos(false)
                            .enableImageCapture(true)
                            .setMaxSelection(8)
                            .setSkipZeroSizeFiles(true)
                            .build());
                    startActivityForResult(intent, FILE_REQUEST_CODE);
                }else {
                    Toast.makeText(AddImagesActivity.this, "Allow image permission from the app setting", Toast.LENGTH_SHORT).show();
                }


            }
        });


        btnSubmit.setOnClickListener(view -> {

            if (adapter.getItemCount() < 4) {
                Toast.makeText(AddImagesActivity.this, "please select atleast 4 images", Toast.LENGTH_SHORT).show();
            } else if (adapter.getItemCount() > 8) {
                Toast.makeText(this, "8 images only", Toast.LENGTH_SHORT).show();
            } else {


                finalList = adapter.getImageList();


                for (int i = 0; i < finalList.size(); i++) {
                    Log.e("imgPath", fileList.get(i).getAbsolutePath());
                }

                Log.e("nxsjkdn", UserData.getAddProductBrand(getApplicationContext()));
                Log.e("nxsjkdn", UserData.getAddProductPower(getApplicationContext()));
                Log.e("nxsjkdn", UserData.getAddProductWeight(getApplicationContext()));
                Log.e("nxsjkdn", UserData.getAddProductColor(getApplicationContext()));
                Log.e("nxsjkdn", UserData.getAddProductHeight(getApplicationContext()));
                Log.e("nxsjkdn", UserData.getAddProductWidth(getApplicationContext()));
                Log.e("nxsjkdn", UserData.getAddProductLength(getApplicationContext()));
                Log.e("nxsjkdn", UserData.getAddProductLatitude(getApplicationContext()));
                Log.e("nxsjkdn", UserData.getAddProductLongitude(getApplicationContext()));


            /*    list, userID, categoryID, sub_categoryID, cityID, product, amount, tags, description,
                        address, lat, lng, power, length, width, height, brandID, color, condition, max_period,
                        min_period, deposit, marketPrice, availability_date, availability_time, currency_code_id,
                        delivery_type, weight*/

                confirmDialog(finalList,
                        UserData.getUserID(getApplicationContext()),
                        UserData.getAddProductCategoryId(getApplicationContext()),
                        UserData.getAddProductSubCatId(getApplicationContext()),
                        UserData.getCityID(getApplicationContext()),
                        UserData.getAddProductTitle(getApplicationContext()),
                        UserData.getAddProductPrice(getApplicationContext()),
                        UserData.getAddProductTag(getApplicationContext()),
                        UserData.getAddProductDesc(getApplicationContext()),
                        UserData.getAddProductAddress(getApplicationContext()),
                        UserData.getAddProductLatitude(getApplicationContext()),
                        UserData.getAddProductLongitude(getApplicationContext()),
                        UserData.getAddProductPower(getApplicationContext()),
                        UserData.getAddProductLength(getApplicationContext()),
                        UserData.getAddProductWidth(getApplicationContext()),
                        UserData.getAddProductHeight(getApplicationContext()),
                        UserData.getAddProductBrand(getApplicationContext()),
                        UserData.getAddProductColor(getApplicationContext()),
                        "2",
                        UserData.getAddProductMaxPeriod(getApplicationContext()),
                        UserData.getAddProductMinPeriod(getApplicationContext()),
                        UserData.getAddProductDeposit(getApplicationContext()),
                        UserData.getAddProductMarketPrice(getApplicationContext()),
                        UserData.getAddDate(getApplicationContext()),
                        UserData.getAddTime(getApplicationContext()),
                        UserData.getAddCurrencyCode(getApplicationContext()),
                        UserData.getAddDeliveryType(getApplicationContext()),
                        UserData.getAddProductWeight(getApplicationContext()),
                        UserData.getAddDurationOfProduct(getApplicationContext())
                );
            }
        });


    }


    public void isPermissionApproved() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            isPermissionApproved = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_READ_EXTERNAL_STORAGE);
        }
    }


    public void confirmDialog(List<File> list, String userID, String categoryID, String sub_categoryID, String cityID,
                              String product, String amount, String tags, String description, String address,
                              String lat, String lng, String power, String length, String width,
                              String height, String brandID, String color, String condition,
                              String max_period, String min_period, String deposit, String marketPrice,
                              String availability_date, String availability_time, String currency_code_id,
                              String delivery_type, String weight, String price_type) {

        final Dialog dialog = new Dialog(AddImagesActivity.this);
        dialog.setContentView(R.layout.sure_dialog);
        RelativeLayout relYes = dialog.findViewById(R.id.relYes);
        RelativeLayout relNo = dialog.findViewById(R.id.relNo);

        relYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                addProduct(list, userID, categoryID, sub_categoryID, cityID, product, amount, tags, description,
                        address, lat, lng, power, length, width, height, brandID, color, condition, max_period,
                        min_period, deposit, marketPrice, availability_date, availability_time, currency_code_id,
                        delivery_type, weight, price_type);
                dialog.dismiss();


            }
        });


        relNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {
            Log.e("sxscxxs", e.getMessage(), e);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            List<MediaFile> mediaFiles = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);

            if (mediaFiles != null) {

                for (int i = 0; i < mediaFiles.size(); i++) {

                    filePath = mediaFiles.get(i).getPath();

                    File file = new File(filePath);
                    fileList.add(file);


                }

                adapter = new ImageAdapter(fileList, AddImagesActivity.this);

                recycler.setAdapter(adapter);


            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        isPermissionApproved = false;
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            isPermissionApproved = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void addProduct(List<File> list, String userID, String categoryID, String sub_categoryID, String cityID, String product,
                           String amount, String tags, String description, String address,
                           String lat, String lng, String power, String length, String width,
                           String height, String brandID, String color, String condition,
                           String max_period, String min_period, String deposit, String marketPrice,
                           String availability_date, String availability_time, String currency_code_id,
                           String delivery_type, String weight, String price_type) {


        dialog = new ProgressDialog(this);
        dialog.setTitle("Uploading product");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);


        AndroidNetworking.upload(API.BASE_URL + API.addProduct)
                .addMultipartFileList("image[]", list)
                .addMultipartParameter("userID", userID)
                .addMultipartParameter("categoryID", categoryID)
                .addMultipartParameter("cityID", cityID)
                .addMultipartParameter("sub_categoryID", sub_categoryID)
                .addMultipartParameter("product", product)
                .addMultipartParameter("amount", amount)
                .addMultipartParameter("price_type", "1")
                .addMultipartParameter("tags", tags)
                .addMultipartParameter("address_of_product", address)
                .addMultipartParameter("description", description)
                .addMultipartParameter("lat", lat)
                .addMultipartParameter("long", lng)
                .addMultipartParameter("power", power)
                .addMultipartParameter("length", length)
                .addMultipartParameter("height", height)
                .addMultipartParameter("width", width)
                .addMultipartParameter("brandID", brandID)
                .addMultipartParameter("color", color)
                .addMultipartParameter("condition", condition)
                .addMultipartParameter("min_period", min_period)
                .addMultipartParameter("max_period", max_period)
                .addMultipartParameter("deposit", deposit)
                .addMultipartParameter("market_price", marketPrice)
                .addMultipartParameter("availability_date", availability_date)
                .addMultipartParameter("availability_time", availability_time)
                .addMultipartParameter("currency_code_id", currency_code_id)
                .addMultipartParameter("delivery_type", delivery_type)
                .addMultipartParameter("weight", weight)
                .addMultipartParameter("price_type", price_type)
                .setTag("addPost")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {

                        double p = ((bytesUploaded / (float) totalBytes) * 100);
                        dialog.setProgress((int) p);
                        dialog.setMessage("please wait...." + Math.round(p) + " %");
                        try {
                            dialog.show();
                        } catch (Exception e) {
                            dialog.dismiss();
                        }

                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            if (response.has("result")) {

                                String strresult = response.getString("result");
                                String message = response.getString("message");
                                if (strresult.equals("true")) {
                                    startActivity(new Intent(getApplicationContext(), BottomNavigationActivity.class));
                                    finishAffinity();
                                    dialog.dismiss();
                                    Toast.makeText(AddImagesActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();

                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(AddImagesActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                dialog.dismiss();
                                Toast.makeText(AddImagesActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception e) {

                            dialog.dismiss();
                            Log.e("iwueiuwe", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dialog.dismiss();
                        Log.e("iwueiuwe", anError.getMessage());
                    }
                });

    }


}