package com.mibaldipabjimcas.otakucookmvp.Application;

import android.content.Context;

import com.mibaldipabjimcas.otakucookmvp.Base.BaseActivity;
import com.mibaldipabjimcas.otakucookmvp.Navigation.Navigator;
import com.mibaldipabjimcas.otakucookmvp.Services.Firebase.FirebaseRepository;
import com.mibaldipabjimcas.otakucookmvp.features.LoginFirebase.ApiClientRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = OtakuCookApplicationModule.class)
public interface OtakuCookApplicationComponent {
    @Named("ApplicationContext")
    Context context();

    void inject (BaseActivity baseActivity);
    Navigator getNavigator();
    ApiClientRepository apiClientRepository();
    FirebaseRepository firebaseRepository();
}
