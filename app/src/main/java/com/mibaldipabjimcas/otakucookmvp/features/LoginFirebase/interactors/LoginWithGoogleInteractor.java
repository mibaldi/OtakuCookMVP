package com.mibaldipabjimcas.otakucookmvp.features.LoginFirebase.interactors;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by pabji on 18/07/2016.
 */

public interface LoginWithGoogleInteractor {

    void onStart(GoogleApiClient mGoogleApiClient);

    void execute(FirebaseAuth mAuth,FirebaseAuth.AuthStateListener listener);

    void onActivityResult(int requestCode, Intent data, OnCompleteListener onCompleteListener);
}
