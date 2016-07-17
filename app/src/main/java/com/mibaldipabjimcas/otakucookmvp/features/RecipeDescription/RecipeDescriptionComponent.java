package com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription;

import com.mibaldipabjimcas.otakucookmvp.Application.OtakuCookApplicationComponent;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.MainFragment;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.RecipeDescriptionFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = OtakuCookApplicationComponent.class,modules = RecipeDescriptionModule.class)
public interface RecipeDescriptionComponent {

    void inject(RecipeDescriptionFragment recipeDescriptionFragment);

    RecipeDescriptionPresenter presenter();
}
