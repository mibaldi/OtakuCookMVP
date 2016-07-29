package com.mibaldipabjimcas.otakucookmvp.ui.Views;

import com.mibaldipabjimcas.otakucookmvp.Base.BaseView;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Ingredient;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Measure;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikelbalducieldiaz on 17/7/16.
 */
public interface IngredientListView extends BaseView {
    void showIngredientList(ArrayList<Measure> ingredients);
}
