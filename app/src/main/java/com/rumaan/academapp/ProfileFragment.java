package com.rumaan.academapp;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private static final int REQUEST_CODE = 11;
    private TextView profileHeaderText;
    private CircularImageView profileAvatarImage;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileHeaderText = (TextView) view.findViewById(R.id.profile_header);
        profileAvatarImage = (CircularImageView) view.findViewById(R.id.profile_avatar);

        profileAvatarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // change the default image
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        profileAvatarImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(view.getContext(), "Profile Image", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        profileHeaderText.setTypeface(CustomFont.getInstance(view.getContext()).getTypeFace(2));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Uri imageUri = data.getData();
                profileAvatarImage.setImageURI(imageUri);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getView().getContext(), "Failed to load image!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
