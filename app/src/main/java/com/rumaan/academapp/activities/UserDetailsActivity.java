package com.rumaan.academapp.activities;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rumaan.academapp.R;
import com.rumaan.academapp.model.Constants;
import com.rumaan.academapp.model.MaterialIn;

import java.util.regex.Pattern;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Activity to get Extra User Details:
 * College Name
 * Sem
 * Class
 * Course
 */

public class UserDetailsActivity extends AppCompatActivity {
    public static final String BUNDLE_KEY = "Type";
    public static final String INTENT_KEY = "key";

    private static final String TAG = "UserDetailsActivity";

    @BindView(R.id.root_view)
    LinearLayout rootView;
    @BindView(R.id.usn)
    TextInputLayout usnTextInput;
    @BindView(R.id.college_name)
    TextInputLayout collegeNameInput;
    @BindView(R.id.course_spinner)
    Spinner courseSpinner;
    @BindView(R.id.year_spinner)
    Spinner yearSpinner;
    @BindColor(R.color.Emerald_flat)
    int emeraldFlatColor;
    @BindView(R.id.mask_view)
    View maskView;
    @BindView(R.id.btn_text)
    TextView buttonText;
    @BindView(R.id.img_check)
    ImageView checkImage;
    @BindView(R.id.card_details)
    CardView cardDetails;
    private int count = 0;
    private DatabaseReference collegeDetailsRef;
    private int type;

    @OnClick(R.id.btn_next)
    void onClick(View view) {
        // Validate the fields before going to next activity
        Editable collegeName = collegeNameInput.getEditText().getText();
        Editable usn = usnTextInput.getEditText().getText();
        if (TextUtils.isEmpty(collegeName) || isCollegeNameValid(collegeName)) {
            collegeNameInput.setErrorEnabled(true);
            collegeNameInput.setError(getString(R.string.valid_college_name_error));
            return;
        }
        if (TextUtils.isEmpty(usn) || isUsnValid(usn) || usn.length() != 10) {
            usnTextInput.setErrorEnabled(true);
            usnTextInput.setError(getString(R.string.valid_usn_error));
            return;
        }

        // reset the error for EditText
        collegeNameInput.setErrorEnabled(false);
        usnTextInput.setErrorEnabled(false);

        // update the values in the database
        updateFirebaseDatabase(type);
        updateFirebaseDatabase(collegeName,
                usn,
                yearSpinner.getSelectedItem().toString(),
                courseSpinner.getSelectedItem().toString()
        );

        // TODO: check for changes after the button click
        // change the next button to continue and increment counter
        if (count == 1) {
            startActivity(new Intent(this, MainActivity.class));
        } else animateButton();
    }

    /* Animates the Next Button */
    private void animateButton() {
        /* Circular reveal the button */
        int startRadius = 0;
        int finalRadius = Math.max(maskView.getHeight(), maskView.getWidth());
        final Animator anim = ViewAnimationUtils.createCircularReveal(maskView,
                maskView.getRight(),
                maskView.getHeight() / 2
                , startRadius, finalRadius);
        maskView.setVisibility(View.VISIBLE);
        anim.setInterpolator(new FastOutSlowInInterpolator());
        anim.setDuration(800);
        anim.start();

        // set the button text2+
        buttonText.setText(R.string.continue_btn_text);

        /* Animate the check image */
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(checkImage, "rotation", 0f, 360f);
        rotationAnimator.setDuration(400);
        rotationAnimator.setInterpolator(new FastOutSlowInInterpolator());

        int center = maskView.getRight() / 2;
        int right = maskView.getRight();
        int offSet = (right - center) / 2;
        ObjectAnimator translateXInterpolator = ObjectAnimator.ofFloat(checkImage, "x", offSet + 10f);
        translateXInterpolator.setDuration(400);
        translateXInterpolator.setInterpolator(new DecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotationAnimator, translateXInterpolator);
        animatorSet.start();

        // for second time
        count++;
    }

    private void updateFirebaseDatabase(Editable collegeName, Editable usn, String year, String course) {
        // set the values in Database
        // set firebase stuffs

        collegeDetailsRef.child("name").setValue(collegeName.toString());
        collegeDetailsRef.child("usn").setValue(usn.toString());
        collegeDetailsRef.child("year").setValue(year);
        collegeDetailsRef.child("course").setValue(course);
    }

    private void updateFirebaseDatabase(int type) {
        collegeDetailsRef.child("type").setValue(type == 0 ? "Student" : "Lecturer");
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);

        // animate card
        MaterialIn.animate(cardDetails);

        // set up Database Reference
        collegeDetailsRef = FirebaseDatabase.getInstance()
                .getReference(Constants.USER_REF_STRING)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("college_details");

        ArrayAdapter coursesList = ArrayAdapter.createFromResource(this, R.array.courses_list, R.layout.spinner_item);
        ArrayAdapter yearList = ArrayAdapter.createFromResource(this, R.array.year_list, R.layout.spinner_item);

        courseSpinner.setAdapter(coursesList);
        yearSpinner.setAdapter(yearList);

        // add text watchers to input fields
        collegeNameInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (isCollegeNameValid(s)) {
                    collegeNameInput.setError(getString(R.string.valid_college_name_error));
                    collegeNameInput.setErrorEnabled(true);
                } else {
                    collegeNameInput.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        usnTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (isUsnValid(s)) {
                    usnTextInput.setErrorEnabled(true);
                    usnTextInput.setError(getString(R.string.valid_usn_error));
                } else {
                    usnTextInput.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // get the user type
        type = getIntent().getIntExtra(Constants.INTENT_SEND_KEY, 0);
        Log.d(TAG, "Type: " + type);
    }

    boolean isCollegeNameValid(CharSequence s) {
        // TODO: Check for more invalid characters
        return !Pattern.matches("^(a-z|A-Z|0-9)*[^#$%^&*()/+_-]*$", s.toString());
    }

    boolean isUsnValid(CharSequence s) {
        // TODO: create regExp particular to USN
        return !Pattern.matches("^(a-z|A-Z|0-9)*[^#$%^&*()' /+_-]*$", s.toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
