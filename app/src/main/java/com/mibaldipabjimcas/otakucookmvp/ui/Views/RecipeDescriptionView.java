package com.mibaldipabjimcas.otakucookmvp.ui.Views;

import android.widget.Button;

import com.mibaldipabjimcas.otakucookmvp.Base.BaseView;

public interface RecipeDescriptionView extends BaseView {
    void showRecipeImage(String photo);

    void showRecipeName(String name);

    void showRecipeRating(int score);

    void showRecipeAuthor(String author);

    void showRecipeIngredients();

    void showRecipeTime(Button button);

    void recipeFavorite();

    void showRecipeTaskList();

    void changeFavoriteIcon(boolean b);

    void showRecipePortions(int portions);

    void hideFavoriteIcon();
}
