package com.truelease;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.downloader.Status;
import com.truelease.Others.Utils;

import org.json.JSONObject;

import java.io.File;

import okhttp3.OkHttpClient;


public class MainActivity extends AppCompatActivity {

    private static final int BUFFER_SIZE = 4096;
    final String URL1 = "http://www.appsapk.com/downloading/latest/Facebook-119.0.0.23.70.apk";

    Button buttonOne, buttonCancelOne;
    ProgressBar progressBarOne;
    TextView textViewProgressOne;
    int downloadIdOne;
    private static String dirPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        buttonOne = findViewById(R.id.buttonOne);
        buttonCancelOne = findViewById(R.id.buttonCancelOne);
        progressBarOne = findViewById(R.id.progressBarOne);
        textViewProgressOne = findViewById(R.id.textViewProgressOne);




        /*StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);*/


        onClickListenerOne();

        show();

    }


    public void onClickListenerOne() {
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Status.RUNNING == PRDownloader.getStatus(downloadIdOne)) {
                    PRDownloader.pause(downloadIdOne);
                    return;
                }

                buttonOne.setEnabled(false);
                progressBarOne.setIndeterminate(true);
                progressBarOne.getIndeterminateDrawable().setColorFilter(
                        Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);

                if (Status.PAUSED == PRDownloader.getStatus(downloadIdOne)) {
                    PRDownloader.resume(downloadIdOne);
                    return;
                }

                File file = new File(Environment.getExternalStorageDirectory(), "TrueLease Agreement");

                if (!file.exists()) {
                    file.mkdirs();
                }

                downloadIdOne = PRDownloader.download("https://maestrosinfotech.com/True_Lease/image/a22a96005a5a737d2b25a0501d1d1563.pdf", file.getPath(), "abc.pdf")
                        .build()
                        .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                            @Override
                            public void onStartOrResume() {
                                progressBarOne.setIndeterminate(false);
                                buttonOne.setEnabled(true);
                                buttonOne.setText("Pause");
                                buttonCancelOne.setEnabled(true);
                            }
                        })
                        .setOnPauseListener(new OnPauseListener() {
                            @Override
                            public void onPause() {
                                buttonOne.setText("Resume");
                            }
                        })
                        .setOnCancelListener(new OnCancelListener() {
                            @Override
                            public void onCancel() {
                                buttonOne.setText("Start");
                                buttonCancelOne.setEnabled(false);
                                progressBarOne.setProgress(0);
                                textViewProgressOne.setText("");
                                downloadIdOne = 0;
                                progressBarOne.setIndeterminate(false);
                            }
                        })
                        .setOnProgressListener(new OnProgressListener() {
                            @Override
                            public void onProgress(Progress progress) {
                                long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                                progressBarOne.setProgress((int) progressPercent);
                                textViewProgressOne.setText(Utils.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
                                progressBarOne.setIndeterminate(false);
                            }
                        })
                        .start(new OnDownloadListener() {
                            @Override
                            public void onDownloadComplete() {
                                buttonOne.setEnabled(false);
                                buttonCancelOne.setEnabled(false);
                                buttonOne.setText("Completed");
                            }

                            @Override
                            public void onError(Error error) {
                                buttonOne.setText("Start");
                                Toast.makeText(getApplicationContext(), "Some Error occured" + " " + "1", Toast.LENGTH_SHORT).show();
                                textViewProgressOne.setText("");
                                progressBarOne.setProgress(0);
                                downloadIdOne = 0;
                                buttonCancelOne.setEnabled(false);
                                progressBarOne.setIndeterminate(false);
                                buttonOne.setEnabled(true);
                            }
                        });
            }
        });

        buttonCancelOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PRDownloader.cancel(downloadIdOne);
            }
        });
    }


    private void show() {
        AndroidNetworking.enableLogging(); // simply enable logging
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addNetworkInterceptor(logging);
        OkHttpClient client = httpClient.build();
        AndroidNetworking.post("https://lionzy.ruparnatechnologies.com/public/api/vendor_home")
                .addHeaders("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIyIiwianRpIjoiZTQ2MjIzODYwYjk3OTYyYzg3ZmJjMjdjYTAzNDQ4MDcwODQ2MzI4OTZlY2U2NWI0ZTI5MDg3OTg3MzcwNWZkNmQ3Zjc1ODhjMzY4MGQ5OTkiLCJpYXQiOjE2MTY3NjE3MDMsIm5iZiI6MTYxNjc2MTcwMywiZXhwIjoxNjQ4Mjk3NzAzLCJzdWIiOiI2MCIsInNjb3BlcyI6W119.Jj-JcKIEA8SybZSUk7t5ZlQm65QqLviJbxKP1L0Q7DRhOhJLWJZEDP1FcQy9YU_p7_aJW39sMMfZMxFaFq8VP3Wme5K3rpqvGa5hypArcOxlWhf_sG_5uPFLnTkChry4wMo88gmWyuf3-LHtoWpMpKE4k0ycy9nGxa-iHw-SI2ANuTI35brerNDrsqVvdPHf6_pzVwUDB-C3cnvs1IyA-fVXGu2FnmKk2LLTCBOStazJsnNLasAiS-ooWm5qD7mVFCocldSLBCnTPYFo2-_iw14iuJ-wQzsRXNWzPxaEpyT-vkov4zInwHm9WopHwIArDpwqAkSKGDJLxI-Gb-mqsE8jt0pQxbObPViWBwqX6VFjLGZOUiCkr1fQYG7QJWweu36TZB94FXEHk90PHEYD1dJpIL2jyrbXXVewCv34iiVt1QQ5OmAuaP6A6idz1cZ8SnDLKX5wfMHbXhT-Ep7b8siCCUV9pumgrfwwKdnEJJPkgSfDQ8JAsUd7yjmN-VM7aU9g3k-1BVBXLx3jI4qP9ooh9qhT6O68pNV-i6u4kn_uBoLu3p_Wl7e2o9RnMf5l5ICDCgoNZ8CQ7Mm72sIzFeDAfXIyrooNAczu-vRThb9a88IDe4DDrlvjj9m4OTBuhG_Ru-QK0TSx_sT9BQMp0wDE2RCJh3anK_WFGmuqg3k")
                .setTag("ksjx")
                .setOkHttpClient(client)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("iwsudd", response.toString());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(MainActivity.this, "no", Toast.LENGTH_SHORT).show();
                        Log.e("iwsudd", anError.getMessage());
                    }
                });
    }



/*    public static void downloadFile(String fileURL, String saveDir)
            throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();

            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                        fileURL.length());
            }

            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Disposition = " + disposition);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("fileName = " + fileName);

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = saveDir + File.separator + fileName;

            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

            System.out.println("File downloaded");
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }*/
}