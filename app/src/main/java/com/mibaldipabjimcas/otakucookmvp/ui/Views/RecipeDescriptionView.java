package com.mibaldipabjimcas.otakucookmvp.ui.Views;

import com.mibaldipabjimcas.otakucookmvp.Base.BaseView;

public interface RecipeDescriptionView extends BaseView {
    void showRecipeImage(String photo);
    void showRecipeName(String name);
    void showRecipeTaskList();
}
