package com.mibaldipabjimcas.otakucookmvp.features.RecipeList;

import com.mibaldipabjimcas.otakucookmvp.Base.BasePresenter;
import com.mibaldipabjimcas.otakucookmvp.Constants.ErrorConstants;
import com.mibaldipabjimcas.otakucookmvp.Navigation.Navigator;
import com.mibaldipabjimcas.otakucookmvp.Services.Retrofit2.ApiClient;
import com.mibaldipabjimcas.otakucookmvp.Services.Retrofit2.ApiEndPointInterface;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.RecipeListView;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@PerActivity
public class RecipeListPresenter extends BasePresenter<RecipeListView> {
    private ApiEndPointInterface service;

    Navigator navigator;
    @Inject
    public RecipeListPresenter(Navigator navigator) {
        this.navigator = navigator;
    }
    public void init(){
        service = ApiClient.createService(ApiEndPointInterface.class);
        Call<List<Recipe>> recipeList = service.recipes();
        recipeList.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                getView().showRecipeList(response.body());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                getView().showError(ErrorConstants.SERVER_ERROR);
            }
        });
    }
    public void loadRecipe(Recipe recipe){

        Call<Recipe> recipeList = service.getRecipe(recipe.id);
        recipeList.enqueue(new Callback<Recipe>() {

            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                navigator.openRecipeDescription(response.body());
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                //send an error
            }
        });
    }



}
