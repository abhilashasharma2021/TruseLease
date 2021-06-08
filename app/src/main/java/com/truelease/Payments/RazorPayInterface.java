package com.truelease.Payments;

import android.content.Context;

public interface RazorPayInterface {

    void startPayment(Context context,String amount,String currency,String AppName,String email,String number);
}
