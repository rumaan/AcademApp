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
        forumButton.setOnClickListener(this);
        aboutButton.setOnClickListener(this);
        academicsButton.setOnClickListener(this);

    }

    @Override
    public void onClick(@NonNull View v) {
        /* Replace fragments if network available */
        if (isNetworkAvailable(this)) {
            switch (v.getId()) {
                case R.id.btn_forum:
                    // goto ForumFragment
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.dummy_layout, new ForumFragment())
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                    break;
                case R.id.btn_academics:

                    break;
                case R.id.btn_about:
                    break;
                default:
                    // I don't know how you got here
            }
        } else {
            dummyLayout.setVisibility(View.VISIBLE);
        }

    }

    /* Check for Network Connection */
    public static boolean isNetworkAvailable(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE || networkInfo.getType() == ConnectivityManager.TYPE_WIFI || networkInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
                Log.i(TAG, "Network Available");

                // Connected to internet
                return false;
            }
        }

        // not connected
        Log.i(TAG, "Network Unavailable!");
        return true;
    }
}
