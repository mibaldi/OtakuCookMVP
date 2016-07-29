package com.mibaldipabjimcas.otakucookmvp.features.IngredientList;

import com.mibaldipabjimcas.otakucookmvp.Application.OtakuCookApplicationComponent;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.IngredientListFragment;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.RecipeTaskListFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = OtakuCookApplicationComponent.class,modules = IngredientListModule.class)
public interface IngredientListComponent {

    void inject(IngredientListFragment ingredientListFragment);

    IngredientListPresenter presenter();
}
