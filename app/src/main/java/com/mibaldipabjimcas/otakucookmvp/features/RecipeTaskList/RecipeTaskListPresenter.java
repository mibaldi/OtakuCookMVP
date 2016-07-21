package com.mibaldipabjimcas.otakucookmvp.features.RecipeTaskList;

import com.mibaldipabjimcas.otakucookmvp.Base.BasePresenter;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Task;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.MainView;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.RecipeTaskListView;

import java.util.ArrayList;

import javax.inject.Inject;

@PerActivity
public class RecipeTaskListPresenter extends BasePresenter<RecipeTaskListView> {
    private Task task;

    @Inject
    public RecipeTaskListPresenter() {
    }
    public void init(Task task){
        this.task = task;
        getView().showTaskImage(task.photo);
        getView().showTaskName(task.name);
        getView().showTaskDescription(task.description);
        getView().showTaskSeconds(task.seconds);
        //getView().showRecipeImage(recipe.photo);
        //getView().showRecipeName(recipe.name);
    }
}
