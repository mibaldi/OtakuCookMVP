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
    private ArrayList<Task> taskArrayList;

    @Inject
    public RecipeTaskListPresenter() {
    }
    public void init(ArrayList<Task> taskArrayList){
        this.taskArrayList = taskArrayList;
        //getView().showRecipeImage(recipe.photo);
        //getView().showRecipeName(recipe.name);
    }
}
