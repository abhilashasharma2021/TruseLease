package com.truelease.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import com.squareup.picasso.Picasso;
import com.truelease.ApiData.APIClient;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.R;
import com.truelease.RetrofitModel.VerificationStatusModel;
import com.truelease.Room.RoomDatabase.AddressActivity;
import com.truelease.User.UserData;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {

    TextView name, cityName, email, updatePrf, meetupLocation, needHelp, inviteFriends, verify, ver;
    RelativeLayout rel_prf;
    CircleImageView prf;

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String message = intent.getStringExtra("message");
            String action = intent.getStringExtra("action");


            Log.e("wieoiwe", message + "");
            Log.e("wieoiwe", action + "");

            if (!message.isEmpty()) {

                showVerStatus();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        name = findViewById(R.id.name);
        cityName = findViewById(R.id.cityName);
        rel_prf = findViewById(R.id.rel_prf);
        email = findViewById(R.id.email);
        updatePrf = findViewById(R.id.updatePrf);
        meetupLocation = findViewById(R.id.meetupLocation);
        prf = findViewById(R.id.prf);
        needHelp = findViewById(R.id.needHelp);
        inviteFriends = findViewById(R.id.inviteFriends);
        verify = findViewById(R.id.verify);
        ver = findViewById(R.id.ver);

        name.setText(UserData.getUserName(getApplicationContext()));
        email.setText(UserData.getUserEmail(getApplicationContext()));


        if (!UserData.getUserPath(SettingsActivity.this).equals("") && !UserData.getUserImage(SettingsActivity.this).equals("")) {
            Picasso.get().load(UserData.getUserPath(SettingsActivity.this) + UserData.getUserImage(SettingsActivity.this)).placeholder(R.drawable.avatar).into(prf);

        }


        needHelp.setOnClickListener(v -> startActivity(new Intent(SettingsActivity.this, ChatWithAdminActivity.class)));
        inviteFriends.setOnClickListener(v -> startActivity(new Intent(SettingsActivity.this, ReferToFriendsActivity.class)));
        verify.setOnClickListener(v -> startActivity(new Intent(SettingsActivity.this, UploadDocumentActivity.class)));
        ver.setOnClickListener(v -> startActivity(new Intent(SettingsActivity.this, UploadDocumentActivity.class)));


        cityName.setText(UserData.getCityName(getApplicationContext()));

        showVerStatus();


        updatePrf.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsActivity.this, EditProfileActivity.class);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SettingsActivity.this, prf, ViewCompat.getTransitionName(prf));
            startActivity(intent, optionsCompat.toBundle());
        });

        meetupLocation.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AddressActivity.class)));


    }


    @Override
    protected void onResume() {
        super.onResume();

        showVerStatus();

        name.setText(UserData.getUserName(getApplicationContext()));
        email.setText(UserData.getUserEmail(getApplicationContext()));

        registerReceiver(mBroadcastReceiver, new IntentFilter("Check"));

        if (!UserData.getUserPath(SettingsActivity.this).equals("") && !UserData.getUserImage(SettingsActivity.this).equals("")) {
            Picasso.get().load(UserData.getUserPath(SettingsActivity.this) + UserData.getUserImage(SettingsActivity.this)).placeholder(R.drawable.avatar).into(prf);

        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBroadcastReceiver);
    }

    public void showVerStatus() {

        Log.e("xslkcxsl", "userID" + UserData.getUserID(getApplicationContext()));

        Call<VerificationStatusModel> call = APIClient.getAPIClient().showVerificationStatus(UserData.getUserID(SettingsActivity.this));
        call.enqueue(new Callback<VerificationStatusModel>() {
            @Override
            public void onResponse(@NonNull Call<VerificationStatusModel> call, @NonNull Response<VerificationStatusModel> response) {

                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(SettingsActivity.this);
                }

                VerificationStatusModel model = response.body();

                if (model != null) {

                    if (model.getResult()) {


                        Log.e("xslkcxsl", model.getProfileStatus());

                        if (model.getProfileStatus().equals("0")) {
                            verify.setText("Verification pending");
                            verify.setTextColor(Color.parseColor("#FF0000"));
                        } else if (model.getProfileStatus().equals("1")) {
                            verify.setText("Verified");
                            verify.setTextColor(Color.parseColor("#00FF00"));
                        } else {
                            verify.setText("Verification pending");
                            verify.setTextColor(Color.parseColor("#FF0000"));
                        }

                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<VerificationStatusModel> call, @NonNull Throwable t) {

            }
        });
    }
}