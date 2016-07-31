package com.mibaldipabjimcas.otakucookmvp.features.RecipeList;

import android.content.Context;

import com.mibaldipabjimcas.otakucookmvp.Base.BasePresenter;
import com.mibaldipabjimcas.otakucookmvp.Constants.ErrorConstants;
import com.mibaldipabjimcas.otakucookmvp.Navigation.Navigator;
import com.mibaldipabjimcas.otakucookmvp.Services.Connectivity.Connectivity;
import com.mibaldipabjimcas.otakucookmvp.Services.Retrofit2.ApiClient;
import com.mibaldipabjimcas.otakucookmvp.Services.Retrofit2.ApiEndPointInterface;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.RecipeListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@PerActivity
public class RecipeListPresenter extends BasePresenter<RecipeListView> {
    private ApiEndPointInterface service;

    Navigator navigator;
    private Context context;

    @Inject
    public RecipeListPresenter(Navigator navigator) {
        this.navigator = navigator;
    }
    public void init(Context context){
        this.context = context;
       loadServerService();
    }

    public void loadServerService(){
        if(Connectivity.isNetworkAvailable(context)) {
            getView().loadingRecipes(true);
            service = ApiClient.createService(ApiEndPointInterface.class);
            getView().swipeRefresh(true);
            Call<List<Recipe>> recipeList = service.recipes();
            recipeList.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    getView().loadingRecipes(false);
                    getView().showRecipeList(response.body());
                    getView().swipeRefresh(false);
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    getView().showError(ErrorConstants.SERVER_ERROR);
                    getView().swipeRefresh(false);
                }
            });
        }else{
            getView().showNoConnectivity();
        }
    }

    public void loadRecipe(Recipe recipe){
        if(Connectivity.isNetworkAvailable(context)) {
            getView().showProgressBar(true);
            Call<Recipe> recipeList = service.getRecipe(recipe.id);
            recipeList.enqueue(new Callback<Recipe>() {

                @Override
                public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                    getView().showProgressBar(false);
                    navigator.openRecipeDescription(response.body());
                }

                @Override
                public void onFailure(Call<Recipe> call, Throwable t) {
                    getView().showError(ErrorConstants.SERVER_ERROR);
                }
            });
        }else{
            getView().showNoConnectivity();
        }
    }


    public List<Recipe> filter(List<Recipe> recipeList, String newText) {
        List<Recipe> filteredRecipes = new ArrayList<>();
        newText = newText.toLowerCase();
        for (Recipe recipe : recipeList) {
            final String name = recipe.name.toLowerCase();
            if (name.contains(newText)) {
                filteredRecipes.add(recipe);
            }
        }
        return filteredRecipes;
    }
}
