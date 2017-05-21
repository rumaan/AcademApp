package com.rumaan.academapp.activities;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.rumaan.academapp.R;
import com.rumaan.academapp.fragments.CreateDiscussionFragment;
import com.rumaan.academapp.fragments.ForumFragment;
import com.rumaan.academapp.model.CustomFont;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity
        extends AppCompatActivity
        implements View.OnClickListener,
        OnTabSelectListener,
        CreateDiscussionFragment.NoticeDialogListener {

    @BindView(R.id.bottom_bar)
    BottomBar bottomBar;
    /* For back button */
    private int count = 0;
    private String uid, name, email;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
        /*
          Firebase Reference Stuffs go here
          */

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        /* hook up listeners */
        bottomBar.setOnTabSelectListener(this);

        /* Get the custom typeface */
        bottomBar.setTabTitleTypeface(CustomFont.getInstance(getApplicationContext()).getTypeFace(CustomFont.Regular));
    }

    @Override
    public void onClick(View v) {
        // do something on click, wait click what?
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId) {
            case R.id.forum_tab_item:
                // forum fragment
                // TODO: design singleton pattern for fragments
                ForumFragment forumFragment = new ForumFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentContainer, forumFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.academics_tab_item:
                // academics fragment
                break;
            case R.id.profile_tab_item:
                // profile fragment
                break;
            case R.id.options_tab_item:
                // options fragment
                Toast.makeText(this, "Option item selected", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        count++;
        if (count >= 1) {
            /* If count is greater than 1, quit */
            finishAffinity();
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
        super.onBackPressed();
    }

    @Override
    public void onPositiveClick(DialogFragment dialog, String title, String desc) {
        Toast.makeText(this, "Title: " + title, Toast.LENGTH_SHORT).show();
    }

}
