package com.rumaan.academapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout dummyLayout;
    private Button forumButton, aboutButton, academicsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link Java Objects with their real Partners ;-)
        forumButton = (Button) findViewById(R.id.btn_forum);
        aboutButton = (Button) findViewById(R.id.btn_about);
        academicsButton = (Button) findViewById(R.id.btn_academics);

        // hook up on click listeners to buttons
        forumButton.setOnClickListener(this);
        aboutButton.setOnClickListener(this);
        academicsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_forum:
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
