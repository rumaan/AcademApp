package com.rumaan.academapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.*;
import com.firebase.ui.auth.BuildConfig;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class SplashPageActivity extends AppCompatActivity {

    // sign-in intent call request code
    private static final int RC_SIGN_IN = 55;

    private final String TAG = "SplashPageActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // call back for auth state change
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthChanged: signed in");
                    startActivity(new Intent(SplashPageActivity.this, MainActivity.class));
                    finish();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthChanged: signed out");

                    // create sign in intent if logged out
                    startActivityForResult(
                            AuthUI.getInstance().createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                                    .setProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                            new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build()))
                                    .build(), RC_SIGN_IN);
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // get Idpi Response codes
            IdpResponse idpResponse = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // successfully signed in
                startActivity(new Intent(SplashPageActivity.this, MainActivity.class));
                finish();
                return;
            } else {
                if (idpResponse == null) {
                    // user pressed back button
                    showToast("Sign in Cancelled by user.");
                    return;
                } else if (idpResponse.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showToast("No internet Connection");
                    return;
                } else if (idpResponse.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showToast("Some Error Occured!");
                    return;
                }

                showToast("Couldn't sign in. Unknown Response, Please try again later.");
            }
        }
    }
}
