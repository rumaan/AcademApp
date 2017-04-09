package com.rumaan.academapp.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.rumaan.academapp.R;

/**
 * Activity to get Extra User Details:
 * College Name
 * Sem
 * Class
 * Course
 */

public class UserDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
    }
}
