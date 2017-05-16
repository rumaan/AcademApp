package com.rumaan.academapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.rumaan.academapp.R;
import com.rumaan.academapp.model.MaterialIn;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChooseTypeActivity extends AppCompatActivity {
    @BindView(R.id.root_view)
    ViewGroup rootView;
    @BindView(R.id.choose_view)
    ViewGroup rootViewContainer;

    private TransitionSet mTransitionSet;
    private Scene mStudentScene, mLecturerScene, mDefaultScene;

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
                // chosen student
                // change scene to student details
                createStudentScene();
                break;
            case R.id.lecturer:
                // chosen lecturer
                // change scene to lecturer details
                createLecturerScene();
                break;
        }
    }

    // change the scene to Student Select Scene
    private void createStudentScene() {
        TransitionManager.go(mStudentScene);
    }

    // change the scene to Lecturer Select Scene
    private void createLecturerScene() {
        TransitionManager.go(mLecturerScene);
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

        // set up scenes
        mDefaultScene = Scene.getSceneForLayout(rootView, R.layout.activity_choose_type, this);
        mLecturerScene = Scene.getSceneForLayout(rootView, R.layout.scene_type_lecturer_details, this);
        mStudentScene = Scene.getSceneForLayout(rootView, R.layout.scene_type_student_details, this);

    }

    @Override
    public void onBackPressed() {
        if (getContentScene() == mDefaultScene) {
            this.finishAffinity();
        } else {
            getContentScene().exit();
            TransitionManager.go(mDefaultScene);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
