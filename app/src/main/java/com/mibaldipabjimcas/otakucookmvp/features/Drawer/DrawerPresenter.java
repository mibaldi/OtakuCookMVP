package com.mibaldipabjimcas.otakucookmvp.features.Drawer;

import android.support.annotation.NonNull;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.mibaldipabjimcas.otakucookmvp.Base.BasePresenter;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.features.LoginFirebase.ApiClientRepository;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.DrawerView;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.MainView;

import javax.inject.Inject;

@PerActivity
public class DrawerPresenter extends BasePresenter<DrawerView> {

    @Inject
    ApiClientRepository apiClientRepository;

    @Inject
    public DrawerPresenter() {
    }

    public void signOut(){
        apiClientRepository.signOut(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {

            }
        });
    }


}
