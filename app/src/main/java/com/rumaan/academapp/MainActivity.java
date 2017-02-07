package com.rumaan.academapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout dummyLayout;
    private Button forumButton, aboutButton, academicsButton;
    private FragmentManager fragmentManager;

    private static final String TAG = "MainActivity";

    private static boolean sNetworkAvailabilitiy = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link Java Objects with their real Partners ;-)
        dummyLayout = (FrameLayout) findViewById(R.id.dummy_layout);
        forumButton = (Button) findViewById(R.id.btn_forum);
        aboutButton = (Button) findViewById(R.id.btn_about);
        academicsButton = (Button) findViewById(R.id.btn_academics);

        fragmentManager = getSupportFragmentManager();

        // hook up on click listeners to buttons
        dummyLayout.setOnClickListener(this);
        forumButton.setOnClickListener(this);
        aboutButton.setOnClickListener(this);
        academicsButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        /* As soon as you come to this activity check for network connectivity */
        if (isNetworkAvailable(this)) {
            sNetworkAvailabilitiy = true;
            dummyLayout.setVisibility(View.INVISIBLE);

            /* By default goto forum fragment */
            fragmentManager.beginTransaction().replace(R.id.dummy_layout, new ForumFragment()).commit();
            forumButton.setEnabled(true);
        } else {
            sNetworkAvailabilitiy = false;
            dummyLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(@NonNull View v) {
            switch (v.getId()) {
                case R.id.dummy_layout:
                    // Reload the page
                    // If network available go to ForumFragment
                    break;
                default:
                    // I don't know how you got here
            }
    }

    /* Check for Network Connection */
    public static boolean isNetworkAvailable(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (networkInfo.isConnectedOrConnecting()) {
                Log.i(TAG, "Network Available");

                // Connected to internet
                return true;
            }
        }

        // not connected
        Log.i(TAG, "Network Unavailable!");
        return false;
    }
}
