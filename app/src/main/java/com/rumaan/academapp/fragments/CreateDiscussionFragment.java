package com.rumaan.academapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rumaan.academapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateDiscussionFragment extends DialogFragment {
    @BindView(R.id.dicussion_title)
    EditText discussionTitle;
    @BindView(R.id.discussion_desc)
    EditText discussinDesc;

    NoticeDialogListener noticeDialogListener;

    public CreateDiscussionFragment() {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // inflate the layout with layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_create_discussion, null);
        builder.setTitle("Create Discussion");
        builder.setView(view);

        ButterKnife.bind(this, view);
        final AlertDialog d = builder.create();
        /* Work around for validating the input fields */
        d.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = discussionTitle.getText().toString();
                        String desc = discussinDesc.getText().toString();
                        if (TextUtils.isEmpty(title)) {
                            discussionTitle.setError("Invalid Title");
                            discussionTitle.requestFocus();
                        } else if (TextUtils.isEmpty(desc)) {
                            discussinDesc.setError("Invalid Description!");
                            discussinDesc.requestFocus();
                        } else {
                            noticeDialogListener.onPositiveClick(CreateDiscussionFragment.this, title, desc);
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        return d;
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            noticeDialogListener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must be implemented.");
        }
    }

    public interface NoticeDialogListener {
        void onPositiveClick(DialogFragment dialog, String title, String desc);
    }
}
