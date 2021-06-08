package com.truelease.Payments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.truelease.Activities.SuccessfullActivity;
import com.truelease.R;
import com.truelease.User.UserData;

public class PaypalActivity extends AppCompatActivity {
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);

        webview = findViewById(R.id.webView);


        WebSettings settings = webview.getSettings();
        settings.setAllowContentAccess(true);
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.e("urlLoading", request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);


            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.e("pageStarted", url + "");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("pageFinish", url + "");
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                Log.e("onLoad", url + "");

                if (url.contains("successful")){

                    startActivity(new Intent(PaypalActivity.this, SuccessfullActivity.class));
                    finishAffinity();
                }
            }
        });

        String cartId = UserData.getCartId(getApplicationContext());
        String cityId = UserData.getCityID(getApplicationContext());


        Log.e("sxjlksa", cartId + "");
        Log.e("sxjlksa", cityId + "");
        //https://www.truelease.net/PayPal/payment.php/?cartID=562&cityID=1
        webview.loadUrl("https://www.truelease.net/PayPal/payment.php/?cartID=" + cartId + "&cityID=" + cityId);


    }
}