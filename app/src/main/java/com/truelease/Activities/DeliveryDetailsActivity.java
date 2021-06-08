package com.truelease.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.truelease.Adapters.DetailAdapter;
import com.truelease.Others.AppConstats;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.Others.SharedHelper;
import com.truelease.Others.Utils;
import com.truelease.Payments.PaymentOptionsActivity;
import com.truelease.R;
import com.truelease.Room.SaveDetails.DetailViewModel;
import com.truelease.Room.SaveDetails.Details;
import com.truelease.User.UserData;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class DeliveryDetailsActivity extends AppCompatActivity {


    private static final int AUTOCOMPLETE_REQUEST_CODE_SEARCH = 8929;
    TextView total;
    ImageView savedAddress;

    public static final int DETAIL_REQUEST_CODE = 3934;

    public static DetailViewModel detailViewModel;
    EditText etName, etEmail, etMobile, etFullAddress, etPincode, etCity;
    String strName = "", strEmail = "", strMobile = "", strFullAddress = "", strPincode = "", strCity = "", strState = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);

        ImageView back = findViewById(R.id.back);
        Button btnPay = findViewById(R.id.btnPay);
        total = findViewById(R.id.total);
        savedAddress = findViewById(R.id.savedAddress);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etMobile = findViewById(R.id.etMobile);
        etFullAddress = findViewById(R.id.etFullAddress);
        etPincode = findViewById(R.id.etPincode);
        etCity = findViewById(R.id.etCity);

        etCity.setText(UserData.getCityName(getApplicationContext()));
        etMobile.setText(UserData.getUserMobileNumber(getApplicationContext()));

        back.setOnClickListener(view -> finish());


        if (!Places.isInitialized()) {
            Places.initialize(DeliveryDetailsActivity.this, getString(R.string.api_key), Locale.getDefault());
        }

        etFullAddress.setOnClickListener(v -> {

            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(DeliveryDetailsActivity.this);
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE_SEARCH);

        });


        total.setText("\u20B9 " + UserData.getTotalAmount(getApplicationContext()));

        btnPay.setOnClickListener(v -> {

            strName = etName.getText().toString().trim();
            strEmail = etEmail.getText().toString().trim();
            strMobile = etMobile.getText().toString().trim();
            strFullAddress = etFullAddress.getText().toString().trim();
            strPincode = etPincode.getText().toString().trim();
            strCity = etCity.getText().toString().trim();

            if (TextUtils.isEmpty(strName)) {
                etName.setError("Enter your name");
            } else if (TextUtils.isEmpty(strEmail)) {
                etEmail.setError("Enter your email");
            } else if (TextUtils.isEmpty(strMobile)) {
                etMobile.setError("Enter your mobile");
            } else if (TextUtils.isEmpty(strFullAddress)) {
                etFullAddress.setError("Enter your full address");
            } else if (TextUtils.isEmpty(strPincode)) {
                etPincode.setError("Enter your pincode");
            } else if (TextUtils.isEmpty(strCity)) {
                etCity.setError("Enter your city");
            } else if (!strState.equals(UserData.getCityName(DeliveryDetailsActivity.this))) {
                ReturnErrorToast.showWarningToast(DeliveryDetailsActivity.this, "This address does not belong to this state");
            } else {
                detailViewModel = new ViewModelProvider(DeliveryDetailsActivity.this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(DetailViewModel.class);
                Details details = new Details(strName, strEmail, strMobile, strFullAddress, strPincode, UserData.getCityID(getApplicationContext()), strState, UserData.getUserID(DeliveryDetailsActivity.this));
                detailViewModel.insert(details);

                SharedHelper.putKey(getApplicationContext(), AppConstats.BOOKING_USERNAME, strName);
                SharedHelper.putKey(getApplicationContext(), AppConstats.BOOKING_EMAIL, strEmail);
                SharedHelper.putKey(getApplicationContext(), AppConstats.BOOKING_NUMBER, strMobile);
                SharedHelper.putKey(getApplicationContext(), AppConstats.BOOKING_ADDRESS, strFullAddress);

                startActivity(new Intent(getApplicationContext(), PaymentOptionsActivity.class));
            }


        });


        savedAddress.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SavedDetailsActivity.class);
            startActivityForResult(intent, DETAIL_REQUEST_CODE);
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE_SEARCH && resultCode == RESULT_OK) {
            if (data != null) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                LatLng latlng = place.getLatLng();
                if (latlng != null) {

                    String address = Utils.getAddress(DeliveryDetailsActivity.this, latlng.latitude, latlng.longitude);
                    String pincode = Utils.getPincode(DeliveryDetailsActivity.this, latlng.latitude, latlng.longitude);
                    String state = Utils.getState(DeliveryDetailsActivity.this, latlng.latitude, latlng.longitude);

                    Log.e("statewww", "askja" + state);
                    strState = state;
                    etFullAddress.setText(address);
                    etPincode.setText(pincode);
                }


            }
        }


        if (requestCode == DETAIL_REQUEST_CODE && resultCode == RESULT_OK) {

            if (data != null) {

                String name = data.getStringExtra(DetailAdapter.NAME);
                String email = data.getStringExtra(DetailAdapter.EMAIL_ID);
                String number = data.getStringExtra(DetailAdapter.MOBILE_NUMBER);
                String fullAddress = data.getStringExtra(DetailAdapter.FULL_ADDRESS);
                String pincode = data.getStringExtra(DetailAdapter.PINCODE);
                String state = data.getStringExtra(DetailAdapter.STATE);


                Log.e("actityResult", "onActivityResult: " + name);
                Log.e("actityResult", "onActivityResult: " + email);
                Log.e("actityResult", "onActivityResult: " + number);
                Log.e("actityResult", "onActivityResult: " + fullAddress);
                Log.e("actityResult", "onActivityResult: " + pincode);
                Log.e("actityResult", "onActivityResult: " + state);

                strState = state;
                etName.setText(name);
                etEmail.setText(email);
                etFullAddress.setText(fullAddress);
                etMobile.setText(number);
                etPincode.setText(pincode);


            }


        }

    }


}