package com.rumaan.academapp.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.rumaan.academapp.Manifest;
import com.rumaan.academapp.fragments.AcademicsFragment;
import com.rumaan.academapp.fragments.ForumFragment;
import com.rumaan.academapp.fragments.ProfileFragment;
import com.rumaan.academapp.model.CustomFont;
import com.rumaan.academapp.R;
import com.rumaan.academapp.databinding.ActivityHomeBinding;

import butterknife.BindView;
import uk.co.chrisjenx.calligraphy.BuildConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnTabSelectListener {

    /* Data binding object */
    ActivityHomeBinding activityHomeBinding;

    /* Bottom bar object */
    private BottomBar bottomBar;

    /* For back button */
    private int count = 0;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mUsersRef;

    private String uid, name, email;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /*
          Firebase Reference Stuffs go here
          */
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mFirebaseDatabase.getReference();
        mUsersRef = mDatabaseRef.child("users").getRef();

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        // set name and email values in Firebase Database
        setReferenceValues();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());


        /* Using data binding */
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        bottomBar = activityHomeBinding.bottomBar;

        /* hook up listeners */
        bottomBar.setOnTabSelectListener(this);

        /* Get the custom typeface */
        bottomBar.setTabTitleTypeface(CustomFont.getInstance(getApplicationContext()).getTypeFace(CustomFont.Regular));


        // temp
      //  startActivity(new Intent(this, UserDetailsActivity.class));
    }

    /* Set the user name and email in Database */
    private void setReferenceValues() {
        mUsersRef.child(uid).child("name").setValue(name);
        mUsersRef.child(uid).child("email").setValue(email);
    }

    @Override
    public void onClick(View v) {
        // do something on click, wait click what?

    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId) {
            case R.id.forum_tab_item:
                // change the status bar background accordingly
                getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.blue));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // set the navigation bar color
                    getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.blue));
                }
                ForumFragment forumFragment = new ForumFragment();

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(activityHomeBinding.rootView.getId(), forumFragment)
                        .commit();


                break;
            case R.id.academics_tab_item:
                // change the status bar background accordingly
                getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.Emerald_flat));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // set the navigation bar color
                    getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.Emerald_flat));
                }

                // replace with academ fragment
                AcademicsFragment academicsFragment = new AcademicsFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(activityHomeBinding.rootView.getId(), academicsFragment)
                        .commit();

                break;
            case R.id.profile_tab_item:
                // change the status bar background accordingly
                getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.grey));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // set the navigation bar color
                    getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.Asphalt_grey));
                }
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
    }
}
