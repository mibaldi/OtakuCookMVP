package com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription;

import com.mibaldipabjimcas.otakucookmvp.Base.BasePresenter;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.MainView;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.RecipeDescriptionView;

import javax.inject.Inject;

@PerActivity
public class RecipeDescriptionPresenter extends BasePresenter<RecipeDescriptionView> {
    @Inject
    public RecipeDescriptionPresenter() {
    }
}
