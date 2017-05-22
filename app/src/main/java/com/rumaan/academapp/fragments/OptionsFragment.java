package com.rumaan.academapp.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.rumaan.academapp.R;
import com.rumaan.academapp.activities.SplashPageActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class OptionsFragment extends Fragment {
    public OptionsFragment() {
        // Required empty public constructor
    }

    @OnClick(R.id.btn_log_out)
    void onClick() {
        Toast.makeText(getActivity(), "Logging Out.. please wait!", Toast.LENGTH_SHORT).show();
        FirebaseAuth.getInstance().signOut();
        getActivity().finishAffinity();
        startActivity(new Intent(getActivity(), SplashPageActivity.class));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_options, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}
