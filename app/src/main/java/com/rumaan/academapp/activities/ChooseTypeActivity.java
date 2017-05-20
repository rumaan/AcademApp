package com.rumaan.academapp.activities;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rumaan.academapp.R;
import com.rumaan.academapp.model.Constants;
import com.rumaan.academapp.model.MaterialIn;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Choose the user type in the activity
 * Required in type field in the Firebase Database.
 *
 * @author Rumaan
 * @version 1.0
 */

public class ChooseTypeActivity extends AppCompatActivity {
    @BindView(R.id.root_view)
    ViewGroup rootView;
    @BindView(R.id.choose_view)
    ViewGroup rootViewContainer;
    @BindView(R.id.student)
    ImageView studentImg;
    @BindView(R.id.lecturer)
    ImageView lecturerImg;
    @BindView(R.id.student_tick)
    ImageView studentTickImage;
    @BindView(R.id.lecturer_tick)
    ImageView lecturerTickImage;
    @BindView(R.id.root)
    View root;
    @BindView(R.id.btn_next)
    FrameLayout nextBtn;
    @BindView(R.id.mask_view)
    View maskView;
    @BindView(R.id.btn_text)
    TextView buttonText;

    private int chosenType;

    @OnLongClick({R.id.student, R.id.lecturer})
    boolean onLongClick(ImageView imageView) {
        switch (imageView.getId()) {
            case R.id.student:
                Toast.makeText(this, "Student", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lecturer:
                Toast.makeText(this, "Lecturer", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @OnClick({R.id.student, R.id.lecturer})
    void onClick(ImageView imageView) {
        switch (imageView.getId()) {
            case R.id.student:

                if (studentTickImage.getVisibility() != View.VISIBLE) {
                    chosenType = 0;
                    studentTickImage.setVisibility(View.VISIBLE);
                    MaterialIn.animate(studentTickImage, Gravity.TOP, Gravity.TOP);

                    lecturerTickImage.setVisibility(View.INVISIBLE);
                    animateButton("Student");
                }
                break;
            case R.id.lecturer:
                // chosen lecturer
                if (lecturerTickImage.getVisibility() != View.VISIBLE) {
                    chosenType = 1;
                    studentTickImage.setVisibility(View.INVISIBLE);

                    lecturerTickImage.setVisibility(View.VISIBLE);
                    MaterialIn.animate(lecturerTickImage, Gravity.TOP, Gravity.TOP);

                    animateButton("Lecturer");
                }
                break;
        }
    }

    private void animateButton(String userType) {
        nextBtn.setClickable(true);
        buttonText.setText("I am a " + userType);
        // create a circular reveal
        int centerX = maskView.getWidth() / 2;
        int centerY = maskView.getHeight();
        float startRadius = 0;
        float finalRadius = Math.max(maskView.getWidth(), maskView.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(maskView, centerX, centerY, startRadius, finalRadius);
        maskView.setVisibility(View.VISIBLE);
        animator.setDuration(900);
        animator.setInterpolator(new FastOutSlowInInterpolator());
        animator.start();

        MaterialIn.animate(buttonText, Gravity.TOP, Gravity.TOP);
        buttonText.setVisibility(View.VISIBLE);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseTypeActivity.this, UserDetailsActivity.class);
                // TODO: check for chosen type Nullable
                // send the chosen user type to next activity
                intent.putExtra(Constants.INTENT_SEND_KEY, chosenType);
                if (chosenType == 0) {
                    startActivity(intent);
                    ChooseTypeActivity.this.finishAffinity();
                } else
                    Toast.makeText(ChooseTypeActivity.this, "Lecturer User Type WIP!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_type);
        ButterKnife.bind(this);
        MaterialIn.animate(rootView);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setFontAttrId(R.attr.fontPath)
                .setDefaultFontPath("fonts/regular.ttf")
                .build());
    }

    @Override
    public void onBackPressed() {
        // kill everything
        this.finishAffinity();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
