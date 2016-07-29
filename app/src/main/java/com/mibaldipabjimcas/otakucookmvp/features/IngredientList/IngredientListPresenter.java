package com.mibaldipabjimcas.otakucookmvp.features.IngredientList;

import com.mibaldipabjimcas.otakucookmvp.Base.BasePresenter;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Measure;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Task;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.IngredientListView;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.RecipeTaskListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PerActivity
public class IngredientListPresenter extends BasePresenter<IngredientListView> {

    @Inject
    public IngredientListPresenter() {
    }
    public void init(ArrayList<Measure> ingredients){
        getView().showIngredientList(ingredients);
    }
}
