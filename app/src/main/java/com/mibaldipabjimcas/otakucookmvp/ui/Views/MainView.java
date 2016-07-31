package com.mibaldipabjimcas.otakucookmvp.ui.Views;

import com.mibaldipabjimcas.otakucookmvp.Base.BaseView;

public interface MainView extends BaseView {

    void openMainRecipe();

    void showRecipeImage(String photo);

    void showDefaultImage();

    void showRatingBar(int score);
    
    void showRecipeName(String name);
    
    void showRecipeAuthor(String author);

    void showRandomButton(Boolean b);

    void showProgressDialog(int message);
    void cancelProgressDialog();

    void showRecipeButton(int visibility);
}
