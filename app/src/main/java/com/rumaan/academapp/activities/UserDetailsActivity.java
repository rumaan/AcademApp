package com.rumaan.academapp.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.rumaan.academapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Activity to get Extra User Details:
 * College Name
 * Sem
 * Class
 * Course
 */

public class UserDetailsActivity extends AppCompatActivity {
    private String collegeName;
    private String usn;

    @BindView(R.id.college_selector)
    Spinner spinner;
    @BindView(R.id.usn)
    TextInputLayout usnEdit;

    @OnItemSelected(R.id.college_selector)
    void onItemSelected(Spinner spinner) {
        collegeName = spinner.getSelectedItem().toString();
        Toast.makeText(this, collegeName, Toast.LENGTH_SHORT).show();
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

        ArrayAdapter<CharSequence> collegeNames = ArrayAdapter.createFromResource(this, R.array.college_names, android.R.layout.simple_spinner_item);
        collegeNames.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(collegeNames);
    }
}
