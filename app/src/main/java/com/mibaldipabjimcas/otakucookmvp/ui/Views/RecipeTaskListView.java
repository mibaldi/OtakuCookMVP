package com.mibaldipabjimcas.otakucookmvp.ui.Views;

import com.mibaldipabjimcas.otakucookmvp.Base.BaseView;

public interface RecipeTaskListView extends BaseView {
    void showTaskImage(String photo);
    void showTaskName(String name);
    void showTaskTime(int minutes);
    void showTaskDescription(String description);

    void hideTime();
}
