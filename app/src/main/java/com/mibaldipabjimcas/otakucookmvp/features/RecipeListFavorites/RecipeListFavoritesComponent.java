package com.mibaldipabjimcas.otakucookmvp.features.RecipeListFavorites;

import com.mibaldipabjimcas.otakucookmvp.Application.OtakuCookApplicationComponent;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.MainFragment;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.RecipeListFavoritesFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = OtakuCookApplicationComponent.class,modules = RecipeListFavoritesModule.class)
public interface RecipeListFavoritesComponent {

    void inject(RecipeListFavoritesFragment recipeListFavoritesFragment);

    RecipeListFavoritesPresenter presenter();
}
