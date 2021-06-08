package com.truelease.Payments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.razorpay.PaymentResultListener;
import com.truelease.Activities.SuccessfullActivity;
import com.truelease.ApiData.APIClient;
import com.truelease.Others.AppConstats;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.MyDialog.DialogInterface;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.Others.SharedHelper;
import com.truelease.R;
import com.truelease.RetrofitModel.BookingModel;
import com.truelease.User.UserData;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class PaymentOptionsActivity extends AppCompatActivity implements PaymentResultListener {

    ImageView cross;

    RazorPayImp razorPayImp = new RazorPayImp();

    RelativeLayout  razorpay, stripe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_options2);


        cross = findViewById(R.id.cross);
        razorpay = findViewById(R.id.razorpay);
        stripe = findViewById(R.id.stripe);


        stripe.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),StripePayActivity.class)));


        razorpay.setOnClickListener(view -> {

            String TXN_AMOUNT = "";

            if (!UserData.getTotalAmount(PaymentOptionsActivity.this).equals("")) {
                TXN_AMOUNT = UserData.getTotalAmount(getApplicationContext());
            } else {
                TXN_AMOUNT = "0.0";
            }


            Log.e("cskjclks", TXN_AMOUNT + "");
            razorPayImp.startPayment(PaymentOptionsActivity.this, TXN_AMOUNT,
                    "INR", "True Lease", UserData.getUserEmail(this), UserData.getUserMobileNumber(this));
        });


        cross.setOnClickListener(view -> finish());


    }


    @Override
    public void onPaymentSuccess(String s) {
        Log.e("success", " payment successfull " + s);
        itemBooking();
        Toast.makeText(this, "Payment successfully done! ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.e("onErrorRazor", "error code " + i + " -- Payment failed " + s);
        try {
            Toast.makeText(this, "Payment error please try again", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("OnPaymentError", "Exception in onPaymentError", e);
        }
    }


    public void itemBooking() {

        String strName = SharedHelper.getKey(PaymentOptionsActivity.this, AppConstats.BOOKING_USERNAME);
        String strEmail = SharedHelper.getKey(PaymentOptionsActivity.this, AppConstats.BOOKING_EMAIL);
        String strMobile = SharedHelper.getKey(PaymentOptionsActivity.this, AppConstats.BOOKING_NUMBER);
        String strFullAddress = SharedHelper.getKey(PaymentOptionsActivity.this, AppConstats.BOOKING_ADDRESS);

        Map<String, String> param = new HashMap<>();
        param.put("userID", UserData.getUserID(getApplicationContext()));
        param.put("cartID", UserData.getCartId(getApplicationContext()));
        param.put("name", strName);
        param.put("email", strEmail);
        param.put("mobile", strMobile);
        param.put("full_address", strFullAddress);
        param.put("cityID", UserData.getCityID(getApplicationContext()));
        param.put("pay_through", "3");

        DialogInterface dialog = new CustomDialog();
        dialog.showDialog(R.layout.pr_dialog, PaymentOptionsActivity.this);

        Call<BookingModel> call = APIClient.getAPIClient().booking(param);
        call.enqueue(new Callback<BookingModel>() {
            @Override
            public void onResponse(@NonNull Call<BookingModel> call, @NonNull retrofit2.Response<BookingModel> response) {

                if (!response.isSuccessful()) {

                    dialog.hideDialog();
                    ReturnErrorToast.showToast(PaymentOptionsActivity.this);
                }

                BookingModel bookingModel = response.body();
                if (bookingModel != null) {
                    dialog.hideDialog();
                    if (bookingModel.getResult()) {

                        Log.e("kscsl", "reached");
                        startActivity(new Intent(PaymentOptionsActivity.this, SuccessfullActivity.class));
                    } else {

                        ReturnErrorToast.showWarningToast(PaymentOptionsActivity.this, bookingModel.getMessage());
                    }
                } else {

                    dialog.hideDialog();
                }
            }

            @Override
            public void onFailure(@NonNull Call<BookingModel> call, @NonNull Throwable t) {
                dialog.hideDialog();
                Log.e("kscsl", "reached" + t.getMessage());
            }
        });
    }


}