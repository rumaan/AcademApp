package com.rumaan.academapp.activities;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.rumaan.academapp.R;
import com.rumaan.academapp.fragments.AcademicsFragment;
import com.rumaan.academapp.fragments.CreateDiscussionFragment;
import com.rumaan.academapp.fragments.ForumFragment;
import com.rumaan.academapp.fragments.OptionsFragment;
import com.rumaan.academapp.fragments.ProfileFragment;
import com.rumaan.academapp.model.CustomFont;
import com.rumaan.academapp.model.ForumPost;
import com.rumaan.academapp.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity
        extends AppCompatActivity
        implements View.OnClickListener,
        OnTabSelectListener,
        CreateDiscussionFragment.NoticeDialogListener {
    private static final String TAG = "MainActivity";
    @BindView(R.id.bottom_bar)
    BottomBar bottomBar;
    FirebaseAuth auth;
    /* For back button */
    private int count = 0;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference postsRef;
    private FirebaseUser firebaseUser;


    /* Stores the forumPostList , as a backup copy of FirebaseDatabase */
    private List<ForumPost> forumPostList = new ArrayList<>();

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

        // point the database reference to post node
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        postsRef = FirebaseDatabase.getInstance()
                .getReference("forum_posts")
                .child(User.getInstance().getType());

        // get the posts from the posts reference
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "ChildAdded: " + dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        postsRef.addChildEventListener(childEventListener);

        Log.d(TAG, User.getInstance().getType());
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
                fragmentTransaction.replace(R.id.contentContainer, forumFragment, "forumFragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.academics_tab_item:
                // academics fragment
                AcademicsFragment academicsFragment = new AcademicsFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentContainer, academicsFragment)
                        .commit();
                break;
            case R.id.profile_tab_item:
                // profile fragment
                ProfileFragment profileFragment = new ProfileFragment();
                getFragmentManager().
                        beginTransaction()
                        .replace(R.id.contentContainer, profileFragment, "profileFragmenr")
                        .commit();
                break;
            case R.id.options_tab_item:
                // options fragment
                OptionsFragment optionsFragment = new OptionsFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentContainer, optionsFragment, "optionsFragment")
                        .commit();
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
        // add the post to Firebase Database
        DatabaseReference ref = postsRef.push();
        ref.child("post_title").setValue(title);
        ref.child("post_desc").setValue(desc);
        ref.child("uid").setValue(firebaseUser.getUid());
        List<String> joinedUsers = new ArrayList<>();
        for (int i = 1; i <= 7; i++)
            joinedUsers.add("uid" + i);
        ref.child("joined_users").setValue(joinedUsers);
        ref.child("name").setValue(firebaseUser.getDisplayName());
        ref.child("type").setValue(User.getInstance().getType());
    }

}
