package com.mibaldipabjimcas.otakucookmvp.features.RecipeList;

import com.mibaldipabjimcas.otakucookmvp.Application.OtakuCookApplicationComponent;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.MainFragment;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.RecipeListFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = OtakuCookApplicationComponent.class,modules = RecipeListModule.class)
public interface RecipeListComponent {

    void inject(RecipeListFragment recipeListFragment);

    RecipeListPresenter presenter();
}
