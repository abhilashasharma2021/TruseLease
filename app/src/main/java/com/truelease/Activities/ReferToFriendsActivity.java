package com.truelease.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.truelease.R;
import com.truelease.User.UserData;

import java.io.ByteArrayOutputStream;

public class ReferToFriendsActivity extends AppCompatActivity {
    String ref = "";
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_to_friends);

        ImageView back = findViewById(R.id.back);
        ImageView facebook = findViewById(R.id.facebook);
        ImageView twitter = findViewById(R.id.twitter);
        ImageView whatsApp = findViewById(R.id.whatsApp);
        TextView refCode = findViewById(R.id.refCode);
        image = findViewById(R.id.image);


        if (!UserData.getRefferalCOde(this).equals("")) {
            refCode.setText(UserData.getRefferalCOde(this));
             ref = UserData.getRefferalCOde(this);
        } else {
             ref = "Not Available";
            refCode.setText("Not Available");
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        twitter.setOnClickListener(view -> {


            String tweetUrl = "https://twitter.com/intent/tweet?text=WRITE YOUR MESSAGE HERE &url="
                    + "Join the True Lease Community with my referral code and get your first transaction service fees waived off. Use my referral code " + ref + " while signing up.";
            Uri uri = Uri.parse(tweetUrl);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        });


        facebook.setOnClickListener(view -> shareAppWithSocial(getApplicationContext(), "com.facebook.katana"));
        whatsApp.setOnClickListener(view -> shareAppWithSocial(getApplicationContext(), "com.whatsapp"));



    }

    public void shareAppWithSocial(Context context, String application) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setPackage(application);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(android.content.Intent.EXTRA_TITLE, "True Lease");
        intent.setType("text/plain");
        intent.setType("image/*");
        String tit = "True lease";
        Drawable mDrawable = image.getDrawable();
        Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), mBitmap, "True lease", null);
        Uri imageUri = Uri.parse(path);
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        String shareMessage = "\nJoin the True Lease Community with my referral code and get your first transaction service fees waived off. Use my referral code " + ref + " while signing up.";
        shareMessage = tit + " " + shareMessage + " " + "https://play.google.com/store/apps/details?id=com.truelease";
        intent.putExtra(Intent.EXTRA_TEXT, shareMessage);

        try {
            context.startActivity(Intent.createChooser(intent, "Choose one"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "Please Install App", Toast.LENGTH_LONG).show();
        }


    }

}


