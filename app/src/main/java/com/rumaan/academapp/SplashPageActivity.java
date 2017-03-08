package com.rumaan.academapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SplashPageActivity extends AppCompatActivity {

    private final String TAG = "SplashPageActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onStart() {
        super.onStart();

        startActivity(new Intent(SplashPageActivity.this, MainActivity.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
