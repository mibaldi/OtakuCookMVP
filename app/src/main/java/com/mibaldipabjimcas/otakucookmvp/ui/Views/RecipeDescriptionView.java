package com.mibaldipabjimcas.otakucookmvp.ui.Views;

import com.mibaldipabjimcas.otakucookmvp.Base.BaseView;

public interface RecipeDescriptionView extends BaseView {
    void showRecipeImage(String photo);

    void showRecipeName(String name);

    void showRecipeRating(int score);

    void showRecipeAuthor(String author);

    void showRecipeIngredients();

    void showRecipeTime();

    void recipeFavorite();

    void showRecipeTaskList();

    void changeFavoriteIcon(boolean b);

    void showRecipePortions(int portions);
}
