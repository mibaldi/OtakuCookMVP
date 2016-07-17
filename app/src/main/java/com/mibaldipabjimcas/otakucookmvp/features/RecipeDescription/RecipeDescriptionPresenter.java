package com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription;

import com.mibaldipabjimcas.otakucookmvp.Base.BasePresenter;
import com.mibaldipabjimcas.otakucookmvp.Navigation.Navigator;
import com.mibaldipabjimcas.otakucookmvp.Services.ApiClient;
import com.mibaldipabjimcas.otakucookmvp.Services.ApiEndPointInterface;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.MainView;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.RecipeDescriptionView;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@PerActivity
public class RecipeDescriptionPresenter extends BasePresenter<RecipeDescriptionView> {
    private ApiEndPointInterface service;

    Navigator navigator;
    private Recipe recipe;

    @Inject
    public RecipeDescriptionPresenter(Navigator navigator) {
        this.navigator = navigator;
    }

    public void init(Recipe recipe){
        this.recipe = recipe;
        getView().showRecipeImage(recipe.photo);
        getView().showRecipeName(recipe.name);
    }

    public void showRecipeTaskList(){
        navigator.openRecipeTaskList(recipe.getTasks());
    }
}
