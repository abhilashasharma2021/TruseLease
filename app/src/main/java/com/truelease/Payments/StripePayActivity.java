package com.truelease.Payments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardMultilineWidget;
import com.truelease.Activities.StripeWebActivity;
import com.truelease.ApiData.APIClient;
import com.truelease.Others.AppConstats;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.MyDialog.DialogInterface;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.Others.SharedHelper;
import com.truelease.R;
import com.truelease.RetrofitModel.StripeResponse;
import com.truelease.User.UserData;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StripePayActivity extends AppCompatActivity {
    CardMultilineWidget cardInputWidget;
    Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_pay);
        cardInputWidget = findViewById(R.id.card_input_widget);
        btn_save = findViewById(R.id.save_payment);

        Log.e("cssncjsc", UserData.getCartId(getApplicationContext()));
        Log.e("cssncjsc", UserData.getCityID(getApplicationContext()));


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveCard();
            }
        });
    }


    private void saveCard() {

        Card card = cardInputWidget.getCard();

        if (card == null) {
            Toast.makeText(getApplicationContext(), "Invalid card", Toast.LENGTH_SHORT).show();
        } else {
            if (!card.validateCard()) {
                Toast.makeText(getApplicationContext(), "Invalid card", Toast.LENGTH_SHORT).show();
            } else {
                CreateToken(card);
            }
        }
    }

    private void CreateToken(final Card card) {

        DialogInterface dialog = new CustomDialog();
        dialog.showDialog(R.layout.pr_dialog,StripePayActivity.this);


        Stripe stripe = new Stripe(getApplicationContext(), getString(R.string.publishablekey));

        stripe.createToken(
                card,
                new TokenCallback() {
                    public void onSuccess(Token token) {

                        Log.e("kshvkjsbv", token.getId());
                        Log.e("kshvkjsbv", card.getNumber());
                        Log.e("kshvkjsbv", card.getCVC());
                        Log.e("kshvkjsbv", card.getExpMonth() + "");
                        Log.e("kshvkjsbv", card.getExpYear() + "");

                        Map<String, String> param = new HashMap<>();
                        param.put("cartID", UserData.getCartId(getApplicationContext()));
                        param.put("cityID", UserData.getCityID(getApplicationContext()));
                        param.put("cardNumber", card.getNumber());
                        param.put("cardCVC", card.getCVC());
                        param.put("cardExpMonth", card.getExpMonth() + "");
                        param.put("cardExpYear", card.getExpYear() + "");
                        param.put("source", token.getId());
                        stripePayment(param);


                        dialog.hideDialog();


                    }

                    public void onError(Exception error) {

                        dialog.hideDialog();
                        Log.e("ijofokvkb", error.getLocalizedMessage());
                        Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }


                }
        );
    }


    private void stripePayment(Map<String, String> param) {

        DialogInterface dialog = new CustomDialog();
        dialog.showDialog(R.layout.pr_dialog,StripePayActivity.this);

        Call<StripeResponse> call = APIClient.getAPIClient().stripeResponse(param);
        call.enqueue(new Callback<StripeResponse>() {
            @Override
            public void onResponse(@NonNull Call<StripeResponse> call, @NonNull Response<StripeResponse> response) {

                if (!response.isSuccessful()) {
                    dialog.hideDialog();
                    ReturnErrorToast.showToast(StripePayActivity.this);
                } else {

                    StripeResponse stripeResponse = response.body();

                    if (stripeResponse != null) {

                        if (stripeResponse.getResult()) {
                            SharedHelper.putKey(StripePayActivity.this, AppConstats.STRIPE_LINK, stripeResponse.getPaymentLink());

                            new Handler().postDelayed(() -> {
                                dialog.hideDialog();
                                startActivity(new Intent(getApplicationContext(), StripeWebActivity.class));
                                finish();
                            }, 2000);

                        }

                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<StripeResponse> call, @NonNull Throwable t) {
                dialog.hideDialog();
                Log.e("djcdks", t.getMessage() + "");
            }
        });
    }


}