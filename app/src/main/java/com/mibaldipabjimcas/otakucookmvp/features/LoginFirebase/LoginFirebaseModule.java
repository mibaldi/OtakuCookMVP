package com.mibaldipabjimcas.otakucookmvp.features.LoginFirebase;

import com.mibaldipabjimcas.otakucookmvp.Navigation.Navigator;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.features.LoginFirebase.interactors.LoginWithGoogleInteractor;
import com.mibaldipabjimcas.otakucookmvp.features.LoginFirebase.interactors.LoginWithGoogleInteractorImpl;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeList.RecipeListPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginFirebaseModule {
    public LoginFirebaseModule() {
    }

    @Provides
    @PerActivity
    LoginWithGoogleInteractor providerLoginWithGoogleInteractor(){
        return new LoginWithGoogleInteractorImpl();
    }

}
