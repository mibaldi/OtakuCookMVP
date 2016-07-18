package com.mibaldipabjimcas.otakucookmvp.features.LoginFirebase;

import com.mibaldipabjimcas.otakucookmvp.Application.OtakuCookApplicationComponent;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.RecipeDescriptionFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = OtakuCookApplicationComponent.class,modules = LoginFirebaseModule.class)
public interface LoginFirebaseComponent {

    void inject(RecipeDescriptionFragment recipeDescriptionFragment);

    LoginFirebasePresenter presenter();
}
