package com.rumaan.academapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rumaan.academapp.R;
import com.rumaan.academapp.model.Constants;
import com.rumaan.academapp.model.User;

import java.util.Arrays;

/*
*  Check for Authentication here
* */
public class SplashPageActivity extends AppCompatActivity {

    // sign-in intent call request code
    private static final int RC_SIGN_IN = 55;

    // permssion request code
    private static final int PERMISSION_REQUEST_CODE = 50;

    private final String TAG = "SplashPageActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser firebaseUser;
    private boolean dataSet = false;
    private DatabaseReference userRef;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();

        setAuthListener();
    }

    private void checkPermissions() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // permission not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                // show message if u want..
                // TODO: show message why we want READ_PHONE_STATE permission
                // for now, just toast why we need permissions
                showToast("We need some permissions for this app to function properly.");
            } else {
                // request the permission here
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSION_REQUEST_CODE
                );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // check the callback
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission granted
                // set auth listener after whatever happened previously
                setAuthListener();
            } else {
                // permission denied
                showToast("This app requires some permissions to work properly.");
            }
            return;
        }
    }

    /**
     * Sets the Authentication Listener to check the state auth state
     */
    private void setAuthListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                // call back for auth state change
                firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    // User is signed in
                    // User ID
                    Log.d(TAG, "onAuthChanged: signed in");
                    Log.i(TAG, "UID : " + firebaseUser.getUid());

                    // add available user details into the database
                    userRef = FirebaseDatabase.getInstance().getReference(Constants.USER_REF_STRING)
                            .child(firebaseUser.getUid());
                    userRef.child("name").setValue(firebaseUser.getDisplayName());
                    userRef.child("email").setValue(firebaseUser.getEmail());

                    // check for 'type' set in the Firebase Db
                    // if its null goto ChooseType
                    // else Goto MainActivity
                    ref = FirebaseDatabase.getInstance()
                            .getReference(Constants.USER_REF_STRING)
                            .child(firebaseUser.getUid())
                            .child("college_details")
                            .child("year")
                            .getRef();
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() == null) {
                                Log.d(TAG, "College Details is Empty!");
                                // FIXME: currently checking for year in the database
                                // FIXME: check for the entire college_details node
                                // goto choose type activity instantly
                                startActivity(new Intent(SplashPageActivity.this, ChooseTypeActivity.class));
                                SplashPageActivity.this.finish();
                            } else {
                                Log.d(TAG, "College Details Present: " + dataSnapshot.getValue());
                                User.getInstance().setName(firebaseUser.getDisplayName());
                                User.getInstance().setType(dataSnapshot.getValue(String.class));
                                startActivity(new Intent(SplashPageActivity.this, MainActivity.class));
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            showToast(getString(R.string.network_error));
                        }
                    });

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthChanged: signed out");
                    // TODO: Customize create account page
                    // create sign in intent if logged out
                    startActivityForResult(
                            AuthUI.getInstance().createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAllowNewEmailAccounts(true)
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
                // goto main activity
                // FIXME: for testing purposes only
                startActivity(new Intent(SplashPageActivity.this, ChooseTypeActivity.class));

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
