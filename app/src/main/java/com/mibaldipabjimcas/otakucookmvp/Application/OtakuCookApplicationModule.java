package com.mibaldipabjimcas.otakucookmvp.Application;


import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mibaldipabjimcas.otakucookmvp.Navigation.Navigator;
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
        return new Navigator(this.context);
    }

    @Provides
    @Singleton
    ApiClientRepository providedApiClientRepository(){return new ApiClientRepository();}

}
