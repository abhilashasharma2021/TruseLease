package com.truelease.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.truelease.Others.AppConstats;
import com.truelease.Others.InternetConnection.InternetConnectionInterface;
import com.truelease.Others.InternetConnection.InternetConnectivity;
import com.truelease.Others.SharedHelper;
import com.truelease.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler(Looper.myLooper());
    String userID = "";
    RelativeLayout relative;
    Button click;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(MainActivity.this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_main);

        relative = findViewById(R.id.relative);
        click = findViewById(R.id.click);



        Intent intent2 = null;
        try {
            intent2 = getIntent();
            String action = intent2.getAction();
            String type = intent2.getType();


            if (Intent.ACTION_SEND.equals(action) && type != null) {
                if ("text/plain".equals(type)) {

                    handleSendText(intent2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }






        displayLocationSettingsRequest(getApplicationContext());

        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                ).withListener(new MultiplePermissionsListener() {


            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                handler.postDelayed(() -> {

                    InternetConnectionInterface connectivity = new InternetConnectivity();
                    if (connectivity.isConnected(getApplicationContext())) {


                        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                String token = task.getResult();
                                Log.e("lskjdksj", "token : " + token);
                                SharedHelper.putKey(MainActivity.this, AppConstats.REG_ID, token);
                            } else {
                                Toast.makeText(MainActivity.this, "Token generation failed", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(e -> Log.e("lskjdksj", e.getMessage()));


                        userID = SharedHelper.getKey(getApplicationContext(), AppConstats.USER_ID);
                        if (userID.equals("")) {
                            startActivity(new Intent(getApplicationContext(), LoginSignupActivity.class));
                            finish();
                        } else {
                            startActivity(new Intent(getApplicationContext(), BottomNavigationActivity.class));
                            finish();
                        }
                    } else {
                        relative.setVisibility(View.VISIBLE);

                        click.setOnClickListener(view -> {
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                            Animatoo.animateShrink(MainActivity.this);
                            finish();
                        });

                        Toast.makeText(MainActivity.this, "Not Connected to internet", Toast.LENGTH_SHORT).show();
                    }


                }, 1500);


            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();


    }



    void handleSendText(Intent intent) {
        String senderid = intent.getStringExtra(Intent.EXTRA_SUBJECT);
        String eventid = intent.getStringExtra(Intent.EXTRA_REFERRER);
        Log.e("safaddvdda", senderid);
        Log.e("safaddvdda", eventid);


    }


    private void displayLocationSettingsRequest(Context context) {

        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i("fkdvkf", "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i("fkdvkf", "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(MainActivity.this, 1);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i("fkdvkf", "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i("fkdvkf", "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }
}