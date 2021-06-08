package com.truelease.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.truelease.ApiData.API;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.R;
import com.truelease.User.UserData;

import org.json.JSONException;
import org.json.JSONObject;

public class DocumentsActivity extends AppCompatActivity {

    ImageView image1, image2, image3, image4,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        back = findViewById(R.id.back);
        back.setOnClickListener(v->finish());
        showDocs();

    }


    public void showDocs() {


        com.truelease.Others.MyDialog.DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, DocumentsActivity.this);

        AndroidNetworking.upload(API.BASE_URL + API.updateProfile)
                .addMultipartParameter("userID", UserData.getUserID(DocumentsActivity.this))
                .setTag("docs")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("ssjResponse::", response.toString());


                        try {
                            if (response.getString("result").equals("true")) {


                                dialogInterface.hideDialog();

                                String data = response.getString("data");
                                JSONObject jsonObject = new JSONObject(data);
                                String path = jsonObject.getString("path");
                                String verification_documents = jsonObject.getString("verification_documents");
                                String D_license_front = jsonObject.getString("D_license_front");
                                String D_license_back = jsonObject.getString("D_license_back");
                                String paspot = jsonObject.getString("paspot");
                                String adhar_card = jsonObject.getString("adhar_card");


                                Glide.with(DocumentsActivity.this).load(path + D_license_front).placeholder(R.drawable.logo).into(image1);
                                Glide.with(DocumentsActivity.this).load(path + D_license_back).placeholder(R.drawable.logo).into(image2);
                                Glide.with(DocumentsActivity.this).load(path + paspot).placeholder(R.drawable.logo).into(image3);
                                Glide.with(DocumentsActivity.this).load(path + adhar_card).placeholder(R.drawable.logo).into(image4);

                            } else {
                                dialogInterface.hideDialog();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialogInterface.hideDialog();
                            Log.e("lkskdl", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dialogInterface.hideDialog();
                        Log.e("lkskdl", anError.getMessage());
                    }
                });
    }

}