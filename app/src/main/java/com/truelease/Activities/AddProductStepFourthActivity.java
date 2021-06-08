package com.truelease.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.sdsmdg.tastytoast.TastyToast;
import com.truelease.DraggableMap.LocationPickerActivity;
import com.truelease.Others.AppConstats;
import com.truelease.Others.SharedHelper;
import com.truelease.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddProductStepFourthActivity extends AppCompatActivity implements View.OnClickListener {


    ImageView tick1, tick2;
    RelativeLayout rel1, rel2;
    LinearLayout linearCustom;
    Button btnNext;
    ImageView back;
    boolean isSelected = false;
    TextInputEditText datePick, timePick, etLocation, deliveryCharge;
    String strDatePick = "", strTimePick = "", strLocation = "", strDeliveryCharges = "";
    TextInputLayout deliveryCharges;
    String deliveryType = "";

    public static final int REQUEST_CODE = 1111;

    //datepicker & timepicker
    private final SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a", Locale.getDefault());
    int hour;
    int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_step_fourth);


        rel1 = findViewById(R.id.rel1);
        rel2 = findViewById(R.id.rel2);
        tick1 = findViewById(R.id.tick1);
        tick2 = findViewById(R.id.tick2);
        linearCustom = findViewById(R.id.linearCustom);
        btnNext = findViewById(R.id.btnNext);
        back = findViewById(R.id.back);
        deliveryCharges = findViewById(R.id.deliveryCharges);
        datePick = findViewById(R.id.datePick);
        timePick = findViewById(R.id.timePick);
        etLocation = findViewById(R.id.etLocation);
        deliveryCharge = findViewById(R.id.deliveryCharge);

        rel1.setOnClickListener(this);
        rel2.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        back.setOnClickListener(this);
        datePick.setOnClickListener(this);
        timePick.setOnClickListener(this);
        etLocation.setOnClickListener(this);




    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.rel1:
                tick1.setVisibility(View.VISIBLE);
                tick2.setVisibility(View.GONE);
                deliveryCharges.setVisibility(View.VISIBLE);
                isSelected = true;
                deliveryType = "1";
                break;


            case R.id.rel2:
                tick1.setVisibility(View.GONE);
                tick2.setVisibility(View.VISIBLE);
                deliveryCharges.setVisibility(View.GONE);
                isSelected = true;
                deliveryType = "2";
                break;

            case R.id.btnNext:

                if (isSelected) {

                    strDatePick = datePick.getText().toString().trim();
                    strTimePick = timePick.getText().toString().trim();
                    strLocation = etLocation.getText().toString().trim();
                    strDeliveryCharges = deliveryCharge.getText().toString().trim();

                    if (deliveryType.equals("1")) {

                        if (TextUtils.isEmpty(strDatePick)) {

                            TastyToast.makeText(getApplicationContext(), "Select Date", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                        } else if (TextUtils.isEmpty(strTimePick)) {


                            TastyToast.makeText(getApplicationContext(), "Select Time", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                        } else if (TextUtils.isEmpty(strLocation)) {
                            TastyToast.makeText(getApplicationContext(), "Select Location", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                        } else if (TextUtils.isEmpty(strDeliveryCharges)) {
                            TastyToast.makeText(getApplicationContext(), "Enter Delivery Charges", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                        } else {

                            SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_DATE, strDatePick);
                            SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_TIME, strTimePick);
                            SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_DELIVERY_PRICE, strDeliveryCharges);
                            SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_DELIVERY_TYPE, "1");
                            startActivity(new Intent(getApplicationContext(), AddImagesActivity.class));

                        }


                    } else {

                        if (TextUtils.isEmpty(strDatePick)) {
                            TastyToast.makeText(getApplicationContext(), "Select Date", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                        } else if (TextUtils.isEmpty(strTimePick)) {
                            TastyToast.makeText(getApplicationContext(), "Select Time", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                        } else if (TextUtils.isEmpty(strLocation)) {
                            TastyToast.makeText(getApplicationContext(), "Select Location", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                        } else {

                            SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_DATE, strDatePick);
                            SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_TIME, strTimePick);
                            SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_DELIVERY_PRICE, "0");
                            SharedHelper.putKey(getApplicationContext(), AppConstats.ADD_DELIVERY_TYPE, "2");
                            startActivity(new Intent(getApplicationContext(), AddImagesActivity.class));

                        }

                    }


                } else {
                    Toast.makeText(AddProductStepFourthActivity.this, "Please select delivery option", Toast.LENGTH_SHORT).show();
                }


                break;


            case R.id.back:
                finish();
                break;

            case R.id.etLocation:

                SharedHelper.putKey(AddProductStepFourthActivity.this, AppConstats.MAP_CLICKED, "addproduct");
                Intent intent = new Intent(getApplicationContext(), LocationPickerActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;


            case R.id.datePick:

                /* MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
                builder.setTitleText("True lease");
                MaterialDatePicker<Long> picker = builder.build();
                picker.show(getSupportFragmentManager(), picker.toString());

                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {

                        Date d = new Date(selection);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
                        datePick.setText(dateFormat.format(d));
                    }
                });*/

                MaterialTimePicker materialTimePick = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(hour)
                        .setMinute(minute)
                        .build();

                materialTimePick.show(getSupportFragmentManager(), "fragment_tag");

                materialTimePick.addOnPositiveButtonClickListener(dialog -> {
                    int newHour = materialTimePick.getHour();
                    int newMinute = materialTimePick.getMinute();
                    onTimeSet2(newHour, newMinute);
                });


                break;


            case R.id.timePick:

                MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(hour)
                        .setMinute(minute)
                        .build();

                materialTimePicker.show(getSupportFragmentManager(), "fragment_tag");

                materialTimePicker.addOnPositiveButtonClickListener(dialog -> {
                    int newHour = materialTimePicker.getHour();
                    int newMinute = materialTimePicker.getMinute();
                    onTimeSet(newHour, newMinute);
                });

                break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode==RESULT_OK) {

            String address = data.getStringExtra(LocationPickerActivity.FULL_ADDRESS);
            etLocation.setText(address);

        }else {

            TastyToast.makeText(getApplicationContext(), "Address not selected", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
        }
    }

    private void onTimeSet(int newHour, int newMinute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, newHour);
        cal.set(Calendar.MINUTE, newMinute);
        cal.setLenient(false);

        String format = formatter.format(cal.getTime());
        timePick.setText(format);
        hour = newHour;
        minute = newMinute;
    }


      private void onTimeSet2(int newHour, int newMinute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, newHour);
        cal.set(Calendar.MINUTE, newMinute);
        cal.setLenient(false);

        String format = formatter.format(cal.getTime());
        datePick.setText(format);
        hour = newHour;
        minute = newMinute;
    }

}