package com.truelease.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.truelease.Others.AppConstats;
import com.truelease.Others.BottomSheetSearchProduct;
import com.truelease.Others.SharedHelper;
import com.truelease.R;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MapSearchProductsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    public static GoogleMap map;
    ImageView back;
    CameraPosition cameraPosition;
    Location lastLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    boolean isLocationPermission = false;
    AutoCompleteTextView autoCompleteSearch;
    EditText iNeed;
    private static final int AUTOCOMPLETE_REQUEST_CODE_SEARCH = 1;
    Button btn;
    RelativeLayout relineed, relSearch, relGps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_search_products);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (savedInstanceState != null) {
            lastLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }


        back = findViewById(R.id.back);
        relGps = findViewById(R.id.relGps);
        relineed = findViewById(R.id.relineed);
        autoCompleteSearch = findViewById(R.id.autoCompleteSearch);
        btn = findViewById(R.id.btn);
        relSearch = findViewById(R.id.relSearch);
        iNeed = findViewById(R.id.iNeed);
        relGps = findViewById(R.id.relGps);
        back.setOnClickListener(view -> finish());


        if (!Places.isInitialized()) {
            Places.initialize(MapSearchProductsActivity.this, getString(R.string.api_key), Locale.getDefault());
        }


        autoCompleteSearch.setOnClickListener(v -> {

            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(MapSearchProductsActivity.this);
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE_SEARCH);

        });

        relSearch.setOnClickListener(view -> {

            if (iNeed.getText().toString().trim().equals("")) {

                Toast.makeText(MapSearchProductsActivity.this, "Enter what you want", Toast.LENGTH_SHORT).show();
            } else if (autoCompleteSearch.getText().toString().trim().equals("")) {
                Toast.makeText(MapSearchProductsActivity.this, "Enter address", Toast.LENGTH_SHORT).show();

            } else {
                SharedHelper.putKey(getApplicationContext(), AppConstats.I_NEED, iNeed.getText().toString().trim());
                BottomSheetSearchProduct bottomSheetSearchProduct = new BottomSheetSearchProduct();
                bottomSheetSearchProduct.show(getSupportFragmentManager(), "search");
            }


        });

        relGps.setOnClickListener(view -> {
            if (lastLocation != null) {
                LatLng currentLatLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setOnInfoWindowClickListener(this);

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                Context context = getApplicationContext();

                LinearLayout info = new LinearLayout(context);
                info.setOrientation(LinearLayout.VERTICAL);
                info.setDividerPadding(10);

                TextView title = new TextView(context);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER_HORIZONTAL);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(context);
                snippet.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

        try {
            boolean success = map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));
            if (!success) {
                Log.e("sjdjsk", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("sjdjsk", "Can't find style. Error: ", e);
        }
        getLocationPermission();
        getDeviceLocation();
        updateLocationUI();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE_SEARCH) {
            if (resultCode == RESULT_OK) {

                if (data != null) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                                     LatLng latlng = place.getLatLng();

                    try {
                        Geocoder geocoder = new Geocoder(MapSearchProductsActivity.this, Locale.getDefault());

                        if (latlng != null) {
                            SharedHelper.putKey(getApplicationContext(), AppConstats.SEARCH_LOCATION_LAT, latlng.latitude + "");
                            SharedHelper.putKey(getApplicationContext(), AppConstats.SEARCH_LOCATION_LNG, latlng.longitude + "");

                            List<Address> addressList = geocoder.getFromLocation(latlng.latitude, latlng.longitude, 1);

                            if (addressList != null) {

                                String address = addressList.get(0).getAddressLine(0);
                                autoCompleteSearch.setText(address);

                            } else {
                                Toast.makeText(this, addressList.toString() + "null", Toast.LENGTH_SHORT).show();
                            }
                        }


                    } catch (Exception e) {
                        Log.e("gfvdfrgvd", e.getMessage(), e);
                    }

                }


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                assert status.getStatusMessage() != null;
                Log.i("oireuftoe", status.getStatusMessage());
            }

        } else {
            Log.i("oireuftoe", resultCode + "" + data.toString());
        }
    }

    public void getDeviceLocation() {

        if (isLocationPermission) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            final Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {

                    if (task.isSuccessful()) {
                        lastLocation = task.getResult();
                        if (lastLocation != null) {

                            map.getUiSettings().setMyLocationButtonEnabled(true);
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), 5));


                        } else {
                            LatLng latLng = new LatLng(33.8688, 151.2093);
                            map.animateCamera(CameraUpdateFactory
                                    .newLatLngZoom(latLng, 5));
                            map.getUiSettings().setMyLocationButtonEnabled(false);

                        }
                    }
                }
            });


        }
    }


    public void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            isLocationPermission = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        isLocationPermission = false;
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isLocationPermission = true;
            }
        }

        updateLocationUI();
    }


    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            if (isLocationPermission) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }


    @Override
    public void onInfoWindowClick(Marker marker) {

        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_SHORT).show();
    }
}

