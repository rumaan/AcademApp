package com.rumaan.academapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.rumaan.academapp.databinding.ActivityHomeBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnTabSelectListener {

    /* Data binding object */
    ActivityHomeBinding activityHomeBinding;

    /* Bottom bar object */
    private BottomBar bottomBar;

    /* For back button */
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Using data binding */
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        bottomBar = activityHomeBinding.bottomBar;

        /* hook up listeners */
        bottomBar.setOnTabSelectListener(this);

        /* Get the custom typeface */
        bottomBar.setTabTitleTypeface(CustomFont.getInstance(getApplicationContext()).getTypeFace(CustomFont.Regular));
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId) {
            case R.id.forum_tab_item:
                // change the status bar background accordingly
                getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.blue));

                ForumFragment forumFragment = new ForumFragment();

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(activityHomeBinding.rootView.getId(), forumFragment)
                        .commit();

                break;
            case R.id.academics_tab_item:
                // change the status bar background accordingly
                getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.Asphalt_grey));

                // replace with academ fragment
                AcademicsFragment academicsFragment = new AcademicsFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(activityHomeBinding.rootView.getId(), academicsFragment)
                        .commit();

                break;
            case R.id.profile_tab_item:
                // change the status bar background accordingly
                getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.Emerald_flat));

                // u get the point.
                ProfileFragment profileFragment = new ProfileFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(activityHomeBinding.rootView.getId(), profileFragment)
                        .commit();

                break;
        }
    }

    @Override
    public void onBackPressed() {
        count++;
        if (count > 1) {
            /* If count is greater than 1, quit */
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Press back again to Leave!", Toast.LENGTH_SHORT).show();

            // reset the counter in 2s
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    count = 0;
                }
            }, 2000);
        }
    }
}
