package com.mibaldipabjimcas.otakucookmvp.ui.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.ui.Activities.RecipeDescriptionActivity;

/**
 * Created by Pablo Jiménez Casado on 30/07/2016.
 */

public class FavoriteDialogFragment extends DialogFragment {

    public static  FavoriteDialogFragment newInstance(Boolean favorite) {
        FavoriteDialogFragment frag = new  FavoriteDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean("favorite",favorite);
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return  new AlertDialog.Builder(getActivity()).setIcon(R.mipmap.ic_launcher)
                .setTitle(getTitle())
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getTargetFragment().onActivityResult(getTargetRequestCode(), RecipeDescriptionActivity.FAVORITE_OK,getActivity().getIntent());
                        dismiss();
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert)
                .create();

    }

    public String getTitle(){
        if(getArguments().getBoolean("favorite")){
            return getString(R.string.want_delete_favorite_recipe);
        }
        return getString(R.string.want_save_favorite_recipe);
    }
}


