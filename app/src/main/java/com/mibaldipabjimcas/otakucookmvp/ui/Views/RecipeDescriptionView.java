package com.mibaldipabjimcas.otakucookmvp.ui.Views;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.mibaldipabjimcas.otakucookmvp.Base.BaseView;

public interface RecipeDescriptionView extends BaseView {
    void showRecipeImage(String photo);

    void showRecipeName(String name);

    void showRecipeRating(int score);

    void showRecipeAuthor(String author);

    void showRecipeIngredients();

    void showRecipeTime(String timeString);

    void startRecipeTime(View view);

    void recipeFavorite();

    void showRecipeTaskList();

    void changeFavoriteIcon(boolean b);

    void showRecipePortions(int portions);

    void hideFavoriteIcon();

    Drawable getDrawableImage();
}
