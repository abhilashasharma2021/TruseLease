package com.truelease.Activities;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.truelease.ApiData.APIClient;
import com.truelease.Others.MyDialog.CustomDialog;
import com.truelease.Others.MyDialog.DialogInterface;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.R;
import com.truelease.RetrofitModel.AgreementModel;

import java.io.File;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgreementActivity extends AppCompatActivity {


    public static final String GOOGLE_DRIVE_PDF_LINK = "https://drive.google.com/viewerng/viewer?embedded=true&url=";
    Button btnDownload;
    WebView webview;
    AgreementModel mAgreementModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);

        btnDownload = findViewById(R.id.btnDownload);
        webview = findViewById(R.id.webView);

        btnDownload.setOnClickListener(v -> {

            if (!mAgreementModel.getPath().equals("") && !mAgreementModel.getData().equals("")) {
                Log.e("ksdksjlkd", mAgreementModel.getData() + mAgreementModel.getPath());
                downloadFile(mAgreementModel.getPath() + mAgreementModel.getData());
            } else {
                Toast.makeText(this, "File path is empty", Toast.LENGTH_SHORT).show();
            }

        });


        WebSettings settings = webview.getSettings();
        settings.setAllowContentAccess(true);
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());


        showAgreement();


    }


    private void downloadFile(String imagePath) {

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(imagePath));
        request.setDescription("Sample Agreement Downloading");
        request.setTitle("True Lease");
        request.setVisibleInDownloadsUi(true);

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "True Lease Agreement");
        request.setDestinationInExternalFilesDir(getApplicationContext(), file.getAbsolutePath(), "/agreement.pdf");

        DownloadManager manager1 = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Objects.requireNonNull(manager1).enqueue(request);
        if (DownloadManager.STATUS_SUCCESSFUL == 8) {

            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();

        }
    }


    private void showAgreement() {

        DialogInterface dialog = new CustomDialog();
        dialog.showDialog(R.layout.pr_dialog, AgreementActivity.this);

        Call<AgreementModel> call = APIClient.getAPIClient().showAgreement();
        call.enqueue(new Callback<AgreementModel>() {
            @Override
            public void onResponse(@NonNull Call<AgreementModel> call, @NonNull Response<AgreementModel> response) {

                if (!response.isSuccessful()) {
                    dialog.hideDialog();
                    ReturnErrorToast.showToast(AgreementActivity.this);
                }


                AgreementModel agreementModel = response.body();
                if (agreementModel != null) {

                    if (agreementModel.getResult()) {
                        dialog.hideDialog();
                        mAgreementModel = agreementModel;
                        if (!agreementModel.getPath().equals("") && !agreementModel.getData().equals("")) {
                            String pdf = agreementModel.getPath() + agreementModel.getData();

                            Log.e("lksdls", "m" + pdf);
                            webview.loadUrl(GOOGLE_DRIVE_PDF_LINK + pdf);
                        } else {
                            dialog.hideDialog();
                            ReturnErrorToast.showWarningToast(AgreementActivity.this, "Corrupt file");
                        }

                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<AgreementModel> call, @NonNull Throwable t) {
                dialog.hideDialog();
                Log.e("sjsnxcsc", t.getMessage());
            }
        });
    }

}