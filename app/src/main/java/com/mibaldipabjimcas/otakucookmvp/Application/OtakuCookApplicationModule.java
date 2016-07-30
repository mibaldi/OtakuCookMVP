package com.mibaldipabjimcas.otakucookmvp.Application;


import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mibaldipabjimcas.otakucookmvp.Navigation.Navigator;
import com.mibaldipabjimcas.otakucookmvp.Services.Firebase.FirebaseRepository;
import com.mibaldipabjimcas.otakucookmvp.Services.Preferences.PreferencesManager;
import com.mibaldipabjimcas.otakucookmvp.features.LoginFirebase.ApiClientRepository;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class OtakuCookApplicationModule {
    private final Context context;
    public OtakuCookApplicationModule(Context context){
        this.context = context;
    }
    @Named("ApplicationContext")
    @Provides
    @Singleton
    Context providedApplicationContext(){
        return this.context;
    }
    @Provides
    @Singleton
    Navigator providedNavigator(){
        return new Navigator(context);
    }

    @Provides
    @Singleton
    ApiClientRepository providedApiClientRepository(){return new ApiClientRepository();}

    @Provides
    @Singleton
    FirebaseRepository providedFirebaseRepository(){return new FirebaseRepository();}

    @Provides
    @Singleton
    PreferencesManager providedePreferencesManager(){return new PreferencesManager();}
}
