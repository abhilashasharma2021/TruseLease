package com.truelease.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.truelease.ApiData.APIClient;
import com.truelease.Others.ReturnErrorToast;
import com.truelease.R;
import com.truelease.RetrofitModel.ContactUsModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsActivity extends AppCompatActivity {

    ImageView back;
    TextView contactNumber, email;
    RelativeLayout relWirteUs, rel_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        back = findViewById(R.id.back);
        relWirteUs = findViewById(R.id.relWirteUs);
        rel_call = findViewById(R.id.rel_call);
        contactNumber = findViewById(R.id.contactNumber);
        email = findViewById(R.id.email);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        showAdminData();
    }


    public void showAdminData() {

        Call<ContactUsModel> call = APIClient.getAPIClient().showAdminContact();
        call.enqueue(new Callback<ContactUsModel>() {
            @Override
            public void onResponse(@NonNull Call<ContactUsModel> call, @NonNull Response<ContactUsModel> response) {
                if (!response.isSuccessful()) {
                    ReturnErrorToast.showToast(ContactUsActivity.this);
                } else {

                    ContactUsModel model = response.body();
                    if (model != null) {

                        if (model.getResult()) {
                            ContactUsModel.Data data = model.getData();

                            if (data != null) {


                                email.setText(data.getEmail());
                                contactNumber.setText(data.getMobile());

                                relWirteUs.setOnClickListener(view -> {
                                    Intent email = new Intent(Intent.ACTION_SEND);
                                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{data.getEmail()});
                                    email.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
                                    email.putExtra(Intent.EXTRA_TEXT, "Type here");
                                    email.setType("message/rfc822");
                                    startActivity(Intent.createChooser(email, "Choose an Email client :"));
                                });


                                rel_call.setOnClickListener(view -> {
                                    Intent callingIntent = new Intent(Intent.ACTION_DIAL);
                                    callingIntent.setData(Uri.parse("tel:" + data.getMobile()));
                                    startActivity(callingIntent);
                                });
                            }

                        }

                    }
                }


            }

            @Override
            public void onFailure(Call<ContactUsModel> call, Throwable t) {

            }
        });
    }
}