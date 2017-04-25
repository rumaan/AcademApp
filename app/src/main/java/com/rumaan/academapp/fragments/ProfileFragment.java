package com.rumaan.academapp.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.rumaan.academapp.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment {

    @BindView(R.id.profile_name_text)
    TextView nameText;
    @BindView(R.id.college_text)
    TextView collegeNameText;
    @BindView(R.id.course_text)
    TextView courseText;
    @BindView(R.id.email_text)
    TextView emailText;
    @BindView(R.id.profile_avatar)
    CircularImageView circularImageView;

    private FirebaseUser firebaseUser;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // set up firebase stuffs here
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // set the TextView's
        nameText.setText(firebaseUser.getDisplayName());
        emailText.setText(firebaseUser.getEmail());

        // set the profile pic
        Picasso.with(getContext())
                .load(firebaseUser.getPhotoUrl())
                .placeholder(R.drawable.ic_user)
                .noFade()
                .into(circularImageView);
    }
}
