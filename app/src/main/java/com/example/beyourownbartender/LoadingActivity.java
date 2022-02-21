package com.example.beyourownbartender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LoadingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);

            startActivity(intent);
            finish();
        }, 3000);
    }
}