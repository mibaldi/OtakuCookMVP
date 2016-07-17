package com.mibaldipabjimcas.otakucookmvp.features.RecipeTaskList;

import com.mibaldipabjimcas.otakucookmvp.Base.BasePresenter;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.MainView;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.RecipeTaskListView;

import javax.inject.Inject;

@PerActivity
public class RecipeTaskListPresenter extends BasePresenter<RecipeTaskListView> {
    @Inject
    public RecipeTaskListPresenter() {
    }
}
