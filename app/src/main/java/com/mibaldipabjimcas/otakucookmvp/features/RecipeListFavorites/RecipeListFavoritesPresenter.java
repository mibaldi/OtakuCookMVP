package com.mibaldipabjimcas.otakucookmvp.features.RecipeListFavorites;

import com.mibaldipabjimcas.otakucookmvp.Base.BasePresenter;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.MainView;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.RecipeListFavoritesView;

import javax.inject.Inject;

@PerActivity
public class RecipeListFavoritesPresenter extends BasePresenter<RecipeListFavoritesView> {
    @Inject
    public RecipeListFavoritesPresenter() {
    }
}
