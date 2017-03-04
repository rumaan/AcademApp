package com.rumaan.academapp;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rumaan.academapp.databinding.FragmentAcademicsBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class AcademicsFragment extends Fragment {

    private FragmentAcademicsBinding fragmentAcademicsBinding;

    public AcademicsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return FragmentAcademicsBinding.inflate(inflater, container, false).getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentAcademicsBinding = DataBindingUtil.getBinding(view);

        fragmentAcademicsBinding.testText.setTypeface(CustomFont.getInstance(getContext()).getTypeFace(CustomFont.Regular));
    }
}
