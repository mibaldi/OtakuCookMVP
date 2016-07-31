package com.mibaldipabjimcas.otakucookmvp.ui.Views;

import com.mibaldipabjimcas.otakucookmvp.Base.BaseView;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;

import java.util.List;

/**
 * Created by mikelbalducieldiaz on 17/7/16.
 */
public interface RecipeListFavoritesView  extends BaseView {
    void showRecipeFavoriteList(List<Recipe> recipes);
    void showNoRecipes();

    void showProgressDialog(int message);
    void cancelProgressDialog();
}
