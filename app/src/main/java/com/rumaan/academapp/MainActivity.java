package com.rumaan.academapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout dummyLayout;
    private Button forumButton, aboutButton, academicsButton;
    private FragmentManager fragmentManager;

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_forum:

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
    }
}
