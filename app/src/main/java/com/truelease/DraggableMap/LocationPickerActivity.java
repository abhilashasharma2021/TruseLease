package com.truelease.DraggableMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.truelease.Activities.AddProductStepFourthActivity;
import com.truelease.Others.AppConstats;
import com.truelease.Others.SharedHelper;
import com.truelease.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationPickerActivity extends AppCompatActivity implements MapWrapperLayout.OnDragListener, OnMapReadyCallback {

    private GoogleMap googleMap;
    TextView txtAddress;
    TextView txtLocalAddress;
    Button btnConfirmLocation;
    String strAccurateAdd = "";
    GPSTracker gpsTracker;
    LatLng s_latLng;
    String address = "";
    String lat = "", lng = "";
    public static String FULL_ADDRESS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_picker);
        txtLocalAddress = findViewById(R.id.txtLocalAddress);
        txtAddress = findViewById(R.id.txtAddress);
        btnConfirmLocation = findViewById(R.id.btnConfirmLocation);
        ImageView backImage = findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView imgCurrentLocation = findViewById(R.id.imgCurrentLocation);
        imgCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AnimateCamera();
            }
        });


        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        gpsTracker = new GPSTracker(this);
        if (gpsTracker.canGetLocation()) {
            initilizeMap();

        } else {

            gpsTracker.showSettingsAlert();
        }

    }


    private void initilizeMap() {
        if (googleMap == null) {

            CustomMapFragment supportMapFragment = (CustomMapFragment)
                    getFragmentManager().findFragmentById(R.id.map);
            supportMapFragment.setOnDragListener(LocationPickerActivity.this);
            supportMapFragment.getMapAsync(this);

            if (googleMap == null) {

            }
        }
    }


    @Override
    public void onMapReady(GoogleMap map) {

        googleMap = map;

        gpsTracker = new GPSTracker(this);
        if (gpsTracker.canGetLocation()) {
            s_latLng = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
            updateLocation(s_latLng);
            AnimateCamera();

        } else {

            gpsTracker.showSettingsAlert();
        }


    }


    @Override
    public void onDrag(MotionEvent motionEvent) {

        Log.e("uyttttuy", "DRAG");
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            Projection projection = googleMap != null ? googleMap.getProjection() : null;
            if (projection != null) {
                int centerX = -1;
                int centerY = -1;
                final LatLng centerLatLng = projection.fromScreenLocation(new Point(
                        centerX, centerY));

                updateLocation(centerLatLng);

            }
        }
    }


    private void updateLocation(final LatLng centerLatLng) {
        strAccurateAdd = "";
        if (centerLatLng != null) {

            try {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());

                addresses = geocoder.getFromLocation(centerLatLng.latitude, centerLatLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                if (addresses.size() != 0) {

                    address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    final String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String knownName = addresses.get(0).getFeatureName();
                    final String pincode = addresses.get(0).getPostalCode();


                    String strAdd[] = address.split(",");

                    for (int i = 0; i < strAdd.length; i++) {

                        Log.e("fgdgdfgdfgf", i + " " + strAdd[i]);
                        if (i != 0) {

                            if (strAccurateAdd.equals("")) {

                                strAccurateAdd = strAdd[i];
                            } else {
                                strAccurateAdd = strAccurateAdd + "," + strAdd[i];
                            }
                        }

                    }


                    // txtLocalAddress.setText(knownName);
                    txtLocalAddress.setText(city);
                    txtAddress.setText(strAccurateAdd);
                    lat = String.valueOf(centerLatLng.latitude);
                    lng = String.valueOf(centerLatLng.longitude);


                    btnConfirmLocation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent();
                            intent.putExtra(FULL_ADDRESS,strAccurateAdd);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });

                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public void AnimateCamera() {
        GPSTracker gpsTracker = new GPSTracker(this);

        if (gpsTracker.canGetLocation()) {

            try {
                LatLng latLngs = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(getCameraPositionWithBearing(latLngs)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            gpsTracker.showSettingsAlert();
        }

    }

    @NonNull
    private CameraPosition getCameraPositionWithBearing(LatLng latLng) {
        return new CameraPosition.Builder().target(latLng).zoom(16).build();
    }


}