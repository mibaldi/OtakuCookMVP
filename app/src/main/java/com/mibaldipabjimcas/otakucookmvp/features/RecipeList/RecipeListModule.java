package com.mibaldipabjimcas.otakucookmvp.features.RecipeList;

import com.mibaldipabjimcas.otakucookmvp.Navigation.Navigator;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Adapters.RecipesListAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class RecipeListModule {
    public RecipeListModule() {
    }
    @Provides
    @PerActivity
    RecipesListAdapter providerRecipeListAdapter(){
        return new RecipesListAdapter();
    }
    @Provides
    @PerActivity
    RecipeListPresenter providerRecipeListPresenter(Navigator navigator){
        return new RecipeListPresenter(navigator);
    }

}
