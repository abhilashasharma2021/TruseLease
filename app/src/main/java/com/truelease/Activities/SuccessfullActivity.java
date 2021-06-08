package com.truelease.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.truelease.R;

public class SuccessfullActivity extends AppCompatActivity {

    Button btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successfull);


        btnDone = findViewById(R.id.btnDone);
        btnDone.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), BottomNavigationActivity.class));
            finishAffinity();
        });
    }
}