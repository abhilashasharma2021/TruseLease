package com.truelease.Room.RoomDatabase;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputEditText;
import com.truelease.R;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class AddAddressActivity extends AppCompatActivity {

    public static final String EXTRA_CITY = "EXTRA_CITY";
    public static final String EXTRA_ADDRESS = "EXTRA_ADDRESS";
    public static final String EXTRA_LAT = "EXTRA_LAT";
    public static final String EXTRA_LNG = "EXTRA_LNG";

    TextInputEditText etCity, etAddress;
    Button btnAdd;
    ImageView back;
    String strCity = "", strAddress = "", strLat = "", strLng = "";
    private static final int AUTOCOMPLETE_REQUEST_CODE_SEARCH = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        etCity = findViewById(R.id.etCity);
        btnAdd = findViewById(R.id.btnAdd);
        etAddress = findViewById(R.id.etAddress);
        back = findViewById(R.id.back);


        back.setOnClickListener(v->finish());


        if (!Places.isInitialized()) {
            Places.initialize(AddAddressActivity.this, getString(R.string.api_key), Locale.getDefault());

        }


        etCity.setOnClickListener(v -> {
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(AddAddressActivity.this);
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE_SEARCH);
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strCity = etCity.getText().toString().trim();
                strAddress = etAddress.getText().toString().trim();

                if (strCity.equals("")) {

                } else if (strAddress.equals("")) {

                } else {
                    Intent data = new Intent();
                    data.putExtra(EXTRA_CITY, strCity);
                    data.putExtra(EXTRA_ADDRESS, strAddress);
                    setResult(RESULT_OK, data);
                }
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE_SEARCH) {
            if (resultCode == RESULT_OK) {

                if (data != null) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    Log.i("oireuftoe", "Place: " + place.getName() + ", " + place.getLatLng() +
                            place.getAddress());

                    LatLng pickUplatlng = place.getLatLng();

                    try {
                        Geocoder geocoder = new Geocoder(AddAddressActivity.this, Locale.getDefault());

                        if (pickUplatlng != null) {

                            Log.e("rtrere", "latlng" + pickUplatlng.latitude + "," + pickUplatlng.longitude);

                            strLat = pickUplatlng.latitude + "";
                            strLng = pickUplatlng.longitude + "";

                            List<Address> addressList = geocoder.getFromLocation(pickUplatlng.latitude, pickUplatlng.longitude, 1);

                            if (addressList != null) {

                                String address = addressList.get(0).getAddressLine(0);
                                Log.e("weewr", "msg : " + address);
                                etAddress.setText(address);
                                etCity.setText(addressList.get(0).getSubAdminArea());

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
}