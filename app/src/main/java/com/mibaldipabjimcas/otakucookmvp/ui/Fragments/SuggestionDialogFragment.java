package com.mibaldipabjimcas.otakucookmvp.ui.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.ui.Activities.RecipeDescriptionActivity;

public class SuggestionDialogFragment extends DialogFragment {

    public static SuggestionDialogFragment newInstance() {
        SuggestionDialogFragment frag = new SuggestionDialogFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LinearLayout view = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.dialog_suggestion_fragment,null);
        final EditText text = (EditText) view.findViewById(R.id.suggestion);
        return  new android.app.AlertDialog.Builder(getActivity()).setIcon(R.mipmap.ic_launcher)
                .setView(view)
                .setTitle(R.string.dialog_suggestion_question)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(!text.getText().equals("")) {
                            Intent email = new Intent();
                            email.setAction(Intent.ACTION_SEND);
                            email.putExtra(Intent.EXTRA_TEXT, text.getText());
                            email.setType("message/rfc822");
                            email.putExtra(Intent.EXTRA_EMAIL,new String[]{getString(R.string.email_suggestions)});
                            email.putExtra(Intent.EXTRA_SUBJECT,getResources().getString(R.string.suggestion));
                            email.putExtra(Intent.EXTRA_TEXT,text.getText());

                            startActivity(Intent.createChooser(email,getResources().getString(R.string.select_email)));
                        }
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .create();

    }


}


