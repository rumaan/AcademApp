package com.rumaan.academapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.AutoTransition;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rumaan.academapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Activity to get Extra User Details:
 * College Name
 * Sem
 * Class
 * Course
 */

public class UserDetailsActivity extends AppCompatActivity {

    @BindView(R.id.root_view)
    LinearLayout rootView;
    @BindView(R.id.usn)
    TextInputLayout usnTextInput;
    @BindView(R.id.college_name)
    TextInputLayout collegeNameInput;

    @OnClick(R.id.btn_next)
    void onClick() {
        TransitionManager.go(Scene.getSceneForLayout(rootView, R.layout.activity_home, this)
                , TransitionInflater.from(this).inflateTransition(R.transition.into_main_screen));

        startActivity(new Intent(this, MainActivity.class));
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);

    }


    /* Inner class which gets triggered on text change in the text input fields */
    class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // do something before text changes
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // do something when text is changing
        }

        @Override
        public void afterTextChanged(Editable s) {
            // do something after text has changed
        }
    }
}
