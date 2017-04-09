package com.rumaan.academapp.Activities;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.rumaan.academapp.R;
import com.rumaan.academapp.databinding.FragmentProfileBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private static final int REQUEST_CODE = 11;
    private static final int PERMISSION_REQUEST_CODE = 10;
    private static final String TAG = "ProfileFragment";
    private TextView profileHeaderText;
    private CircularImageView profileAvatarImage;

    private Intent intent;

    // Binding object
    private FragmentProfileBinding fragmentProfileBinding;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // instantiate binding object
        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false);


        // Inflate the layout for this fragment
        return inflater.inflate(com.rumaan.academapp.R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileHeaderText = (TextView) view.findViewById(R.id.profile_header);
        profileAvatarImage = (CircularImageView) view.findViewById(R.id.profile_avatar);

        // create choose profile image intent
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");

        profileAvatarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // change the default image
                // check permission here
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // permissions not available
                    ActivityCompat.requestPermissions(
                            getActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            PERMISSION_REQUEST_CODE);
                } else {
                    startActivityForResult(intent, REQUEST_CODE);
                }

            }
        });

        profileAvatarImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // TODO: 19-Mar-17 show the larger image preview
                Toast.makeText(view.getContext(), "Profile Image", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // check for permissions here
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(intent, REQUEST_CODE);
            } else if (grantResults.length == 0) {
                // this is probably useless
                Log.e(TAG, "Results Entered Zero");
            }
        }
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
