package com.mibaldipabjimcas.otakucookmvp.features.RecipeTaskList;

import com.mibaldipabjimcas.otakucookmvp.Application.OtakuCookApplicationComponent;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.MainFragment;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.RecipeTaskListFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = OtakuCookApplicationComponent.class,modules = RecipeTaskListModule.class)
public interface RecipeTaskListComponent {

    void inject(RecipeTaskListFragment recipeTaskListFragment);

    RecipeTaskListPresenter presenter();
}
