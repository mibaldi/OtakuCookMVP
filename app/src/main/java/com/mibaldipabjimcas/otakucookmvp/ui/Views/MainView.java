package com.mibaldipabjimcas.otakucookmvp.ui.Views;

import com.mibaldipabjimcas.otakucookmvp.Base.BaseView;

public interface MainView extends BaseView {
    void showRecipeImage(String photo);

    void showRatingBar(int score);
    
    void showRecipeName(String name);
    
    void showRecipeAuthor(String author);

    void showRandomButton(int visible);
}
