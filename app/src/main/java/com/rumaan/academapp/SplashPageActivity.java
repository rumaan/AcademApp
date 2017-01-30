package com.rumaan.academapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        /* after loading goto next activity */
    }

    @Override
    protected void onStart() {
        super.onStart();
        startActivity(new Intent(SplashPageActivity.this, MainActivity.class));
    }
}
