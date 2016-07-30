package com.mibaldipabjimcas.otakucookmvp.features.MainActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mibaldipabjimcas.otakucookmvp.Base.BasePresenter;
import com.mibaldipabjimcas.otakucookmvp.Navigation.Navigator;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.features.LoginFirebase.ApiClientRepository;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.MainActivityView;

import javax.inject.Inject;

import timber.log.Timber;

@PerActivity
public class MainActivityPresenter extends BasePresenter<MainActivityView> {
    Navigator navigator;
    @Inject
    ApiClientRepository apiClientRepository;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Inject
    public MainActivityPresenter(Navigator navigator) {
        this.navigator= navigator;
    }
    public void init(FragmentActivity fragmentActivity){

        apiClientRepository.init(fragmentActivity, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Timber.e("error");
            }
        });
        getView().showUserName(firebaseUser.getDisplayName());

        getView().showUserAvatar(firebaseUser.getPhotoUrl().toString());
        Timber.d(firebaseUser.getDisplayName());

    }



    public void signOut(){
        apiClientRepository.signOut(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                FirebaseAuth.getInstance().signOut();
                navigator.goToLogin();
            }
        });
    }
    public void suggestionDialog(Context context){
        navigator.openSuggestionDialog(context);
    }

    public void goPreferences() {
        navigator.openPreferences();
    }
}
