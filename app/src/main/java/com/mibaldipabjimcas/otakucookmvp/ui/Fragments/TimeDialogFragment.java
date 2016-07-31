package com.mibaldipabjimcas.otakucookmvp.ui.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.ui.Activities.RecipeDescriptionActivity;


public class TimeDialogFragment extends DialogFragment {

    public static TimeDialogFragment newInstance() {
        TimeDialogFragment frag = new TimeDialogFragment();
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return  new AlertDialog.Builder(getActivity()).setIcon(R.mipmap.ic_launcher)
                .setTitle(R.string.want_start_challenge)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getTargetFragment().onActivityResult(getTargetRequestCode(), RecipeDescriptionActivity.SHARED_OK,getActivity().getIntent());
                        dismiss();
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert)
                .create();

    }


}


